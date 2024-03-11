import sys
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np


def load_data(filepath):
    with open(filepath, encoding='utf-8') as f:
        ice_df = pd.read_csv(f)
    return ice_df


def compute_X(df):
    X = np.append(np.ones((len(df), 1), dtype=np.int64), np.array(df.loc[:, ["year"]], dtype=np.int64), axis=1)
    return X


def compute_Y(df):
    return np.array(np.array(df.loc[:, ["days"]]), dtype=np.int64).reshape(len(df),)


def compute_Z(X):
    return np.dot(X.transpose(), X)


def compute_inverse_Z(Z):
    return np.linalg.inv(Z)


def compute_pseudo_inverse_X(I, X):
    return np.dot(I, X.transpose())


def compute_beta_hat(PI, Y):
    return np.dot(PI, Y)


def predict_y(beta_hat):
    return beta_hat[0] + beta_hat[1] * 2022.0


def predict_no_freeze(beta_hat):
    return -beta_hat[0] / beta_hat[1]


def summary():
    ice_df = load_data(sys.argv[1])
    matrix_X = compute_X(ice_df)
    matrix_Y = compute_Y(ice_df)
    matrix_Z = compute_Z(matrix_X)
    matrix_I = compute_inverse_Z(matrix_Z)
    matrix_PI = compute_pseudo_inverse_X(matrix_I, matrix_X)
    beta_hat = compute_beta_hat(matrix_PI, matrix_Y)
    print('Q3a:\n' + str(matrix_X))
    print('Q3b:\n' + str(matrix_Y))
    print('Q3c:\n' + str(matrix_Z))
    print('Q3d:\n' + str(matrix_I))
    print('Q3e:\n' + str(matrix_PI))
    print('Q3f:\n' + str(beta_hat))

    y_test = predict_y(beta_hat)
    print("Q4: " + str(y_test))

    if beta_hat[1] == 0:
        print('Q5a: =')
        print('Q5b: This sign tells that the Lake-Mendota ice days keeps unchanged as the year increases')
    elif beta_hat[1] > 0:
        print('Q5a: >')
        print('Q5b: This sign tells that the Lake-Mendota ice days increases as the year increases')
    else:
        print('Q5a: <')
        print('Q5b: This sign tells that the Lake-Mendota ice days decreases as the year increases')

    no_freeze = predict_no_freeze(beta_hat)
    print("Q6a: " + str(no_freeze))
    print("Q6b: x* is NOT a compelling result because ice days over past ~150 years does not have " +
          "a strong significant trend(some years might have more ice days but some years might have less ice days." +
          " It seems like ice days has a little or no association with years. As a result, using linear regression to estimate the ice days is NOT a compelling prediction.")

    plt.plot(ice_df['year'], ice_df['days'])
    plt.xlabel("Year")
    plt.ylabel("Number of frozen days")
    plt.savefig("plot.jpg")


if __name__ == "__main__":
    summary()
