# Convolutional Neural Networks for Scene Recognition

## Project Overview

This project involves implementing and training a convolutional neural network (CNN), specifically LeNet, to perform scene recognition on the MiniPlaces dataset. The goal is to explore the power of CNNs in classifying images into 100 different scene categories, understand the intricacies of trainable parameters within CNNs, and experiment with various training configurations to optimize performance.

## Objectives

- Set up and familiarize with the PyTorch framework for deep learning.
- Implement the LeNet-5 architecture and train it on the MiniPlaces dataset.
- Count the number of trainable parameters within the implemented CNN.
- Experiment with different batch sizes, learning rates, and training epochs to optimize the model's performance.
- Design and customize a deep network for efficient and accurate scene recognition.

## Dataset

The MiniPlaces dataset is a subset of the Places2 Database from MIT, designed for scene recognition tasks. It includes:
- 100,000 images for training
- 10,000 images for validation
- 10,000 images for testing
across 100 mutually exclusive scene categories.

## Implementation Details

### Part I: Creating LeNet-5

- Implemented the classic LeNet-5 CNN architecture using PyTorch for the MiniPlaces dataset.
- Configured layers according to specified dimensions, including convolutional layers, max pooling, and fully connected layers.

### Part II: Parameter Counting

- Developed a function to count the number of trainable parameters in the LeNet-5 model, providing insights into the model's complexity.

### Part III: Training Configurations

- Trained the LeNet-5 model under various configurations to explore the effects of batch size, learning rate, and number of training epochs on the model's accuracy and efficiency.

### Part IV: Model Customization

- Designed a custom deep network architecture for the MiniPlaces dataset, aiming to improve upon the baseline performance established by LeNet-5.

## Results

The experimentation with different training configurations led to a comprehensive understanding of how each parameter affects the training process and model performance. The results are documented in `results.txt`, detailing the validation accuracy achieved under each configuration.

