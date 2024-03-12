# Hierarchical Clustering on Pokemon Stats

## Project Description

This project involves performing hierarchical clustering on Pokemon statistics to group Pokemons based on their attributes: Attack, Sp. Atk, Speed, Defense, Sp. Def, and HP. Each Pokemon is represented as a 6-dimensional feature vector based on these attributes. The objective is to cluster the first `n` Pokemons using hierarchical agglomerative clustering (HAC) with complete linkage method, similar to `scipy.cluster.hierarchy.linkage()`.

## Objectives

- To process real-world data by transforming the given Pokemon stats into a suitable format for analysis.
- To implement hierarchical clustering from scratch and understand the intricacies of clustering algorithms.
- To visualize the clustering process using dendrograms to understand the relationships between different Pokemons.

## Environment and Libraries

- Python 3
- NumPy for numerical computations.
- Matplotlib and SciPy for visualization and verifying the results of the custom HAC implementation.

## Dataset

The dataset used in this project is a collection of Pokemon stats provided in a CSV file named `Pokemon.csv`. Each Pokemon is characterized by six attributes, which are transformed into a 6-dimensional feature vector for clustering.

## Implementation Overview

The project consists of several key functions implemented in `hw4.py`:

1. **load_data(filepath):** Reads the dataset from a CSV file and returns a list of dictionaries, each representing a Pokemon's stats.
2. **calc_features(row):** Converts a Pokemon's stats into a 6-dimensional feature vector.
3. **hac(features):** Performs hierarchical agglomerative clustering on the feature vectors and returns a clustering linkage matrix.
4. **imshow_hac(Z, names):** Visualizes the hierarchical clustering process using a dendrogram.
