# P1: Badger-Fortune

In this assignment, you will build a command line fortune telling utlity.

## Learning Objectives:

-   Re-familiarize yourself with the C programming language
-   Familiarize yourself with a shell / terminal / command-line of UNIX
-   Learn about how UNIX command line utilities are implemented

You will build is called badger-fortune. This tool takes a fortune database file, and either a fortune number or a batch file of fortune numbers, and outputs the correct fortunes to either the file specified on the command line or STDOUT.

Here is how an example successful call would look in number mode to STDOUT.

```         
prompt> ./badger-fortune -f fortune.txt -n 1
"... the educated person is not the person who can answer the questions, but
the person who can question the answers."
      ― Theodore Schick Jr., in The_Skeptical_Inquirer, March/April, 1997
```

Here is how an example successful call would look in batch mode to STDOUT.

\$ batch.txt contains:

```         
1 
3
```

## Format of output to STDOUT and Output Files:

### Number Mode

-   Output to STDOUT and output file consists of simiply the fortune as it is formatted in the fortune file. It should contain the same newline and white space locations.

### Batch Mode

-   Output to STDOUT and output file consists of each of the fortune printed as it is formatted in the fortune file followed by two newlines. The fortunes should contain any newlines and white space which appear in the fortune file.

## Known Issues

-   The implementation is expected to handle various error cases, such as invalid arguments, missing files, or empty files, by returning specific error messages.

-   Detailed error handling and edge case management are crucial parts of the utility's robustness.

-   If there are specific issues or unimplemented features in your version, list them here.

## Acknowledgments

The assignment is inspire by this fortune utility, <http://software.clapper.org/fortune/#the-fortune-cookie-database> Links to an external site., by Brian M. Clapper, and several of the tests utilize his fortune databse found here, <https://github.com/bmc/fortunes> Links to an external site..
