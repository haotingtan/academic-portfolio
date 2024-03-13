#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <errno.h>


char** split_line(char *lineptr, int *num_args) {
    int cur_len = 0;
    int cap = 8;
    char **tokens = malloc(sizeof(char*) * cap);

    if (tokens == NULL) {
        return NULL;
    }

    const char *delim = " \t\n";
    char *token = strtok(lineptr, delim);

    while (token != NULL) {
        tokens[cur_len] = token;
        cur_len++;

        if (cur_len >= cap) {
            cap = (int) (cap * 1.5);
            tokens = realloc(tokens, sizeof(char*) * cap);
            if (tokens == NULL) {
                free(tokens);
                return NULL;
            }
        }

        token = strtok(NULL, delim);
    }
    tokens[cur_len] = NULL;
    if (tokens[0] == NULL) {
        free(tokens);
        return NULL;
    }
    *num_args = cur_len;
    return tokens;
}


int check_multi_cmd(char** tokens, int num_args, int delim_ind[]) {
    int num_delim = 0;
    for (int i=0; i<num_args; i++) {
        if (strcmp(tokens[i], ";") == 0) {
            delim_ind[num_delim] = i;
            num_delim++;
        }
    }
    return num_delim;
}


char check_redirection(char** tokens, int num_args) {
    int redirect = 0;
    for (int i=0; i<num_args; i++) {
        if (strcmp(tokens[i], ">") == 0) {
            if (tokens[i+1] == NULL) {
                return -1;
            } 
            if (redirect != 0) {
                return -1;
            }
            redirect = i+1;
            i++;
        }
    }
    return redirect;
}


char ***check_pipe(char** tokens, int num_args, int cmd_num) {
    char ***cmd = malloc(sizeof(char**) * cmd_num);
    if (cmd == NULL) {
        return NULL;
    }
    int curr_cmd = 0;
    int pipe_index = -1;
    for (int i=0; i<num_args; i++) {
        if (strcmp(tokens[i], "|") == 0) {
            cmd[curr_cmd] = malloc(sizeof(char*) * (i-pipe_index));
            if (cmd[curr_cmd] == NULL) {
                free(cmd);
                return NULL;
            }
            int k = 0;
            for (int j=pipe_index+1; j<i; j++) { 
                cmd[curr_cmd][k] = tokens[j];
                k++;
            }
            cmd[curr_cmd][k] = NULL;
            curr_cmd++;
            pipe_index = i;
        }
    }
    int k = 0;
    cmd[curr_cmd] = malloc(sizeof(char*) * (num_args-pipe_index));
    if (cmd[curr_cmd] == NULL) {
        free(cmd);
        return NULL;
    }
    for (int j=pipe_index+1; j<num_args; j++) { 
        cmd[curr_cmd][k] = tokens[j];
        k++;
    }
    cmd[curr_cmd][k] = NULL;
    return cmd;
}


int spawn_proc(int in, int out, char** cmd) {
    pid_t pid;

    int valfd[2];
    int val = 0;

    pipe(valfd);

    if ((pid = fork()) == 0) {
        close(valfd[0]);

        if (in != 0) {
            dup2(in, 0);
            close(in);
        }
        if (out != 1) {
            dup2(out, 1);
            close(out);
        }

        execv(cmd[0], cmd);
        val = -1;
        write(valfd[1], &val, sizeof(val));
        close(valfd[1]);
        exit(1);
    } else {
        wait(NULL);
        close(valfd[1]);
        read(valfd[0], &val, sizeof(val));
        close(valfd[0]);
        if (val == -1) {
            return -1;
        } else {
            return pid;
        }
    }
}   


void fork_pipes(int n, char*** cmd) {
    int i;
    int in, pipefd[2];

    in = 0;

    for (i=0; i<n-1; ++i) {
        pipe(pipefd);
        int num = spawn_proc(in, pipefd[1], cmd[i]);
        if (num == -1) {
            return;
        }
        close(pipefd[1]);
        in = pipefd[0];
    }

    if (in != 0) {
        dup2 (in, 0);
    } 
    execv(cmd[i][0], cmd[i]);
}


