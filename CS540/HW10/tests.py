import gym
import pickle
import random
import numpy as np
import time
import torch.nn as nn
import torch.nn.functional as functional
import torch




def default_Q_value():
    return 0


def evaluate_frozen_lake(Q_table, EPSILON, visualize=False):
    total_reward = 0
    random.seed(1)
    np.random.seed(1)
    env = gym.envs.make("FrozenLake-v1")
    env.reset(seed=1)
    env.action_space.np_random.seed(1)


    for i in range(100):
        obs = env.reset()
        done = False
        while done == False:
            if random.uniform(0,1) < EPSILON:
                action = env.action_space.sample()
            else:
                prediction = np.array([Q_table[(obs,i)] for i in range(env.action_space.n)])
                action =  np.argmax(prediction)
            obs,reward,done,info = env.step(action)
            total_reward += reward
            if visualize:
                env.render()
                time.sleep(.01)
    score = total_reward/100
    return score
    


def test_Q_learning(visualize = False):
    loaded_data = pickle.load(open('Q_TABLE.pkl', 'rb'))
    Q_table = loaded_data[0]
    EPSILON = loaded_data[1]
    score = evaluate_frozen_lake(Q_table,EPSILON,visualize = False)
    points = 0
    if score >= 0.6:
        points = 30
    elif score >= 0.4:
        points = 15 
    print("Q Learning on FrozenLake-v1:")
    print("Average episode-reward over 100 episodes is " + str(score))
    print(str(points)  +" out of 30 points received.")
    return points


if __name__ == "__main__":
    total_points = 0
    total_extra_credit_points = 0
    print('-' * 40)
    try:
        total_points += test_Q_learning()
    except Exception as e:
        print(e)