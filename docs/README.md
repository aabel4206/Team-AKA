# Team-AKA Chess Project - Phase 3

Class: OOP, CS 3354.255

## Phase 3 Summary
The project is now a Java Swing chess game with backend move validation integrated into the GUI. User clicks are sent to `GameController`, legal moves are validated by `Board` and the piece classes, and the GUI refreshes from the backend state after each successful move.

## Implemented Features
- Standard 8x8 chess board and starting position
- White/Black turn tracking
- Legal moves for pawns, rooks, knights, bishops, queens, and kings
- Illegal move rejection
- Opponent captures
- Own-piece capture prevention
- Sliding-piece path blocking
- Prevention of moves that leave the moving side in check
- Check detection
- Checkmate detection and winner declaration
- New Game, Settings, Save Game, and Load Game menu support
- Save/load of backend board state, current turn, and board appearance settings

## Compile
From the repository root:

```bash
javac -d out src/chess/Main.java src/chess/gui/*.java src/chess/game/*.java src/chess/board/*.java src/chess/pieces/*.java src/chess/utils/*.java
```

## Run
From the repository root:

```bash
java -cp out chess.Main
```

## Basic Controls
- Click a piece belonging to the current player.
- Click the destination square to attempt the move.
- If the move is legal, the backend board updates and the GUI refreshes.
- If the move is illegal, a message appears and the board stays unchanged.
- Check and checkmate are reported with popups.

## Notes
- Castling, en passant, and pawn promotion are outside this implementation.
- Kings are never captured; checkmate ends the game and declares the winner.
- Older Phase 2 save files can load, defaulting to White to move when no turn is saved.
