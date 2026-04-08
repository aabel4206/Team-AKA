Class: OOP, CS.3354.255

# Team-AKA Chess Project Phase 1

## Team Members
- Abel Tadele
- Kidus Beshah
- Abigiya Adamu Bodja

## Project Overview
This project is a console-based chess game for Phase 1 of the group project.  
The program will display a chessboard, accept user moves in standard chess notation, and update the board after valid moves.

## Folder Structure
- `src/chess` - main source code
- `src/chess/board` - board-related classes
- `src/chess/pieces` - piece classes
- `src/chess/game` - game flow and player classes
- `src/chess/utils` - input parsing utilities
- `docs` - generated Javadoc files

## Current Status
The project structure has been created and the initial console interface has been implemented.

Completed:
- Project package structure
- Main entry point (`Main.java`)
- Game loop (`Game.java`)
- User input handling and validation (`InputParser.java`)
- Turn switching between players
- Move parsing for inputs like `E2 E4`

## How to Run
Open the project folder and run:

```bash
javac -d out src/chess/Main.java src/chess/game/Game.java src/chess/utils/InputParser.java
java -cp out chess.Main