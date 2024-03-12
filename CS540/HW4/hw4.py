import csv
import numpy as np
from scipy.cluster import hierarchy
import matplotlib.pyplot as plt


def load_data(filepath):
    reader = csv.DictReader(open(filepath, 'r', encoding='utf-8'))
    pokemon_list = []
    for dictionary in reader:
        pokemon_list.append(dict(dictionary))
    return pokemon_list


def calc_features(row):
    feature_list = ["Attack", "Sp. Atk", "Speed", "Defense", "Sp. Def", "HP"]
    pokemon_feature = np.array([0, 0, 0, 0, 0, 0], dtype=np.int64)
    for i in range(len(feature_list)):
        pokemon_feature[i] = row.get(feature_list[i])
    return pokemon_feature


def hac(features):
    pokemons = np.array(features)
    #  specific case:
    if len(features) == 0 or len(features) == 1:
        exit(0)

    comp1 = np.dot(np.sum(np.square(pokemons), axis=1).reshape((len(features), 1)), np.ones((1, len(features))))
    comp2 = np.dot(np.ones((len(features), 1)), np.sum(np.square(pokemons), axis=1).reshape((1, len(features))))
    distance_matrix = np.sqrt(comp1 + comp2 - 2 * np.dot(pokemons, pokemons.transpose()))
    valid_list = [1] * len(distance_matrix)

    hac_algo = np.zeros(((len(pokemons) - 1), 4))
    for i in range(len(hac_algo)):
        min_val = None
        index = [0, 0]
        size = 0
        for col in range(len(distance_matrix)-1):
            if valid_list[col] == 0:
                continue
            for row in range(col+1, len(distance_matrix)):
                if valid_list[row] == 0:
                    continue
                if (min_val is None) or (min_val > distance_matrix[row, col] >= 0.0):
                    min_val = distance_matrix[row, col]
                    index[0] = row
                    index[1] = col

        valid_list[index[0]] = 0
        valid_list[index[1]] = 0
        valid_list.append(1)

        if index[0] < len(pokemons):
            size += 1
        else:
            size += hac_algo[index[0]-len(pokemons), 3]

        if index[1] < len(pokemons):
            size += 1
        else:
            size += hac_algo[index[1]-len(pokemons), 3]

        new_row = []
        for m in range(len(distance_matrix)):
            new_row.append(max(distance_matrix[index[0], m], distance_matrix[index[1], m]))
        new_row_numpy_row = np.array(new_row).reshape(1, len(new_row))
        new_row.append(0.0)
        new_row_numpy_col = np.array(new_row).reshape(len(new_row), 1)
        distance_matrix = np.concatenate([distance_matrix, new_row_numpy_row], axis=0)
        distance_matrix = np.concatenate([distance_matrix, new_row_numpy_col], axis=1)

        hac_algo[i] = np.array((index[1], index[0], min_val, size))

    return hac_algo


def imshow_hac(Z, names):
    plt.subplots(figsize=(5.5, 6))
    hierarchy.dendrogram(Z, labels=names, leaf_rotation=90.)
    plt.tight_layout()
    plt.show()

