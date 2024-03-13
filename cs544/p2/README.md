# P2: Predicting COVID Deaths with PyTorch

## Overview

In this project, we'll use PyTorch to create a regression model that
can predict how many deaths there will be for a WI census tract, given
the number of people who have tested positive, broken down by age.
The train.csv and test.csv files we provide are based on data from
https://dhsgis.wi.gov/, downloaded in Spring of 2023 (it appears the
dataset is no longer available).

Learning objectives:
* multiply tensors
* check whether GPUs are available
* optimize inputs to minimize outputs
* use optimization to optimize regression coefficients

## Part 1: Setup

Build the Dockerfile we give you (feel to make edits if you like) to
create your environment.  Run the container, setup an SSH tunnel, and
open JupyterLab in your browser.  Create a notebook called `p2.ipynb`
in the `nb` directory.

Use train.csv and test.csv to construct four PyTorch float64 tensors:
`trainX`, `trainY`, `testX`, and `testY`.  

#### Q1: about how many bytes does trainX consume?

#### Q2: what is the biggest difference we would have any one cell if we used float16 instead of float64?

#### Q3: is a CUDA GPU available on your VM?

## Part 2: Prediction with Hardcoded Model

Let's predict the number of COVID deaths in the test dataset under the
assumption that the deathrate is 0.004 for those <60 and 0.03 for those >=60.
Encode these assumptions as coefficients in a tensor by pasting
the following:

```python
coef = torch.tensor([
        [0.0040],
        [0.0040],
        [0.0040],
        [0.0040],
        [0.0040],
        [0.0040], # POS_50_59_CP
        [0.0300], # POS_60_69_CP
        [0.0300],
        [0.0300],
        [0.0300]
], dtype=trainX.dtype)
coef
```

#### Q4: what is the predicted number of deaths for the first census tract?

Multiply the first row `testX` by the `coef` vector and use `.item()`
to print the predicted number of deaths in this tract.

#### Q5: what is the average number of predicted deaths, over the whole testX dataset?

## Part 3: Optimization

Let's say `y = x^2 - 8x + 19`.  We want to find the x value that minimizes y.

#### Q6: first, what is y when x is a tensor containing 0.0?


#### Q7: what x value minimizes y?

Write an optimization loop that uses `torch.optim.SGD`. 

## Part 4: Linear Regression

Use the `torch.zeros` function to initialize a 2-dimensional `coef`
matrix of size and type that allows us to compute `trainX @ coef` (we
won't bother with a bias factor in this exercise).

#### Q8: what is the MSE (mean-square error) when we make predictions using this vector of zero coefficients?

You'll be comparing `trainX @ coef` to `trainY`


#### Optimization

Seed torch random number generation with **544**.

Setup a training dataset and data loader like this:

```python
ds = torch.utils.data.TensorDataset(trainX, trainY)
dl = torch.utils.data.DataLoader(ds, batch_size=50, shuffle=True)
```

Write a training loop to improve the coefficients.  Requirements:
* use the `torch.optim.SGD` optimizer
* use 0.000002 learning rate
* run for 500 epochs
* use `torch.nn.MSELoss` for your loss function

#### Q9: what is the MSE over the training data, using the coefficients resulting from the above training?

#### Q10: what is the MSE over the test data?