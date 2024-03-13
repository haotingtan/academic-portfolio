#include "ext2_fs.h"
#include "read_ext2.h"
#include <dirent.h>
#include <stdio.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>

int fd;
char **out;
int block_read, nblock, njpg = 0;
__u32 jpg_inode[10], inode_size[10];

void reading_data_blocks(char *block_contents, int level) {
    if (level > 1) {
        for (int z=0; z < 256; z++) {
            char temp[1024];
            int *ptr = (int*)(block_contents + sizeof(int) * z);
            int block_num = *ptr; 
            if (lseek(fd, BLOCK_OFFSET(block_num), SEEK_SET) == -1) { 
                printf("Failed to seek to data block\n");
                continue;
            }
            if (read(fd, temp, block_size) != block_size) {
                printf("Failed to read data block\n");
                continue;
            }
            reading_data_blocks(temp, level-1);
            if (block_read == nblock) {
                return;
            }
        }
    } else {
        for (int z=0; z < 256; z++) {
            int *ptr = (int*)(block_contents + sizeof(int) * z);
            int block_num = *ptr; 
            if (lseek(fd, BLOCK_OFFSET(block_num), SEEK_SET) == -1) {
                printf("Failed to seek to data block\n");
                continue;
            }
            if (read(fd, out[njpg]+block_size*block_read, block_size) != block_size) {
                printf("Failed to read data block\n");
                continue;
            }
            block_read += 1;
            if (block_read == nblock) {
                return;
            }
        }
    }
}

