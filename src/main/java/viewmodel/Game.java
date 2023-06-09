package viewmodel;

import model.Board;
import model.ChessColors;
import model.PieceTypes;
import model.Triplet;

import java.util.ArrayList;

public abstract class Game {
    ChessColors currentPlayer;
    Board board;
    public GameStatus currentGameStatus;

    Game(){
        currentPlayer = ChessColors.WHITE;
        board = new Board();
        currentGameStatus = GameStatus.NORMAL;
    }

    GameStatus checkGameStatus(){
        return currentGameStatus;
    }

    public abstract ArrayList<InterfaceChange> makeMove(int fromX, int fromY, int toX, int toY, char c, PieceTypes type);

    public abstract ArrayList<Triplet> getPossibleMoves(int X, int Y);

    public abstract boolean isTaken(int X, int Y);
}
