# Factorial

This program computes factorial of all integers read from an input file
and prints results to STDOUT. 

The calculation is done in more threads so that already existing calculations
are not repeated. E.g. when numbers 12 and 10 are on the input, for 12 the 
program will multiply ot only twice.

## Build and Run

Maven is required to build the source code. 

```
mvn package
```
Which will generates factorial.jar program file inside "/target" folder.

```
java -jar ./target/factorial.jar <file>
```
Where &lt;file> is a path to an input file. This command executes the program.

## Usage

```
Usage: java -jar factorial.jar <file>

This program computes factorial of all integers read from an input file and prints results to STDOUT.
One line in the input file represents one number. The number must be in a range <0; 2,147,483,647>
  <file>        The input file containing one number on each line.
  -h, --help    Show this message and exit.

```

## Input file format

Each line in the input file represent one number.
Number must be an Integer. In other words in a range of Integer &lt;0; 2,147,483,647>

Input file example:
```
10
5
24
7
```

### Errors

If the input file is in an incorrect format
and/or any single line is not recognized as a relevant Integer value,
the programs exits prematurely without computing output value.

The program handles the following error categories:
1. Negative number.
2. Too large number - outside the Integer scope &lt;-2,147,483,648; 2,147,483,647>.
3. Not a number - such as text "abcd"

## Output

If no error is encountered the output is print to STDOUT in the format "number! = factorial".
E.g. "5! = 120"

Example output for the input number from the previous chapter:
```
10! = 3628800
5! = 120
24! = 620448401733239439360000
7! = 5040
```