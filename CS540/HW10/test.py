#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri May 12 13:25:29 2023
FIN 285 Final
@author: yuyuehuang
"""
import numpy as np
import pandas as pd

# problem 1
# 1

df = pd.read_excel('AssetPrice.xlsx', sheet_name='Price', index_col=0)
# Calculate daily returns
daily_rets = df[['AGG', 'SPY']].pct_change().dropna()

# convert to monthly return
rets = daily_rets * 20


# Use EWMA method to forecast Covariance matrix
def ewma_cov(rets, lamda):
    T, n = rets.shape
    ret_mat = rets.values
    EWMA = np.zeros((T + 1, n, n))  # corection changed from T to T+1
    S = np.cov(ret_mat.T)
    EWMA[0, :] = S
    for i in range(1, T + 1):  # corection changed from T to T+1
        S = lamda * S + (1 - lamda) * np.matmul(ret_mat[i - 1, :].reshape((-1, 1)),
                                                ret_mat[i - 1, :].reshape((1, -1)))
        EWMA[i, :] = S

    return (EWMA)


def calc_cov(rets):
    lamda = 0.94
    # volatility of the assets
    vols = rets.std()
    demean_rets = rets - rets.mean()

    # calculate covaraince
    var_ewma = ewma_cov(demean_rets, lamda)
    # take only the covariance matrix for the last date, which is the forecast for next time period
    cov_end = var_ewma[-1, :]
    return cov_end


forecast_mothly_covariance = calc_cov(rets)
print('Forecasted monthly covariance:\n', forecast_mothly_covariance)

lamda = 0.94
wts_port = [1 / 2, 1 / 2]
num_assets = len(wts_port)
demean_rets = rets - rets.mean()
port_ret = np.dot(demean_rets, wts_port)
ret_mat = demean_rets.values
T = len(ret_mat)

Variance_EWMA_Hist = np.zeros((T + 1, 1))
S = demean_rets.cov().values

Variance_EWMA_Hist[0, :] = np.matmul(np.matmul(np.transpose(wts_port), S), wts_port)

for i in range(1, T + 1):
    S = lamda * S + (1 - lamda) * np.matmul(ret_mat[i - 1, :].reshape((-1, 1)),
                                            ret_mat[i - 1, :].reshape((1, -1)))

    # Calc Portfolio level Variance for each time  using EWMA
    for j in range(0, num_assets):
        for k in range(0, num_assets):
            Variance_EWMA_Hist[i, :] = Variance_EWMA_Hist[i, :] + wts_port[j] * wts_port[k] * S[j, k]

# Calc the Portfolio Variance (at the last date) for VaR Calculation
coeff = np.zeros((T + 1, 1))
for i in range(0, T + 1):
    coeff[i] = (1 - lamda) * lamda ** (i)

S = S / np.sum(coeff)
Var_Forecast = 0;
for i in range(0, num_assets):
    for j in range(0, num_assets):
        Var_Forecast = Var_Forecast + wts_port[i] * wts_port[j] * S[i, j]

# Forecasted vol. for the next period (the last element of the array)
Sigma_Forecast = np.sqrt(Var_Forecast)
print('The portfolio’s volatility for the next day:', Sigma_Forecast)