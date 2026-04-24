# Team-AKA Chess Project - Phase 3

Class: OOP, CS 3354.255

## Team Members
- Abel Tadele
- Kidus Beshah
- Abigiya Adamu Bodja

## Phase 3 Summary
This project is a Java Swing chess application with backend chess logic integrated into the GUI. The GUI sends source and destination squares to a `GameController`; the controller asks the backend `Board` and `Piece` classes to validate the move, applies only legal moves, and refreshes the GUI from the backend board state.

## Implemented Features
- Standard chess starting position
- Turn tracking for White and Black
- Legal move enforcement for pawns, rooks, knights, bishops, queens, and kings
- Captures of opponent pieces
- Rejection of illegal moves, including wrong-turn moves and own-piece captures
- Sliding-piece path blocking for rooks, bishops, and queens
- Prevention of moves that leave the moving side's king in check
- Check detection
- Checkmate detection and winner declaration
- Backend and GUI synchronization after every accepted move
- New Game, Save Game, Load Game, and Settings menu items
- Saved games include board colors, board size, piece font size, current turn, and backend board state

## Project Structure
- `src/chess/Main.java` - Swing application entry point
- `src/chess/game` - `GameController`, `MoveResult`, and optional console game loop
- `src/chess/board` - board grid, position conversion, legal move validation, check/checkmate logic
- `src/chess/pieces` - abstract `Piece` and concrete chess piece movement rules
- `src/chess/gui` - Swing frame, board panel, square buttons, settings, and save/load helpers
- `src/chess/utils` - console input parsing utility

## Compile
From the project root:

```bash
javac -d out src/chess/Main.java src/chess/gui/*.java src/chess/game/*.java src/chess/board/*.java src/chess/pieces/*.java src/chess/utils/*.java
```

## Run
From the project root:

```bash
java -cp out chess.Main
```

## Basic Controls
- Click one of your pieces to select it.
- Click a destination square to attempt the move.
- Legal moves update the backend board and then refresh the GUI.
- Illegal moves are rejected and the board display does not change.
- The status label shows whose turn it is.
- Check and checkmate show message popups.
- Use `Game > New Game` to restart.
- Use `Game > Settings` to change board colors, board size, and piece text size.
- Use `Game > Save Game` and `Game > Load Game` to persist and restore a game.

## Phase 3 Notes
- Castling, en passant, and pawn promotion are not implemented.
- Kings are not captured; checkmate declares the winner and stops further movement.
- The GUI does not decide chess rules by moving button text directly. It always refreshes from the backend `Board`.
- Older Phase 2 save files without current-turn data can still load with White to move.

## UML Diagram
A UML class diagram is included in the Phase 3 PDF submission. It illustrates the relationships between the GUI layer, game controller, board, and piece hierarchy.

## Screenshots
Screenshots of the running GUI, legal moves, captures, and checkmate states are included in the Phase 3 PDF submission.

## Final Note
This project demonstrates a fully integrated object-oriented design combining backend game logic with a responsive GUI interface.