int exec_smash(char** tokens, int num_args) {
    char error_message[30] = "An error has occurred\n";
    int buffer_size = 128;
    char *dict = malloc(sizeof(char) * buffer_size); 
    int curr_time = 0;
    int loop_time = 1;

    // built-in command:
    if (strcmp(tokens[0], "loop") == 0) {
        if (num_args < 3 || (loop_time=atoi(tokens[1])) == 0) {
            write(STDERR_FILENO, error_message, strlen(error_message)); 
            return -1;
        } else {
            tokens[0]= NULL;
            tokens[1] = NULL;
            int i = 0;
            while (tokens[i+2] != NULL) {
                tokens[i] = tokens[i+2];
                tokens[i+2] = NULL;
                i++;
            }
            num_args -= 2;
        }
    } 
    
    if (strcmp(tokens[0], "exit") == 0) {
        if (num_args == 1) {
            exit(0);
        } else {
            return -1;
        }
    } else if (strcmp(tokens[0], "cd") == 0) {
        while (curr_time != loop_time) {
            int error = chdir(tokens[1]);
            if (num_args != 2 || error != 0) {
                write(STDERR_FILENO, error_message, strlen(error_message)); 
                return -1;
            } 
            curr_time++;
        }
        return 0;
    } else if (strcmp(tokens[0], "pwd") == 0) {
        if (dict == NULL) {
            write(STDERR_FILENO, error_message, strlen(error_message)); 
            return -1;
        }
        while (curr_time != loop_time) {
            dict = realloc(dict, sizeof(char) * buffer_size); 
            char* error = getcwd(dict, buffer_size);
            if (num_args != 1 || error == NULL) {
                if (errno == ERANGE) {
                    while (error == NULL) {
                        buffer_size = buffer_size * 2;
                        dict = realloc(dict, sizeof(char) * buffer_size);
                        if (dict == NULL) {
                            write(STDERR_FILENO, error_message, strlen(error_message)); 
                            return -1;
                        }
                        error = getcwd(dict, buffer_size);
                    }
                } else {
                    write(STDERR_FILENO, error_message, strlen(error_message)); 
                    return -1;
                }
            } 
            printf("%s\n", dict);
            curr_time++;
        }

        free(dict);
        dict = NULL;
        return 0;
    } 

    // check redirection
    int file[loop_time]; 
    char* filename = NULL;
    int file_ind = check_redirection(tokens, num_args);
    if (file_ind == -1) {
        write(STDERR_FILENO, error_message, strlen(error_message));
        return -1;
    } else if (file_ind != 0) {
        if (strcmp(tokens[num_args-2], ">") != 0) {
            write(STDERR_FILENO, error_message, strlen(error_message));
            return -1;
        }
        filename = tokens[file_ind];
        for (int i=0; i<2; i++) {
            tokens[num_args-i-1] = NULL;
        }
        num_args -= 2;
    }

    // check if pipe
    char*** cmd = NULL;
    int pipe_num = 0;
    for (int i=0; i<num_args; i++) {
        if (strcmp(tokens[i], "|") == 0) {
            if (tokens[i+1] == NULL || strcmp(tokens[i+1], "|") == 0) {
                write(STDERR_FILENO, error_message, strlen(error_message));
                return -1;
            } 
            pipe_num++;
        } 
    }
    if (pipe_num != 0) {
        cmd = check_pipe(tokens, num_args, (pipe_num+1));
        if (cmd == NULL) {
            write(STDERR_FILENO, error_message, strlen(error_message)); 
            return -1;
        }
    }
    

    while (curr_time != loop_time) {
        int valfd[2];
        int val = 0;

        pipe(valfd);

        int pid = fork();
        if (pid < 0) {
            write(STDERR_FILENO, error_message, strlen(error_message));
            return -1;
        }

        // child 
        if (pid == 0) {  
            if (file_ind != 0) {
                file[curr_time] = open(filename, O_CREAT|O_TRUNC|O_WRONLY, 0644);
                if (file[curr_time] == -1) {
                    write(STDERR_FILENO, error_message, strlen(error_message));
                    exit(1);
                }
                dup2(file[curr_time], STDOUT_FILENO);
                close(file[curr_time]);
            }   
            if (pipe_num > 0) {
                fork_pipes((pipe_num+1), cmd); 
            } else {
                execv(tokens[0], tokens);
            }
            write(STDERR_FILENO, error_message, strlen(error_message)); 
            close(valfd[0]);
            val = -1;
            write(valfd[1], &val, sizeof(val));
            close(valfd[1]);
            exit(1);
        } else {
            // parent
            wait(NULL);
            close(valfd[1]);
            read(valfd[0], &val, sizeof(val));
            close(valfd[0]);
        }
        
        if (val == -1) {
            break;
        }
        
        curr_time++;
    }

    if (cmd != NULL) {
        for(int i=0; i<(pipe_num+1); i++) {
            free(cmd[i]);
            cmd[i] = NULL;
        }
        free(cmd);
        cmd = NULL;
    }

    return 0;
}



