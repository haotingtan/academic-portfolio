# Project 6: Regression Models

## Overview

We will be making predictions about census data for Wisconsin using
regression models. You'll need to extract data from four files to
construct DataFrames suitable for training during this project:

* `counties.geojson` - population and boundaries of each county in Wisconsin
* `tracts.shp` - boundaries of each census tract (counties are subdivided into tracts)
* `counties_tracts.db` - details about housing units per tract
* `land.zip` - details about land use (farm, forest, developed, etc.)


## Part 1: Predicting Population using Area

### Q1: How many counties are in Wisconsin?

Read `counties.geojson` into a GeoDataFrame and use it to calculate the number of counties in Wisconsin.

### Q2: What is the population of each county in WI?

Create a geopandas plot that has a legend using the population from
the `POP100` column.

The US Census Bureau does some surveys that attempt to sample the
population and others (like the decennial one) that attempt to count
everybody.  "POP100" means this is there attempt to count 100% of the
population (no sampling).  Similarly, "HU100" is a 100% count of
housing units.

### Feature 1: `AREALAND`

Let's add an `AREALAND` column to your GeoDataFrame of counties so
that we can try to predict population based on area.

Use data from the database to add an `AREALAND` column to your
GeoDataFrame.  The order of rows in your GeoDataFrame should not
change as part of this operation.

After you've added `AREALAND` to your GeoDataFrame, use
`train_test_split` from `sklearn` to split the rows into `train` and
`test` datasets.

* by default, `train_test_split` randomly shuffles the data differently each time it runs.  Pass `random_state=250` as a parameter so that it shuffles the same way as it did for us (so that you get the answers the tester expects).
* Pass `test_size=0.25` to make the test set be one quarter of the original data and the other three quarters remaining in the training set.

### Q3: What are the counties in the test dataset?

Answer with a list, in the same order as the names appear in the original DataFrame. 

### Q4: How much variance in the `POP100` can a `LinearRegression` model explain based only on `AREALAND`?

`fit` the model to your `train` dataset and `score` it on your `test` dataset.

### Q5: What is the predicted population of a county with 400 square miles of area, according to the model?

Consult the [Census documentation](https://tigerweb.geo.census.gov/tigerwebmain/TIGERweb_attribute_glossary.html) to learn what units the data is in, and do any conversions necessary to answer the question.  Assume there are exactly 2.59 square kilometers per square mile for the purposes of your calculation.

## Part 2: Predicting Population using Housing Units

### Feature 2: `HU100` (housing units)

Look at the `tracts` table inside `counties_tracts.db` and find the
`HU100` column. Add a `HU100` column to your GeoDataFrame of counties,
similar to how you added `AREALAND`.

**The query to get housing units per county is more complicated than the
one for AREALAND**.  County names are in the `counties` table and
`HU100` values are in the `tracts` table.  Fortunately, both tables
have a `COUNTY` column you can use to combine. Make sure to get the
total number of housing units for each county from the `tracts` table
by summing the housing units in each tract of the county.

Split your updated GeoDataFrame into a train and test set, the same
way you did previously.

### Q6: What are the counties in the test dataset?

Answer with a `list`, in the same order as the names appear in the DataFrame.

### Q7: What are the HU100 values for the counties in the test dataset?

Answer with a `dict`.

### Q8: How much variance in the `POP100` can a `LinearRegression` model explain based only on `HU100`?

Answer with the average of 5 scores produced by `cross_val_score` on the training data.

### Q9: What is the standard deviation of the cross validation scores from Q8?

Refrain from using statistics.stdev to calculate standard deviation. Variance is the average squared deviations from the mean, while standard deviation is the square root of this number.

### Q10: What is the formula relating POP100 and HU100?

Fit your model to the training dataset to find the answer. Round the coefficient and intercept to 2 decimal places. Format the answer according to the following formula:

```
POP100 = ????*HU100 + ????
```

### Q11: What is the relationship between HU100 and POP100, visually?

Answer with a scatter plot showing the actual values (both train and test) and the predicted fit line based on the model.

## Part 3: Land Use Features

### Q12: How many numbers in matrix `A` are between 3 and 6 (inclusive)?

You can paste the following to define `A`:

```python
A = np.array([
    [0,0,5,8,4],
    [1,2,4,0,3],
    [2,4,0,9,2],
    [3,5,2,1,1],
    [0,5,0,1,0]
])
```

### Q13: How does Brown County look?

### Q14: What portion of Brown County is "Open Water"?

### Q15: What is the Relationship Between POP100 and ________________?

Replace the blank with a cell count for a land type of your choosing.
Show a scatter plot where each point corresponds to a county.

## Part 4: 
1. start with a GeoDataFrame dataset loaded from `tracts.shp`
2. add feature columns to that dataset for every key in land_use(found under q14), with the column value being the number of units present of that land type, based on raster data from `lands.zip`.
Note : Computation takes considerable amount of time with raster data so try to minimize masking operations 
3. split your GeoDataFrame into train/test using `random_state=300` and `test_size=0.20`.
4. Construct a regression model to predict POP100. Use all of the new columns you created in step 2 as the features for training. 
6. Write a comment discussing what the graph is showing you and how you might use that information in building your own model(Part 2). 

### Q16: What features does your model rely on most?

Answer with a bar plot showing feature and coefficient of feature in our trained model.

Include a comment discussing what the graph is showing you and what it means for our model.

In terms of tester scores, this question is weighted to be worth 2 regular questions.

## Part 5:

1. Construct at least 2 regression models predicting POP100. They should differ in terms of (a) what columns they use and/or (b) whether or not they're preceded by transformers in an sklearn Pipeline
2. Perform cross validation on both your models over your training dataset
3. Write a comment recommending which model you recommend for this prediction task. Factors you **must** consider are (a) mean of cross validation scores is high, (b) variance of cross validation scores is low. Factors you might consider are (c) model is simple, and (d) anything else you think is important.
4. Fit your recommended model to the entire training dataset and score it against the test dataset

### Q17: How does your recommended model score against the test dataset?
