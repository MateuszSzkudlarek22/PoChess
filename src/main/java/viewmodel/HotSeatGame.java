package viewmodel;

import javafx.util.Pair;
import model.Board;
import model.ChessColors;
import model.PieceTypes;
import model.Triplet;
import viewmodel.Game;
import viewmodel.GameStatus;

import java.util.ArrayList;
import java.util.Collections;

public class HotSeatGame extends Game {

    public HotSeatGame() {
        this.board = new Board();
        this.board.setNormalGame();
        this.currentPlayer = ChessColors.WHITE;
        this.currentGameStatus = GameStatus.NORMAL;
    }


    public ArrayList<Triplet> getPossibleMoves(int X, int Y) {
        switch (currentGameStatus) {
            case NORMAL -> {
                return this.board.getPossibleMoves(X, Y, currentPlayer);
            }
            case BLACKCHECK, WHITECHECK -> {
                return this.board.getPossibleCheckedMoves(X, Y, currentPlayer);
            }
        }
        return null;
    }

    public ArrayList<InterfaceChange> makeMove(int fromX, int fromY, int toX, int toY, char c, PieceTypes type) {
        ArrayList<InterfaceChange> list = board.makeMove(fromX, fromY, toX, toY, currentPlayer, c, type);
        if (currentPlayer == ChessColors.WHITE) {
            currentPlayer = ChessColors.BLACK;
            if (board.checkingBlack != null) {
                if (board.isCheckmate(ChessColors.BLACK)) {
                    currentGameStatus = GameStatus.BLACKMATE;
                    return new ArrayList<>(
                            Collections.singleton(new InterfaceChange(-1, -1, null, null)));
                }
                currentGameStatus = GameStatus.BLACKCHECK;
                return list;
            }
        } else {
            currentPlayer = ChessColors.WHITE;
            if (board.checkingWhite != null) {
                if (board.isCheckmate(ChessColors.WHITE)) {
                    currentGameStatus = GameStatus.WHITEMATE;
                    currentGameStatus = GameStatus.BLACKMATE;
                    return new ArrayList<>(
                            Collections.singleton(new InterfaceChange(-1, -1, null, null)));
                }
                currentGameStatus = GameStatus.WHITECHECK;
                return list;
            }
        }
        currentGameStatus = GameStatus.NORMAL;
        return list;
    }

    public boolean isTaken(int X, int Y) {
        return board.getPiece(X, Y) != null;
    }
}
