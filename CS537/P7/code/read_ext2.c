#include <stdio.h>
#include "read_ext2.h"

/* implementations credit to
 * Smith College
 * http://www.science.smith.edu/~nhowe/Teaching/csc262/oldlabs/ext2.html
 */

unsigned int block_size = 1024;         /* default 1kB block size */
unsigned int inodes_per_block = 0;                      /* number of inodes per block */
unsigned int itable_blocks = 0;                         /* size in blocks of the inode table */
unsigned int blocks_per_group = 0;
unsigned int num_groups = 0;
unsigned int inodes_per_group = 0;



int debug = 1;          //turn on/off debug prints


/* read the first super block to initialize common variables */
void ext2_read_init(int fd)
{
	struct ext2_super_block super;
	lseek(fd, BASE_OFFSET, SEEK_SET);        /* position head above super-block */
        read(fd, &super, sizeof(struct ext2_super_block));              /* read super-block */

        if (super.s_magic != EXT2_SUPER_MAGIC) {
                fprintf(stderr, "read_super_block: Not a Ext2 filesystem\n");
                exit(1);
        }

        block_size = 1024 << super.s_log_block_size;
		inodes_per_block = block_size /  super.s_inode_size;		/* number of inodes per block */
		itable_blocks = super.s_inodes_per_group / inodes_per_block;		/* size in blocks of the inode table */
		blocks_per_group = super.s_blocks_per_group;
		num_groups = (super.s_blocks_count + blocks_per_group - 1) / blocks_per_group;
		inodes_per_group = super.s_inodes_per_group;

		
		if (debug)
		{
			printf("Reading first super-block from device: \n"
				   "Block size                    : %u\n"
				   "number of inodes in a block   : %u\n"
				   "Inode table size in blocks    : %u\n"
				   "Blocks per group              : %u\n"
				   "number of block groups        : %u\n"
				   ,
				   block_size,
				   inodes_per_block,
				   itable_blocks,
				   blocks_per_group,
				   num_groups);
		}
		
		
}

int isPowerOf(int m, int n) 
{
    while (m != 1) 
	{
		if (m % n != 0)
			return 0;
        else
			m /= n;
    }

    return 1;
}

//how many powers of n are below m?
int powersBelow(int m, int n) 
{
	int cnt = 0;
    while (m != 1) 
	{
		if (m % n != 0)
			return cnt;
        m /= n;
		cnt++;
    }

    return cnt;
}

static inline int has_blockdescriptor_copy(int ngroup) 
{
	return ngroup == 0 ||
		ngroup == 1;
}

static inline int has_superblock_copy(int ngroup) 
{
	return ngroup == 0 ||
		ngroup == 1 ||
		isPowerOf(ngroup, 3) ||
		isPowerOf(ngroup, 5) ||
		isPowerOf(ngroup, 7) ;
}

/* read the super block copy at group 0 */
int read_super_block( int                      fd,        /* the disk image file descriptor */
					   struct ext2_super_block *super      /* where to put the super block */
						)
{
		lseek(fd, BASE_OFFSET, SEEK_SET);
	    //lseek(fd, BASE_OFFSET + BLOCK_OFFSET(blocks_per_group * ngroup), SEEK_SET);        /* position head above super-block */
        read(fd, super, sizeof(struct ext2_super_block));              /* read super-block */
		
        if (super->s_magic != EXT2_SUPER_MAGIC) {
                fprintf(stderr, "read_super_block: Not a Ext2 filesystem\n");
                exit(1);
        }

        block_size = 1024 << super->s_log_block_size;
		
		if (debug)
		{
			printf("Reading super-block from device: \n"
				   "Inodes count            : %u\n"
				   "Blocks count            : %u\n"
				   "First data block        : %u\n"
				   "Block size              : %u\n"
				   "log2(Block size)        : %u\n"
				   "Blocks per group        : %u\n"
				   "Inodes per group        : %u\n"
				   "First non-reserved inode: %u\n"
				   "Size of inode structure : %hu\n"
				   ,
				   super->s_inodes_count,
				   super->s_blocks_count,
				   super->s_first_data_block,
				   block_size,
				   super->s_log_block_size,
				   super->s_blocks_per_group,
				   super->s_inodes_per_group,
				   super->s_first_ino,          /* first non-reserved inode */
				   super->s_inode_size);
		}
		
		
		inodes_per_block = block_size / super->s_inode_size;		/* number of inodes per block */
		itable_blocks = super->s_inodes_per_group / inodes_per_block;		/* size in blocks of the inode table */
		
		return 0;
}

/* Read the group-descriptor table in the first block group */
int read_group_descs( int                      fd,        /* the disk image file descriptor */
				      struct ext2_group_desc *groups,      /* where to put the group-descriptor table; it takes one page */
					  int					  num_groups  /* how many groups exist in the file system; calculated using the #blocks in the fs image divided by blocks per group  */
					)
{
		lseek(fd, BLOCK_OFFSET( BASE_OFFSET + 1 ), SEEK_SET);	// super block is one block
		
		read(fd, groups, num_groups * sizeof(struct ext2_group_desc) );


		if (debug)
		{
			for (int i = 0; i < num_groups; i++) {
				struct ext2_group_desc group = groups[i];
				printf("Reading group-descriptor from device:\n"
						"Blocks bitmap block: %u\n"
						"Inodes bitmap block: %u\n"
						"Inodes table block : %u\n"
						"Free blocks count  : %u\n"
						"Free inodes count  : %u\n"
						"Directories count  : %u\n"
						,
						group.bg_block_bitmap,
						group.bg_inode_bitmap,
						group.bg_inode_table,
						group.bg_free_blocks_count,
						group.bg_free_inodes_count,
						group.bg_used_dirs_count);    /* directories count */
			}
			
		}
		return 0;
}

/* calculate the start address of the inode table in the nth group */
off_t locate_inode_table( int ngroup, const struct ext2_group_desc *groups      /* the group-descriptor table */
							    )
{
	return BLOCK_OFFSET( groups[ngroup].bg_inode_table );
}

/* calculate the start address of the data blocks in the first group */
off_t locate_data_blocks( int ngroup, const struct ext2_group_desc *groups      /* the first group-descriptor */
							    )
{
	return BLOCK_OFFSET( groups[ngroup].bg_inode_table + itable_blocks );
}

void read_inode(fd, offset, inode_no, inode, s_inode_size)
     int                            fd;        /* the floppy disk file descriptor */
     off_t 			   				offset;    /* offset to the start of the inode table */
     int                            inode_no;  /* the inode number to read  */
     struct ext2_inode             *inode;     /* where to put the inode */
	 __u16							s_inode_size;
{
        lseek(fd, offset + (inode_no-1)*s_inode_size, SEEK_SET);
        read(fd, inode, sizeof(struct ext2_inode));
}
