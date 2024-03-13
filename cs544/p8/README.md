# P8: BigQuery, Loans Data

## Overview

In this project, we'll (a) study the geography of loans in WI using
BigQuery and (b) make predictions about loan amounts.  You'll be
combining data from several sources: a public BigQuery dataset
describing the geography of US counties, the HDMA loans dataset used
previously this semester, and pretend loan applications made via a
Google form (you'll make some submissions yourself and then
immediately analyze the results).

Learning objectives:
* combine data from a variety of sources to compute results in BigQuery
* query live results from a Google form/sheet
* perform spatial joins
* train and evaluate models using BigQuery

## Setup

You'll create and submit a `p8.ipynb` notebook.  You'll answer 10
questions in the notebook.  If the output of a cell answers question
3, start the cell with a comment: `#q3`.  The autograder depends on
this to correlate parts of your output with specific questions.

Run JupyterLab directly on your VM (no Docker containers).  You'll need some packages:

```
pip3 install jupyterlab google-cloud-bigquery google-cloud-bigquery-storage pyarrow tqdm ipywidgets pandas matplotlib db-dtypes pandas-gbq
```

You'll also need to give your VM permission to access BigQuery and
Google Drive.  You can do so by pasting the following into the
terminal on your VM and following the directions. Please read the following cautions before running this command.

```
gcloud auth application-default login --scopes=openid,https://www.googleapis.com/auth/cloud-platform,https://www.googleapis.com/auth/drive.readonly
```


## Notebook

create a notebook named
`p8.ipynb`. This is the only file we need from you.

You can create a BigQuery client like this in your `p8.ipynb`:

```python
from google.cloud import bigquery
bq = bigquery.Client()
```


## Part 1: County Data (Public Dataset)

For this part, you'll use the
`bigquery-public-data.geo_us_boundaries.counties` table.  This
contains names, IDs, boundaries, and more for every county in the
United States.

#### Test your setup

Run the following in your notebook:

```python
q = bq.query(
"""
select count(*) as num_rows 
from bigquery-public-data.geo_us_boundaries.counties
""")
q.to_dataframe()
```

It should show something like this:

|     | num_rows |
| ---:| -------: |
| 0   |     3233 |

Now, let's answer some questions.

#### Q1: what is the `geo_id` for Dane county? (note that Madison is in Dane county). 

The output should be a string.

#### Q2: how many counties are there per state?


#### Q3: about how much should the queries for the last two questions cost?

Assumptions:
1. you don't have free credits
2. you've already exhausted BigQuery's 1 TB free tier
3. you're doing this computation in an Iowa data center


## Part 2: HDMA Data (Parquet in GCS)

<!-- Link Updated. Check! -->
Download
https://pages.cs.wisc.edu/~harter/cs544/data/hdma-wi-2021.parquet.  


Do the following two tasks outside your `p8.ipynb` notebook:
1. Create a private GCS bucket (named whatever you like, for example: `cs544_p8`). 
2. Upload the parquet file to your bucket.

Write code to create a dataset called `p8` in your GCP project.  Use
`exists_ok=True` so that you can re-run your code without errors.

Use a `load_table_from_uri` call to load the parquet data into a new
table called `hdma` inside your `p8` project.

#### Q4: what are the datasets in your GCP project?

#### Q5: how many loan applications are there in the HDMA data for each county?

Answer with a `dict` where the key is the county name and the value is
the count. 


## Part 3: Application Data (Google Sheet Linked to Form)

Apply for your loan in the Google form here:
https://forms.gle/cf1R26MoGCmMriAN9. Feel free to apply multiple
times if a single vacation home is insufficient for your needs.


The form is linked to this spreadsheet (check that your loan applications show up): https://docs.google.com/spreadsheets/d/11UeIBqQylAyNUBsIO54p6WiYJWHayQMfHDbUWq1jGco/

Now, run some code to add the sheet as an external BigQuery table.

#### Q6: how many applications are there with your chosen income?


#### Q7: how many applications are there in the Google sheet per WI county?


## Part 4: Machine Learning

Create a linear regression model (`model_type='LINEAR_REG'`) to
predict `loan_amount` based on `income`, and `loan_term` -- train it
on the HDMA data. 

#### Q8: what is your model's `r2_score` on the HDMA dataset on which it was trained?

#### Q9: what is the coefficient weight on the income column?

#### Q10: what ratio of the loan applications in the Google form are for amounts greater than the model would predict, given income?

For example, if 75% are greater, the answer would be 0.75.
