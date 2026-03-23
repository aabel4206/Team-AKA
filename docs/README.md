Class: OOP, CS 3354.255

# Team-AKA Chess Project Phase 1

## Team Members
- Abel Tadele
- Kidus Beshah
- Abigiya Adamu Bodja

## Project Overview
This project is a console-based Java chess program for Phase 1 of the group assignment.
The current implementation starts a game loop, reads moves in chess notation such as `E2 E4`, validates the input format, initializes a standard 8x8 board, and can display the board state in the console.

## Folder Structure
- `src/chess` - application entry point
- `src/chess/board` - board and position classes
- `src/chess/pieces` - base piece type and chess piece subclasses
- `src/chess/game` - game loop and player classes
- `src/chess/utils` - input parsing utilities
- `docs` - project documentation

## Current Phase 1 Status
- `Main` starts the program by creating a `Game`
- `Game` handles the console loop and turn switching
- `InputParser` validates and parses moves in `E2 E4` format
- `Board` maintains an 8x8 `Piece[][]`, initializes starting positions, and displays the board
- `Position` converts chess notation such as `E2` into board coordinates
- `Player` stores simple player identity data

## How to Compile
From the project root:

```bash
javac $(find src -name '*.java')
```

## How to Run
From the project root:

```bash
java -cp src chess.Main
```

## Phase 1 Scope
- Basic board setup and console interaction only
- No advanced chess rules yet
- Castling, check, checkmate, en passant, and promotion are not implemented in this phase
