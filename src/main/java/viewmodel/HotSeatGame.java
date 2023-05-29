package viewmodel;

import model.Board;
import model.ChessColors;
import model.PieceTypes;
import model.Triplet;
import viewmodel.Game;
import viewmodel.GameStatus;

import java.util.ArrayList;

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
            if (board.checkingBlack != null) {
                currentGameStatus = GameStatus.BLACKCHECK;
            }
            currentPlayer = ChessColors.BLACK;
        } else {
            if (board.checkingWhite != null) {
                currentGameStatus = GameStatus.WHITECHECK;
            }
        }
        return list;
    }
}
