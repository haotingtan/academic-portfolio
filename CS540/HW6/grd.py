import torch
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
import random
import numpy as np

import os, shutil, re
import io, re, signal
from contextlib import redirect_stdout

from six.moves import urllib
opener = urllib.request.build_opener()
opener.addheaders = [('User-agent', 'Mozilla/5.0')]
urllib.request.install_opener(opener)

import argparse
parser = argparse.ArgumentParser(description='hw6')
parser.add_argument('--test', type=int, default=0)
parser.add_argument('--single', type = bool, default= False)
parser.add_argument('--debug', type = bool, default= False)
# parser.add_argument('--debug', action='store_true')
args = parser.parse_args()
 
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'
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

##################### TESTS #########################

def test1(train_loader):
    if (60000, 28, 28) == train_loader.dataset.data.shape and 60000 == train_loader.dataset.targets.shape[0]:
        message = 'train dataset passed +5 pts; '
        score = 5
    else:
        message = 'train dataset output mismatch (0/5 pts); '
        score = 0

    if train_loader.dataset.targets[20000].item() == 7:
        message += 'train dataset passed +5 pts; '
        score += 5
    else:
        message += 'train dataset label mismatch (0/5 pts)'



    out = "{}\n{}".format(train_loader.dataset.data.shape,  
                                    train_loader.dataset.targets[20000].item())
    return out, message, score




def test3(output):
    score = 0
    message = ''
    s = output.strip().split('\n')
    if len(s) == 3:
        score += 5
        message += 'passed +5 pts; '
    else:
        message = 'incorrect format; '


    if 'Loss' in s[0].strip():
        score += 5
        message += 'passed +5 pts; '
    else:
        message += 'no loss; '


    acc = float(s[-1].split('(')[-1].split('%')[0])
    if acc >= 80 and acc < 100:
        score += 5
        message += 'correct accuracy +5 pts'
    else:
        message += 'accuracy wrong'

    return output, message, score



def test4(model, test_loader, criterion):
    score = 0
    message = ''

    o = io.StringIO()
    with redirect_stdout(o):
        evaluate_model(model, test_loader, criterion, show_loss=False)
    output = o.getvalue()

    s = output.strip().split('\n')

    if len(s) == 1:
        accs = s[0].split(':')
        acc_decimal = str(accs[1])[::-1].find('.')
        if '%' in accs[1] and acc_decimal == 3:
            score += 5
            message += 'passed +5 pts; '
        else:
            message += 'incorrect format (0/5 pts); '

    else:
        message += 'incorrect format (0/5 pts)'

    return output, message, score

############## Grade ##############################

signal.signal(signal.SIGALRM, handler)

class_names = ['T-shirt/top','Trouser','Pullover','Dress','Coat','Sandal','Shirt','Sneaker','Bag','Ankle Boot']
fnames = ['get_data_loader', 'build_model', 'train_model', 'evaluate_model', 'predict_label']
setup_txt_path = './setup_output.txt'

total_score = 0
for i in [1,3,4]:
    if args.single and i > int(args.test):
        break
    out = ''
    score = 0
    message = ''

    try:
        signal.alarm(10000)
        if i == 1:
            from intro_pytorch import get_data_loader
            train_loader = get_data_loader()
            test_loader = get_data_loader(False)
            out, message, score = test1(train_loader)
        elif i == 3:
            from intro_pytorch import build_model
            model = build_model()
            from intro_pytorch import train_model
            o = io.StringIO()
            with redirect_stdout(o):
                train_model(model, train_loader, criterion = torch.nn.CrossEntropyLoss(), T = 3)
            output = o.getvalue()
            # if int(args.test) == i:
            out, message, score = test3(output)
        elif i == 4:
            from intro_pytorch import evaluate_model
            # if int(args.test) == i:
            out, message, score = test4(model, test_loader, criterion = torch.nn.CrossEntropyLoss())

        signal.alarm(0)
        total_score += score
    except TimeOutException as exc:
        message = "Time Out"
    except ImportError:
        message = "Function is NOT found"
    except Exception as e:
        message = "Exception: " + str(e)

    mess = "Test {}. {}: {}".format(i, fnames[i - 1], message)
    if args.single and int(args.test) == i:
        print(mess)
        print('Score: ', score)
    else:
        if not (args.single):
            print(mess)
    print()


if not(args.single or args.debug):
    print('\n===> Total score: {}/30'.format(total_score))
