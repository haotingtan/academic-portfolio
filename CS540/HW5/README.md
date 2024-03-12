# Linear Regression Analysis on Lake Mendota Ice Coverage

## Overview

This project applies linear regression to historical data on the number of days Lake Mendota was covered by ice each year, with the goal of understanding trends over time and making predictions for future years. Using data curated from the Wisconsin State Climatology Office, the project involves data cleaning, visualization, model training, prediction, and analysis of results.

## Objectives

- Curate a clean dataset of Lake Mendota ice coverage days from 1855-56 to 2021-22.
- Visualize the data to understand trends over time.
- Train a linear regression model to fit the data.
- Predict the number of ice coverage days for the winter of 2022-23.
- Interpret the model's implications and discuss its limitations.

## Dataset

The dataset consists of two main features for each year:
- **x**: The starting year (e.g., for 1855-56, x=1855).
- **y**: The number of days Lake Mendota was covered by ice in that year.

This data is processed and stored in a file named `hw5.csv`, following the format specified in the assignment.

## Implementation Steps

1. **Data Curation (`Question 1`)**: Process the original data to format it correctly and save it as `hw5.csv`.
2. **Visualize Data (`Question 2`)**: Plot the year against the number of ice days and save the plot as `plot.jpg`.
3. **Linear Regression (`Question 3`)**:
    - Convert the data into a matrix form suitable for linear regression analysis.
    - Compute the optimal regression coefficients using the closed-form solution.
    - Output various intermediate matrices and the final regression coefficients.
4. **Prediction (`Question 4`)**: Predict the number of ice coverage days for the year 2022-23 using the trained model.
5. **Model Interpretation (`Question 5`)**: Interpret the significance of the regression coefficient related to the year.
6. **Model Limitation (`Question 6`)**: Predict when Lake Mendota will no longer freeze based on the model, and discuss the prediction's realism.
