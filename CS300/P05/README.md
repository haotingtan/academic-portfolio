# P05 Treasure Hunt Adventure Game

## Overview

This assignment involves developing a treasure hunt style adventure game that incorporates interactive graphical elements. Users interact with various objects by clicking and dragging them around the game environment to uncover clues, solve puzzles, and ultimately win the game. The game is set in a visually rich backyard environment where the main objective is to find a coin and place it onto a target location to claim victory.

### Learning Objectives

- **Code Organization**: Experience organizing code using object-oriented principles, leveraging inheritance, and interfaces for clear and concise code structure.
- **Inheritance and Interfaces**: Understand and apply inheritance and interfaces to promote code reusability and polymorphism, enhancing code organization and maintenance.
- **Graphic Application Development**: Learn to use the `PApplet` class from the Processing library directly, developing a graphic application from scratch rather than relying on a provided wrapper file.

### Key Features

- **Interactive Gameplay**: The game involves interactive objects that can be clicked or dragged to reveal clues and solve puzzles.
- **Dynamic Object Behavior**: Objects in the game exhibit different behaviors, including draggable and droppable properties, to interact with other objects or trigger game events.
- **Clickable Buttons**: Implement clickable buttons for game functionalities such as restarting the game and taking screenshots.
- **Game Settings from File**: Load game settings, including object properties and clues, from an external file to easily modify or expand the game without altering the codebase.

### Technical Implementation

- **Game Environment**: Use the `PApplet` class for creating the game window, loading background images, and drawing interactive objects.
- **Object-Oriented Design**: Design classes for interactive objects, draggable and droppable items, and buttons, using inheritance and interfaces to define common behaviors and properties.
- **Game Logic**: Implement game logic for object interactions, including clicking, dragging, dropping, and triggering actions upon specific events.
- **File I/O**: Load game configurations and object clues from a file, parsing the content to dynamically create objects and set up the game environment.

### Project Requirements

- Pair programming is allowed, encouraging collaboration and peer learning.
- Specific import statements are permitted to integrate the Processing library and handle file operations.
- The game includes several classes (`TreasureHunt`, `InteractiveObject`, `DraggableObject`, `DroppableObject`, `Button`, and subclasses) and an interface (`Clickable`) to structure the gameplay mechanics.
- Implement callback methods (`setup()`, `draw()`, `mousePressed()`, `mouseReleased()`) for Processing events and interactions.
