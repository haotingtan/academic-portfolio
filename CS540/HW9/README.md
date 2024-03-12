# Teeko Game AI

## Overview

This project implements an artificial intelligence (AI) game player for Teeko, a strategic board game. The AI utilizes the minimax algorithm to make decisions, aiming to win by placing its pieces in specific patterns on a 5x5 board. The project involves creating an internal state representation of the game in Python and developing the AI's ability to navigate through the game phases: the drop phase and the move phase.

## Assignment Goals

- Implement a minimax algorithm to solve the Teeko game.
- Develop a heuristic evaluation function for non-terminal game states.
- Optimize the algorithm to make decisions within a five-second timeframe.
- Familiarize with Python classes and state representation in AI game development.

## Game Rules

Teeko is played on a 5x5 board with two players, each having four markers. Players alternate turns placing their markers on the board with the objective of arranging their markers in a winning pattern:

- Four markers in a row horizontally, vertically, or diagonally.
- Four markers forming a 2x2 square.

After all markers are placed, players move one of their markers to an adjacent (including diagonal) unoccupied space in an attempt to achieve a winning pattern.

## Program Specification

The core of the AI's decision-making is in the `make_move` method, which selects the best move from the current state using the minimax algorithm. Key components include:

- `succ(self, state)`: Generates legal successors of a given state.
- `game_value(self, state)`: Evaluates if a state is a terminal state and which player wins.
- `heuristic_game_value(self, state)`: Heuristically evaluates non-terminal states.
- Minimax algorithm implementation with depth cutoff.

The AI operates under a constraint to select moves within five seconds, requiring optimization of the minimax depth and heuristic evaluation.

## Implementation Details

- **Minimax Algorithm**: Implemented to explore possible game states to a certain depth and select the best move based on a heuristic evaluation function.
- **Heuristic Evaluation**: Designed to score game states based on the potential for winning, encouraging moves that lead to winning patterns and blocking the opponent's winning moves.
- **Performance Optimization**: The depth of the minimax algorithm and the heuristic function were fine-tuned to ensure the AI's decisions are made within the five-second limit.

