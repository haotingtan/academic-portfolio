# Q-Learning with FrozenLake-v1

## Overview

This project involves the implementation of a Q-Learning algorithm to solve the FrozenLake-v1 environment provided by OpenAI Gym. The FrozenLake environment is a grid-based game where an agent must navigate from a starting point to a goal on a frozen lake without falling into any holes. The agent learns to achieve this goal through trial and error, receiving rewards based on its actions.

## Objectives

- Set up a reinforcement learning environment using OpenAI Gym.
- Implement the Q-Learning algorithm to enable an agent to learn optimal policies for navigating the FrozenLake-v1 environment.
- Experiment with different hyperparameters to optimize the learning rate (`alpha`) and discount factor (`gamma`) for the agent.
- Save and evaluate the learned Q-table to analyze the agent's performance.

## Environment Setup

The virtual environment setup includes the installation of PyTorch, Gym, Pygame, and other necessary dependencies. Instructions for setting up the virtual environment and installing dependencies are provided to ensure compatibility with the skeleton code.

## Q-Learning Implementation

### Key Components

- **Environment Interaction**: Utilizes OpenAI Gym to interact with the FrozenLake-v1 environment, allowing the agent to take actions and receive observations and rewards.
- **Q-Table**: Implements a Q-table to store the value of taking a certain action in a given state, which is updated based on the Q-Learning update rule.
- **Epsilon-Greedy Policy**: Adopts an epsilon-greedy policy for action selection, balancing exploration and exploitation.
- **Learning and Discount Factors**: Configures the learning rate (`alpha`) and discount factor (`gamma`) as hyperparameters to influence the learning process.

### Files

- `Q-Learning.py`: Contains the implementation of the Q-Learning algorithm, including the agent's interaction with the environment, update of the Q-table, and policy selection.
- `tests.py`: Provides tests to evaluate the learned policies and the effectiveness of the Q-Learning implementation.
- `Q_TABLE.pkl`: Stores the learned Q-table, which represents the agent's learned policy for navigating the environment.

