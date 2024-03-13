#!/bin/bash

# Lin Ha	lha2@wisc.edu
# Haoting Tan	htan47@wisc.edu

# download the zip file
wget -O hdma-wi-2021.zip https://pages.cs.wisc.edu/~harter/cs544/data/hdma-wi-2021.zip

# unzip the file
unzip -o hdma-wi-2021.zip

# check the word counts
Multifamily_count=$(grep -c "Multifamily" hdma-wi-2021.csv)

# output the result
echo "$Multifamily_count"

exit 0
