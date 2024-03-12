# P09 Art Gallery

## Overview

This assignment involves creating an online art gallery using a Binary Search Tree (BST) to store, explore, lookup, and purchase visual artwork objects. The BST enables efficient insertion and retrieval operations, organized by a key combining the artwork's year of creation and name. This project offers an introduction to using BSTs for managing a collection of ordered elements, providing an elegant solution for common data manipulation tasks.

### Learning Objectives

- **Binary Search Tree Operations**: Implement and understand common BST operations, including insertion, search, and traversal.
- **Recursive Problem-Solving**: Develop a deeper understanding of recursion through the implementation of BST operations and problem-solving.
- **Unit Testing**: Enhance skills in developing comprehensive unit tests to ensure the correctness and reliability of the BST implementation and its functionalities.

### Key Features

- **Artwork Management**: The ability to add new artworks to the gallery, search for specific artworks, and purchase artworks from the collection.
- **Efficient Data Organization**: Use of a BST to organize artwork objects by key information (year of creation and name) for efficient data retrieval.
- **Recursive BST Operations**: Implementation of recursive methods for BST operations, emphasizing the elegance and efficiency of recursive algorithms.
- **User Interaction**: Simulate a real-world application where users can interact with the art gallery, exploring and engaging with the artwork collection.

### Technical Implementation

- **Artwork Class**: Design an `Artwork` class with attributes such as name, year of creation, and cost. Implement the `Comparable` interface to define a natural ordering of artwork objects.
- **BSTNode Class**: Utilize a `BSTNode` class for constructing the binary search tree, with methods to access and manage node data and links to child nodes.
- **ArtGallery Class**: Develop an `ArtGallery` class representing the BST structure, implementing methods for adding, searching, and purchasing artworks, as well as traversing the gallery.
- **Recursive Operations**: Employ recursive strategies for BST operations, enhancing the implementation's simplicity and effectiveness.
- **ArtGalleryTester Class**: Create a tester class with static methods to rigorously test the functionality and robustness of the art gallery application.
