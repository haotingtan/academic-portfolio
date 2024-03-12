#!/usr/bin/env python3
from subprocess import Popen, PIPE, TimeoutExpired
import filecmp
import subprocess
import re
import os


def mkdir(name, rm=True):
    if not os.path.exists(name):
        os.makedirs(name)
    elif rm:
        shutil.rmtree(name)
        os.makedirs(name)


def strip_list_of_str_from_control_chars(strlist):
    tmplist = []
    for line in strlist:
        tmplist.append(line.replace('\r',''))
    tmplist2 = []
    for line in tmplist:
        tmplist2.append(line.replace('\r',''))


    return tmplist2


def comp(name):
    return True
    # p = Popen("javac " + name, shell=True, stdin=PIPE, stdout=PIPE, stderr=PIPE)
    # output, err = p.communicate(b"")
    # rc = p.returncode
    # return (rc == 0)


def test_example(c):
    name = c[0]
    point = float(c[1])
    case = c[2:]
    ans = open(name)
    answer = open(name).read()

    name = name.split('/')[1]
    # print("answer:\n ", answer)

    try:
        ans = [line.lower() for line in ans]
        ans = list(filter(lambda x: not re.match(r'^\s*$', x), ans))
        for i in range(len(ans)):
            while ans[i][-1] == '\n' or ans[i][-1] == ' ' or ans[i][-1] == '\r':
                ans[i] = ans[i][:-1]
    
        ans_max = list(filter(lambda x:  re.match(r'^max', x), ans))
        ans = list(filter(lambda x: not re.match(r'^max', x), ans))

        # get the student output
        case[2] = ' '.join(case[2:])

        # print('Case:', case[:3])
        p = subprocess.run(case[:3], stdin=PIPE, stdout=PIPE, stderr=PIPE, timeout=30)
        output = p.stdout.decode("utf-8")

        outerr = p.stderr.decode("utf-8")

        # output = "".join(output.split())
        # answer = "".join(answer.split())

        # output = output.rstrip()
        # answer = answer.rstrip()


        # file = open("output/"+name, "w+")
        # file.write("Output:\n")
        # file.write(output)
        # file.write("\nSolution:\n")
        # file.write(answer)
        # file.close()


        output = [line.lower() for line in output.split('\n')]

        out = list(filter(lambda x: not re.match(r'^\s*$', x), output))
        for line in out:
            while line[-1] == '\n' or line[-1] == ' ' or line[-1] == '\r':
                line = line[:-1]
        out_max = list(filter(lambda x:  re.match(r'^max', x), out))
        out = list(filter(lambda x: not re.match(r'^max', x), out))
        
        if (len(out) == 0):
            print("%s: output nothing " % name)
            print("Exception caused :- ")
            print(outerr)
            return 0, False

        ans.sort()
        out.sort()

        

        ans = strip_list_of_str_from_control_chars(ans)
        out = strip_list_of_str_from_control_chars(out)
        out_max = strip_list_of_str_from_control_chars(out_max)
        ans_max = strip_list_of_str_from_control_chars(ans_max)



        

        if out_max!=[]:

            if ans == out and out_max==ans_max:

                print("%s: test case passed: " % name, end = '')
                print("%.0f points" % point)
                return point, True
            else:
                print("%s: output mismatch " % name)
                
                return 0, False
        else:
            if ans == out:
                print("%s: test case passed: " % name, end = '')
                print("%.0f points" % point)
                return point, True
            else:
                print("%s: output mismatch " % name)

                return 0, False

    except TimeoutExpired:
        print("%s: timeout " % name)
        return 0, False
    except:
        print("%s: exception thrown " % name)
        return 0, False


if __name__ == "__main__":
    total = 0
    print("Tests HW9:")

    # output_path = 'output'
    # mkdir(output_path)

    cases = open("testcases")
    cases = [x.split() for x in cases]
    scores = 0
    for c in cases:
        score, cur = test_example(c)
        scores += score

    total += scores
    print("Scores: %.0f" % total)
