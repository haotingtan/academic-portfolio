# P06 Exam Scheduler

## Overview

The Exam Scheduler assignment challenges students to develop a system for matching courses with available exam rooms. This task involves creating a set of classes that encapsulate course and room data, manage the schedule, and utilize recursive methods to efficiently allocate rooms based on course needs.

### Learning Objectives

- **Object-Oriented Design**: Strengthen skills in designing classes and objects that encapsulate both data and behaviors related to courses and exam rooms.
- **Recursive Problem Solving**: Develop recursive solutions for complex problem-solving, focusing on scheduling courses into available rooms.
- **Test-Driven Development**: Gain further experience in designing and implementing tester methods to ensure program correctness and robustness.

### Key Features

- **Course and Room Objects**: Define basic data structures to hold course names, enrollment numbers, room locations, and capacities.
- **Schedule Management**: Implement a system to track course assignments to rooms and manage the overall scheduling process.
- **Recursive Scheduling**: Design recursive algorithms to explore and identify valid room assignments for a given set of courses, either finding one valid schedule or all possible schedules.

### Technical Implementation

- **Data Encapsulation**: Use encapsulation principles to design `Course` and `Room` classes with private fields and public methods for data access.
- **Recursive Algorithms**: Implement recursive methods in `ExamScheduler` for scheduling, capable of handling both finding a single valid schedule and enumerating all possible schedules.
- **Dynamic Data Management**: Utilize arrays and array lists to dynamically manage courses, rooms, and their assignments during the scheduling process.
- **Exception Handling**: Implement robust error checking and exception handling to ensure data integrity and provide meaningful feedback for invalid operations.
