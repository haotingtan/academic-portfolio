# P5: Selling Laptops: Smart Marketing

## Overview

You are the owner of a retail website. You're planning on running a
promotion on a laptop and you want to send emails out about
it. However, you only want to send it to people that may be interested
in it so as not to annoy people that aren't interested.  You're
looking to use your data about who clicked on similar emails in 2020
to help you predict which users may be interested in the promotion.

You can decide what features to consider and how to create your
classifier.  Your grade will correspond to the accuracy of your
predictions.  50% accuracy and below will give a grade of 0%, whereas
accuracy of 75% and above will give a grade of 100%; any accuracy
between 50 and 75 will be rescaled to a 0-100% grade.  Some models can
get better than 90% accuracy, so we encourage you to keep improving
your model beyond what is necessary for full credit if you have time.

You should create your classifier in a `main.py` file, which will be
the only thing you'll submit.

You can collaborate with your team on the entire project (no
individual part!).  As usual, you cannot work with anybody outside of
your team and can only get help from 320 staff.

## Dataset

We have generated 3 datasets:

1. train
2. test1
3. test2 (secret)

You'll fit your classifier to the training data, then estimate your
accuracy using the test1 data.  We'll compute your final grade based
on test2 data, which is similar but not identical to test1 data.

Each of the 3 datasets consist of 3 files (so there are 9 files total, of which we give you 6):

1. `???_users.csv`: information about each user
2. `???_logs.csv`: details about webpages visited by each user
3. `???_y.csv`: y=1 means they clicked the email; y=0 means they did not

User browsing history has the potential to be a good indicator for
user clicks, but to make use of it with a machine learning model,
you'll need to find ways to compute summary stats per user based on
multiple page visits (or sometimes no page visits).

## `UserPredictor` class

The goal of this project is to build a classifier that, given user and
log data, can predict whether those users will be interested in our
product.  There are a number of ways that you can go about this and a
number of ways that you can use the data (or not use portions of the
data); the freedom is yours.

You'll write your classifier as a class called `UserPredictor` in your
`main.py` file.  We require two methods (feel free to add more as
useful): `fit` and `predict`.