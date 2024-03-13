# Project 3: Build Unix Shell

In this project, you'll build a simple Unix shell. The shell is the heart of the command-line interface, and thus is central to the Unix/C programming environment. Mastering use of the shell is necessary to become proficient in this world; knowing how the shell itself is built is the focus of this project.

-   **Objective**: To further familiarize yourself with the Linux programming environment. To learn how processes are created, destroyed, and managed. To gain exposure to the necessary functionality in shells.

## Program Specifications

### Basic Shell: `smash`

-   `smash` is an interactive loop: it repeatedly prints a prompt `smash>` (note the space after the greater-than sign), parses the input, executes the command specified on that line of input, and waits for the command to finish.
-   The shell can be invoked with no arguments. Anything else is an error.

### Built-in Commands

-   Implement `exit`, `cd`, and `pwd` as built-in commands.
-   `exit`: Exits the shell. It is an error to pass any arguments to `exit`.
-   `cd`: Changes directories. It always takes one argument (0 or \>1 args should be signaled as an error).
-   `pwd`: Prints the current working directory.

### Redirection

-   Supports redirection of standard output to a file with a slight twist: the standard error output of the program should also be rerouted to the same file.

### Multiple Commands

-   Supports executing multiple commands in a single line, separated by semicolons.

### Pipes

-   Supports using pipes to connect the STDOUT of one command to the STDIN of another.

### Loops

-   Supports a built-in command `loop` to execute a command multiple times.

## Error Handling

-   The one and only error message to use for any error:

```         
char error_message[30] = "An error has occurred\n";
write(STDERR_FILENO, error_message, strlen(error_message));
```
