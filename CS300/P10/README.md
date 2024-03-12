# P10 Open Position

## Overview

This program is designed to help manage a large number of job applications by maintaining only the applications of the current highest-scoring N applicants. An array-based min-heap is utilized to implement a priority queue, optimizing the process of filing and sorting applications based on their scores.

### Learning Objectives

- **Test-Driven Development**: Emphasize the practice of developing software starting with unit tests to guide the coding process.
- **Array-Based Priority Queue**: Introduce the concept and implementation of a priority queue using an array-based min-heap structure.
- **Comparable Interface**: Reinforce understanding and use of the `Comparable` interface for sorting and priority determination.
- **End-of-Semester Review**: Provide an opportunity to consolidate knowledge and skills acquired over the semester.

### Key Features

- **Application Management**: Efficiently manage job applications, ensuring only the top N highest-scoring applications are retained.
- **Priority Queue Implementation**: Leverage an array-based min-heap to prioritize applications based on their scores.
- **Application Comparison**: Utilize the `Comparable` interface to define a natural ordering of applications by their score.
- **Comprehensive Testing**: Focus on creating robust test cases in the `OpenPositionTester` class to validate the functionality of the application management system.

### Technical Implementation

- **Application Class**: Design an `Application` class that includes attributes for the applicant's information and their score. Implement the `Comparable` interface to compare applications based on score.
- **Priority Queue Interface**: Define a `PriorityQueueADT` interface to outline the methods required for a priority queue, including operations for adding, removing, and peeking at elements.
- **ApplicationQueue Class**: Implement the priority queue using an array-based min-heap, providing efficient management of the application process.
- **Iterative and Robust Testing**: Develop the `OpenPositionTester` class with static methods that rigorously test each component's functionality, ensuring the system's reliability.
