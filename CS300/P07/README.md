# P07 Iterable Song Player

## Overview

This assignment tasks students with creating an Iterable Song Player using a doubly linked list. The application enables users to navigate through a collection of songs in both forward and backward directions, as well as add and remove songs. Implementing this assignment provides valuable practice with the Java Iterator and Iterable interfaces, along with developing a deeper understanding of doubly linked lists.

### Learning Objectives

- **Iterable Doubly Linked List**: Implement a collection of songs as an iterable doubly linked list, understanding the nuances of linking nodes in both directions.
- **Java API Interfaces**: Gain practical experience with generics and the implementation of `java.lang.Iterable` and `java.util.Iterator` interfaces.
- **Object-Oriented Design and Exception Handling**: Enhance skills in object-oriented programming, focusing on design, code organization, and managing exceptions gracefully.
- **Unit Testing**: Develop comprehensive unit tests to validate the functionality of the song player, ensuring robustness and reliability.

### Key Features

- **Song Management**: Ability to add, remove, and play songs from a collection, maintaining the order of songs as they are managed.
- **Iterative Navigation**: Two types of iterators to navigate through the song list in forward and backward directions, showcasing the power of the Iterator pattern.
- **Doubly Linked List**: Implementation of a custom doubly linked list to store songs, allowing for efficient addition and removal of songs at any point in the list.
- **Error Handling**: Robust error handling to manage common issues such as invalid operations or navigating beyond the list boundaries.

### Technical Implementation

- **Custom Song Class**: Design a `Song` class to encapsulate song details like name, artist, and duration.
- **LinkedNode Generic Class**: Implement a generic `LinkedNode` class to serve as the building block for the doubly linked list, storing song data and links to previous and next nodes.
- **Iterators for Navigation**: Create `ForwardSongIterator` and `BackwardSongIterator` classes to enable forward and backward traversal of the song list, implementing the `Iterator<Song>` interface.
- **SongPlayer Class**: Design the `SongPlayer` class as the central component managing the song list, offering methods to add, remove, and play songs, as well as switch playback direction.
