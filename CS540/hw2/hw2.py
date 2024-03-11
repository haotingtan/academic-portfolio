import sys
import math
import string


def get_parameter_vectors():
    '''
    This function parses e.txt and s.txt to get the  26-dimensional multinomial
    parameter vector (characters probabilities of English and Spanish) as
    descibed in section 1.2 of the writeup

    Returns: tuple of vectors e and s
    '''
    # Implementing vectors e,s as lists (arrays) of length 26
    # with p[0] being the probability of 'A' and so on
    e=[0]*26
    s=[0]*26

    with open('e.txt', encoding='utf-8') as f:
        for line in f:
            # strip: removes the newline character
            # split: split the string on space character
            char,prob=line.strip().split(" ")
            # ord('E') gives the ASCII (integer) value of character 'E'
            # we then subtract it from 'A' to give array index
            # This way 'A' gets index 0 and 'Z' gets index 25.
            e[ord(char)-ord('A')]=float(prob)
    f.close()

    with open('s.txt', encoding='utf-8') as f:
        for line in f:
            char,prob=line.strip().split(" ")
            s[ord(char)-ord('A')]=float(prob)
    f.close()

    return (e,s)


def shred(filename):
    # Using a dictionary here. You may change this to any data structure of
    # your choice such as lists (X=[]) etc. for the assignment
    alphabets_count = dict.fromkeys(string.ascii_uppercase, 0)
    with open(filename, encoding='utf-8') as f:
        for line in f:
            for characters in line:
                if characters.upper() in alphabets_count:
                    alphabets_count[characters.upper()] += 1
    f.close()

    return alphabets_count


def compute_a_probability(lang_prob, chars_dict):
    char_prob = chars_dict['A'] * math.log(lang_prob[0])
    return char_prob


def compute_upper_f(lang_id, lang_prob, chars_file):
    if lang_id == 'E': prior_prob = 0.6
    elif lang_id == 'S': prior_prob = 0.4
    else: prior_prob = 0

    prob_total = 0.0
    for char in chars_file:
        prob_total += float(chars_file[char]) * math.log(lang_prob[ord(char)-65])

    return (math.log(prior_prob) + prob_total)


def compute_final_probability(f_eng, f_span):
    if f_span - f_eng >= 100.0: return round(0.0, 4)
    elif f_span - f_eng <= -100.0: return round(1.0, 4)
    else: return (1.0 / (1 + math.exp(f_span - f_eng)))


def print_output():
    chars_file = shred('letter.txt')
    eng_prob, span_prob = get_parameter_vectors()

    print('Q1')
    for alphabet in chars_file:
        print(alphabet, chars_file[alphabet])

    print('Q2')
    print('{0:.4f}'.format(compute_a_probability(eng_prob, chars_file)))
    print('{0:.4f}'.format(compute_a_probability(span_prob, chars_file)))

    f_eng = compute_upper_f('E', eng_prob, chars_file)
    f_span = compute_upper_f('S', span_prob, chars_file)
    print('Q3')
    print('{0:.4f}'.format(f_eng))
    print('{0:.4f}'.format(f_span))

    eng_prob_given_chars = compute_final_probability(f_eng, f_span)
    print('Q4')
    print('{0:.4f}'.format(eng_prob_given_chars))


print_output()
