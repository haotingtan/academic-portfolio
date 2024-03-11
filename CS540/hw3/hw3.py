from scipy.linalg import eigh
import numpy as np
import matplotlib.pyplot as plt


def load_and_center_dataset(filename):
    img_data = np.load(filename)
    ctr_data = img_data - np.mean(img_data, axis=0)
    return ctr_data


def get_covariance(dataset):
    gram_matrix = np.dot(np.transpose(dataset), dataset)
    S = gram_matrix / (len(dataset) - 1)
    return S


def get_eig(S, m):
    eigen_values, eigen_vectors = eigh(S, subset_by_index=[len(S[0]) - m, len(S[0]) - 1])
    index = eigen_values.argsort()[::-1]
    eigen_values = eigen_values[index]
    eigen_vectors = eigen_vectors[:, index]
    return np.diag(eigen_values), eigen_vectors


def get_eig_prop(S, prop):
    eigen_values = eigh(S, eigvals_only=True)
    eigen_values, eigen_vectors = eigh(S, subset_by_value=[prop * sum(eigen_values), np.inf])
    index = eigen_values.argsort()[::-1]
    eigen_values = eigen_values[index]
    eigen_vectors = eigen_vectors[:, index]
    return np.diag(eigen_values), eigen_vectors


def project_image(image, U):
    projection = [0] * len(U)
    for i in range(len(U[0])):
        temp = np.transpose(np.dot(np.transpose(U)[i], np.transpose(image)).item() * U[:, i])
        for j in range(len(temp)):
            projection[j] += temp[j]
    return projection


def display_image(orig, proj):
    orig_img = np.reshape(orig, (32, 32)).transpose()
    proj_img = np.reshape(proj, (32, 32)).transpose()
    fig, (ax1, ax2) = plt.subplots(nrows=1, ncols=2)

    ax1.set_title('Original')
    ax2.set_title('Projection')

    ax1_imshow = ax1.imshow(orig_img, aspect='equal')
    im_ratio1 = orig_img.shape[0] / orig_img.shape[1]
    fig.colorbar(ax1_imshow, ax=ax1, fraction=0.047 * im_ratio1)

    ax2_imshow = ax2.imshow(proj_img, aspect='equal')
    im_ratio2 = proj_img.shape[0] / proj_img.shape[1]
    fig.colorbar(ax2_imshow, ax=ax2, fraction=0.047 * im_ratio2)

    fig.tight_layout()
    plt.show()