int main(int argc, char **argv) {
    if (argc != 3) 
    {
        printf("expected usage: ./runscan inputfile outputfile\n");
        exit(0);
    }

    /* This is some boilerplate code to help you get started, feel free to modify
       as needed! */

    fd = open(argv[1], O_RDONLY);    /* open disk image */
    if (fd == -1) {
        printf("Failed to open disk image\n");
        exit(0);
    }

    DIR* dir = opendir(argv[2]);
    if (dir) {
        closedir(dir);
        close(fd);
        printf("Error: output directory already exist\n");
        exit(0);
    } else {
        if (mkdir(argv[2], 0777) == -1) {
            printf("Failed to create output directory\n");
            close(fd);
            exit(0);
        }
    }

    ext2_read_init(fd);

    struct ext2_super_block super;
    struct ext2_group_desc groups[num_groups];

    read_super_block(fd, &super);
    read_group_descs(fd, groups, num_groups);

    out = malloc(sizeof(char*) * 10);
    if (out == NULL) {
        printf("Error: malloc()\n");
        close(fd);
        exit(0);
    }

    for (uint i=0; i<num_groups; i++) {

        for (uint j=0; j<super.s_inodes_per_group; j++) {
            struct ext2_inode inode;

            read_inode(fd, locate_inode_table(i, groups), j, &inode, super.s_inode_size);

            if (S_ISREG(inode.i_mode)) {
                if (inode.i_size == 0) {
                continue;
                } 

                if (inode.i_block[0] == 0) {
                    continue;
                } 

                char *block_buf = malloc(block_size);
                if (block_buf == NULL) {
                    printf("malloc(): Error\n");
                    close(fd);
                    exit(1);
                }
                if (lseek(fd, (off_t)inode.i_block[0]*block_size, SEEK_SET) == -1) {
                    printf("Failed to seek to data block\n");
                    continue;
                }
                if (read(fd, block_buf, block_size) != block_size) {
                    printf("Failed to read data block\n");
                    continue;
                }

                if (block_buf[0] == (char)0xff &&
                    block_buf[1] == (char)0xd8 &&
                    block_buf[2] == (char)0xff &&
                    (block_buf[3] == (char)0xe0 ||
                    block_buf[3] == (char)0xe1 ||
                    block_buf[3] == (char)0xe8)) 
                {
                    if (inode.i_size % block_size != 0) {
                        nblock = inode.i_size / block_size + 1;
                    } else {
                        nblock = inode.i_size / block_size;
                    }
                    jpg_inode[njpg] = j+super.s_inodes_per_group*i;
                    inode_size[njpg] = inode.i_size;

                    out[njpg] = malloc(nblock*block_size);
                    if (out[njpg] == NULL) {
                        printf("malloc(): Error\n");
                        close(fd);
                        exit(1);
                    }

                    block_read = 0;
                    for (int k=0; k<15; k++) {
                        if (lseek(fd, BLOCK_OFFSET(inode.i_block[k]), SEEK_SET) == -1) {
                            printf("Failed to seek to data block\n");
                            continue;
                        }
                        if (k < 12) {
                            if (read(fd, out[njpg]+block_size*block_read, block_size) != block_size) {
                                printf("Failed to read data block\n");
                                continue;
                            }
                            block_read += 1;
                        } else if (k == 12) {
                            char temp[1024];
                            if (read(fd, temp, block_size) != block_size) {
                                printf("Failed to read data block\n");
                                continue;
                            }
                            reading_data_blocks(temp, 1);
                            if (block_read == nblock) {
                                break;
                            }
                        } else if (k == 13) {
                            char temp[1024];
                            if (read(fd, temp, block_size) != block_size) {
                                printf("Failed to read data block\n");
                                continue;
                            }
                            reading_data_blocks(temp, 2);
                            if (block_read == nblock) {
                                break;
                            }
                        } else if (k == 14) {
                            char temp[1024];
                            if (read(fd, temp, block_size) != block_size) {
                                printf("Failed to read data block\n");
                                continue;
                            }
                            reading_data_blocks(temp, 3);
                            if (block_read == nblock) {
                                break;
                            } 
                        } else {
                            printf("Error: out of data range\n");
                            close(fd);
                            exit(1);
                        }

                        if (block_read == nblock) {
                            break;
                        }
                    }

                    char out_filename_jpg[256];
                    char out_filename_detail[256];
                    if (argv[2][sizeof(argv[2])] != '/') {
                        snprintf(out_filename_jpg, sizeof(out_filename_jpg), "%s/file-%i.jpg", argv[2], j+super.s_inodes_per_group*i);
                        snprintf(out_filename_detail, sizeof(out_filename_detail), "%s/file-%i-details.txt", argv[2], j+super.s_inodes_per_group*i);
                    } else {
                        snprintf(out_filename_jpg, sizeof(out_filename_jpg), "%sfile-%i.jpg", argv[2], j*super.s_inodes_per_group*i);
                        snprintf(out_filename_detail, sizeof(out_filename_detail), "%s/file-%i-details.txt", argv[2], j+super.s_inodes_per_group*i);
                    }
                    FILE *jpg_fp = fopen(out_filename_jpg, "wb");
                    if (jpg_fp == NULL) {
                        printf("fopen: error\n");
                        close(fd);
                        exit(1);
                    }

                    if (fwrite(out[njpg], inode.i_size, 1, jpg_fp) != 1) {
                        printf("fwrite: error\n");
                        fclose(jpg_fp);
                        close(fd);
                        exit(1);
                    }
                    fclose(jpg_fp);

                    FILE *detail_fp = fopen(out_filename_detail, "wb");
                    if (detail_fp == NULL) {
                        printf("fopen: error\n");
                        close(fd);
                        exit(1);
                    }
                    fprintf(detail_fp, "%d\n%d\n%d", inode.i_links_count, inode.i_size, inode.i_uid);
                    fclose(detail_fp);

                    njpg += 1;
                }
                free(block_buf);
                block_buf = NULL;

            } else {
                continue;
            }
        }
    }
    
    for (uint i=0; i<num_groups; i++) {
        for (uint j=0; j<super.s_inodes_per_group; j++) {
            struct ext2_inode inode;

            read_inode(fd, locate_inode_table(i, groups), j, &inode, super.s_inode_size);
            
            if (S_ISDIR(inode.i_mode)) {
                char *block_buf = malloc(block_size);
                if (block_buf == NULL) {
                    printf("Error: malloc()\n");
                    close(fd);
                    exit(1);
                }
                if (lseek(fd, BLOCK_OFFSET(inode.i_block[0]), SEEK_SET) == -1) {
                    printf("Failed to seek to data block\n");
                    continue;
                }
                if (read(fd, block_buf, block_size) != block_size) {
                    printf("Failed to read the data block\n");
                    continue;
                }

                uint curr_off = 0;
                while (curr_off < block_size) {
                    struct ext2_dir_entry_2* dentry = (struct ext2_dir_entry_2*) & ( block_buf[curr_off] );
                    int name_len = dentry->name_len & 0xFF; // convert 2 bytes to 4 bytes properly
                    for (int cur_i = 0; cur_i < njpg; cur_i++) {
                        if (jpg_inode[cur_i] == dentry->inode && dentry->file_type == 1) {
                            char out_name[EXT2_NAME_LEN];
                            strncpy(out_name, dentry->name, name_len);
                            out_name[name_len] = '\0';
                            char name[256];
                            snprintf(name, sizeof(name), "%s/%s", argv[2], out_name);
                            FILE *jpg_fp = fopen(name, "wb");
                            if (jpg_fp == NULL) {
                                printf("fopen: error\n");
                                close(fd);
                                exit(1);
                            }
                            if (fwrite(out[cur_i], inode_size[cur_i], 1, jpg_fp) != 1) {
                                printf("fwrite: error\n");
                                fclose(jpg_fp);
                                close(fd);
                                exit(1);
                            }
                            fclose(jpg_fp);
                            break;
                        }
                    }
                    uint nextoff = sizeof(dentry->inode) + sizeof(dentry->rec_len) + sizeof(dentry->name_len) + sizeof(dentry->file_type) + name_len;
                    if (nextoff % 4 != 0) {
                        nextoff += 4 - nextoff % 4;
                    }
                    curr_off += nextoff;
                }
                free(block_buf);
                block_buf = NULL;
            } 
        }
    }
    
    close(fd);
    for (int i=0; i<njpg; i++) {
        free(out[i]);
        out[i] = NULL;
    }
    free(out);
    out = NULL;

    return 0;
}