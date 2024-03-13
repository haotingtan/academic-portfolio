# Project 2: Loan Analysis

## Overview

Sadly, there is a long history of lending discrimination based on race
in the United States.  Lenders have literally drawn red
lines on a map around certain neighbourhoods where they would not
offer loans, based on the racial demographics of those neighbourhoods
(read more about redlining here: https://en.wikipedia.org/wiki/Redlining).
In 1975, congress passed the Home Mortgage Disclosure Act (HDMA) to
bring more transparency to this injustice
(https://en.wikipedia.org/wiki/Home_Mortgage_Disclosure_Act).  The
idea is that banks must report details about loan applications and
which loans they decided to approve.

The public HDMA dataset spans all the states and many years, and is documented here:
* https://www.ffiec.gov/hmda/pdf/2023guide.pdf
* https://cfpb.github.io/hmda-platform/#hmda-api-documentation

In this project, we'll analyze every loan application made in Wisconsin in
2021.

Things you'll practice:
* classes
* large datasets
* trees
* testing
* writing modules

## Testing

Run `python3 tester.py p2.ipynb` often and work on fixing any issues.

## Part 1: Loan Classes

### banks.json

The `__init__` of your `Bank` class should check that the given name appears in the `banks.json` file.  It should also lookup the `lei` ("Legal Entity Identifier") corresponding to the name and store that in an `lei` attribute.  In other words, `uwcu.lei` should give the LEI for UWCU, in this case "254900CN1DD55MJDFH69".

### wi.zip

The `__init__` should also read the loans from the `wi.csv` inside `wi.zip` for the given bank, and store them in a list in the `Bank` object. You already learned how to read text from a zip file in lab using `TextIOWrapper` and the `zipfile` module. The list should be an attribute of the `Bank` object.

Read the documentation and example for how to read CSV files with `DictReader` here: https://docs.python.org/3/library/csv.html#csv.DictReader.  You can combine this with what you learned about zipfiles.  When you create a `DictReader`, just pass in a `TextIOWrapper` object instead of a regular file object.

As your `__init__` loops over the loan `dict`s, it should skip any that don't match the bank's `lei`.  The loan dicts that match should get converted to `Loan` objects and appended to end of the loans list. The order of loans in your list should be the same as they appear in the CSV file.

### Special Methods

We aren't enforcing the name of the attribute storing the list of loans, but you should be able to print the last loan like this:

```python
print(uwcu.SOME_ATTRIBUTE_NAME[-1])
```

We can check how many loans there are with this:

```python
print(len(uwcu.SOME_ATTRIBUTE_NAME))
```

To keep the interface consistent, we want to be able to directly use brackets and `len` directly on `Bank` objects, like this:
* `uwcu[-1]`
* `len(uwcu)`

Add the special methods to `Bank` necessary to make this work.

### Testing

Running `python3 tester.py p2.ipynb` does two things:

1. compute a score based on whether answers in your `p2.ipynb` are correct
2. get a second score by running `module_tester.py`, which exercises various classes/methods in `loan.py` (already done) and `search.py` (the next part)

Your total score is an average of these two components.

Try running `module_tester.py` now.  You should see the following (assuming you haven't worked ahead on `search.py`):

```
{'score': 40.0, 'errors': ['could not find search module']}
```

It should actually be possible to get 50.0 from `module_tester.py`
after just completing `loans.py`, but we left some tests undone so you
can get practice writing tests for yourself.

Open `module_tester.py` and take a look at the `loans_test`.  The
function tries different things (e.g., creating different `Loan` and
`Applicant` objects and calling various methods).

Whenever something works, a global variable `loans_points` is
increased.  There are also asserts, and if any fail, the test stops
giving points.  For example, here's a bit that tests the `lower_age`
method:

```python
    # lower_age
    assert loans.Applicant("<25", []).lower_age() == 25
    assert loans.Applicant("20-30", []).lower_age() == 20
    assert loans.Applicant(">75", []).lower_age() == 75
    loans_points += 1
```

You should add some additional test code of your choosing (based on
where you think bugs are most likely to occur).  When the additional
code shows that `loans.py` works correctly, it should add 4 points to
`loan_points`.  You could do this is one step (`loans_points += 4`),
or better, divide the points over the testing of a few different
aspects.

## Part 2: Binary Search Tree

## Part 3: First Home Bank Analysis

rest of this project in `p2.ipynb`.

For the following questions, create a `Bank` object for the bank named "First Home Bank".

### Q1: what is the average interest rate for the bank?

Skip missing loans where the interest rate is not specified in your calculation.

### Q2: how many applicants are there per loan, on average?

### Q3: what is the distribution of ages?

Answer with a dictionary, like this:

```
{'65-74': 21, '45-54': 21, ...}
```

### Tree of Loans for Q4 and Q5

For the following questions, create a `BST` tree.  Loop over every loan in the bank, adding each to the tree.  The `key` passed to the `add` call should be the `.interest_rate` of the `Loan` object, and the `val` passed to `add` should be the `Loan` object itself.

### Q4: how many interest rate values are missing?

Don't loop over every loan to answer.  Use your tree to get and count loans with missing rates (that is, `-1`).

### Q5: how tall is the tree?

The height is the number of edges in the path from the root to the deepest node.  Write a recursive function or method to answer.

## Part 4: University of Wisconsin Credit Union Analysis

Build a new `Bank` and corresponding `BST` object as before, but now for "University of Wisconsin Credit Union".

### Q6: how long does it take to add the loans to the tree?

Answer with a plot, where the x-axis is how many loans have been added so far, and the y-axis is the total time that has passed so far.  You'll need to measure how much time has elapsed (since the beginning) after each `.add` call using `time.time()`.

**Note:** performance and the amount of noise will vary from one virtual machine to another, so your plot probably won't be identical (this applies to the other performance plots too).

<img src="q6.png">

### Q7: how fast are tree lookups?

Create a bar plot with two bars:
1. time to find missing `interest_rate` values (`-1`) by looping over every loan and keeping a counter
2. time to compute `len(NAME_OF_YOUR_BST_OBJECT[-1])`

<img src="q7.png">

### Q8: How many applicants indicate multiple racial identities?

Answer with a bar graph, where the y axis should represent the number of applicants with the corresponding x-axis represents number of race selections, be sure to label your axes.

**Note:** The x-axis should be the number of race identities selected by the applicants, not the individual races. Please keep all number of race selections (Do NOT exclude bars which have less than or equal to one race selection).

### Q9: How many leaf nodes are in the tree?

Write a recursive function or method to count the number of leaf nodes present in the given BST.

### Q10: What is the fifth largest interest rate in the Bank BST?

Write a **recursive** function or method that can return the top 5 (or N) keys for any subtree.