int main(int argc, char* argv[]) {
    char *prompt = "smash> ";
    char error_message[30] = "An error has occurred\n";

    if (argc != 1) {
        write(STDERR_FILENO, error_message, strlen(error_message)); 
    }

    while (1) {
        printf("%s", prompt);
        char *lineptr = NULL;
        int num_args = 0;
        size_t n = 0; 

        // read input
        if (getline(&lineptr, &n, stdin) == -1) {
            write(STDERR_FILENO, error_message, strlen(error_message)); 
            continue;
        }

        // split the arguments
        char **tokens = split_line(lineptr, &num_args);
        if (tokens == NULL) {
            free(lineptr);
            continue;
        }

        // check if multiple commands
        int delim_ind[num_args];
        int num_delim = check_multi_cmd(tokens, num_args, delim_ind);
        if (num_delim == num_args) {
            free(lineptr);
            free(tokens);
            continue;
        }

        fflush(stdout);  
        
        if (num_delim > 0) {
            int start = 0;
            int num = 0;
            char **single_cmd = NULL; 
            for (int i=0; i<num_delim; i++) {
                single_cmd = malloc(sizeof(char*) * (num_args+1));
                if (single_cmd == NULL) {
                    write(STDERR_FILENO, error_message, strlen(error_message)); 
                    continue;
                }
                if (start == delim_ind[i]) {
                    start++;
                    free(single_cmd);
                    single_cmd = NULL;
                    continue;
                }
                num = delim_ind[i]-start;
                int j = 0;
                while (start != delim_ind[i]) {
                    single_cmd[j] = tokens[start];
                    j++;
                    start++;
                }
                single_cmd[j] = NULL;
                exec_smash(single_cmd, num);
                start++;
                free(single_cmd);
                single_cmd = NULL;
            }

            num = num_args - start;
            if (num >= 1) {
                single_cmd = malloc(sizeof(char*) * (num_args+1));
                if (single_cmd == NULL) {
                    write(STDERR_FILENO, error_message, strlen(error_message)); 
                } else {
                    int j = 0;
                    while (start != num_args) {
                        single_cmd[j] = tokens[start];
                        j++;
                        start++;
                    }
                    single_cmd[j] = NULL;
                    exec_smash(single_cmd, num);
                    free(single_cmd);
                    single_cmd = NULL;
                }
            }

        } else {
            exec_smash(tokens, num_args);
        }

        free(lineptr);
        free(tokens);
        tokens = NULL;
    }

    return 0;
}