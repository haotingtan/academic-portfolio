import os
import shutil
import io
import signal
from contextlib import redirect_stdout

import argparse

import torch
import numpy as np

from student_code import count_model_params
from student_code import LeNet

##################### Utils #########################

def write_output(output, fpath):
    f = open(fpath, "w")
    f.write(str(output))
    f.close()


def mkdir(name, rm=True):
    if not os.path.exists(name):
        os.makedirs(name)
    elif rm:
        shutil.rmtree(name)
        os.makedirs(name)

class TimeOutException(Exception):
    pass

def handler(signum, frame):
    raise TimeOutException()

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='hw7')
    parser.add_argument('--data_folder', type=str)
    parser.add_argument('--test', type=int, default=0)
    parser.add_argument('--single', action='store_true')
    parser.add_argument('--debug', action='store_true')
    args = parser.parse_args()

    signal.signal(signal.SIGALRM, handler)

    fnames = ['part1', 'part2', 'part3']
    fnames_description = ['Part 1: LeNet',
                          'Part 2: Calculating the model parameters',
                          'Part 3: Training under different configurations']

    ntests = 3

    total_score = 0
    for i in range(1, ntests + 1):
        if args.single and i != int(args.test):
            continue
        out = ''
        score = 0
        message = ''
        if True:
            try:
                if i == 1:
                    temp1 = 0
                    model = LeNet()
                    try:
                        _, output = model(torch.rand(2, 3, 32, 32))
                        score = 30
                        message += '\nYeay! Your model has the correct input shape!\n'
                    except:
                        score = 0
                        message += '\n:( Your model cannot accept the input of the required dataset.\n'
                elif i == 2:
                    params = count_model_params()
                    print('params',params, type(params))
                    if (type(params) is np.float64) or (type(params) is np.float32) or float:
                        # score = 0
                        score += 20
                        message += '\nYeay! Your function output have the correct type\n'
                    else:
                        message += '\n:( Your output for this function is of the wrong type\n'
                        score += 0
                    message += '\n'
                elif i == 3:
                    f = open("results.txt", "r")
                    files = f.readlines()
                    try:
                        line0 = float(files[0].strip())
                        line1 = float(files[1].strip())
                        line2 = float(files[2].strip())
                        line3 = float(files[3].strip())
                        line4 = float(files[4].strip())
                        line5 = float(files[5].strip())
                        line6 = float(files[6].strip())
                        score += 50
                        message += '\nYeay! looks like your .txt file is complete\n'
                    except:
                        score += 0
                        message += '\nThe results in the .txt file are not complete.\n'
                    message += '\n'
                total_score += score
            except TimeOutException as exc:
                message = "Time Out"
            except ImportError:
                message = "Function is NOT found"
            except Exception as e:
                message = "Exception: " + str(e)
            mess = "{}. {} {}\n".format(i, fnames_description[i - 1], message)
            print(mess)

    if not (args.single or args.debug):
        print('===> score: {}'.format(total_score))
        print('Total: {}/100'.format(total_score))
