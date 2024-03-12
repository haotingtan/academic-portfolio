# Probabilistic Language Identification

## Project Description

This project involves developing a Python program capable of identifying the language of a given text as either English or Spanish. This identification is based on the probabilistic analysis of character frequencies within the text. The project is designed to apply concepts of probability, specifically Bayes' rule, to real-world problems like language identification.

The program processes a text file containing a business letter, which is "digitally shredded" by counting the occurrences of each alphabet character, ignoring case and non-alphabetic characters. Based on these counts, the program uses provided multinomial probability parameters for English and Spanish to estimate the most likely language of the original letter.

## Features

- **Digital Shredding:** The program reads a plain text file and counts the frequency of each alphabet character, disregarding case and non-alphabet characters.
- **Probabilistic Language Identification:** Utilizes Bayes' rule and multinomial probability models to estimate whether the given text is in English or Spanish, considering computational efficiency and avoiding numerical underflow.
- **Robust Computational Methods:** Implements log-space computation to handle large numbers and avoid underflow, ensuring the program can accurately process long texts.

## Usage

1. Place the text file you want to analyze in the same directory as the program, naming it `letter.txt`.
2. Ensure you have the `e.txt` and `s.txt` files in the directory, which contain the multinomial probability parameters for English and Spanish, respectively.
3. Run the program using Python 3 with the command: `python3 hw2.py`
4. The program will output the analysis to the terminal, including character counts, log probabilities, and the estimated language of the text.

## File Descriptions

- `hw2.py`: The main Python script to be executed. It contains the logic for reading the text file, counting character frequencies, computing probabilities, and determining the language.
- `e.txt`: Contains the multinomial parameter vector for English.
- `s.txt`: Contains the multinomial parameter vector for Spanish.
- `samples/`: A directory with sample letters and their expected output for testing and reference.

