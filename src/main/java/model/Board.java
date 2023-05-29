package model;

import viewmodel.InterfaceChange;

import java.util.ArrayList;
import java.util.List;

public class Board {
    Piece[][] fields;
    public Piece checkingWhite;
    public Piece checkingBlack;

    public Board() {
        this.fields = new Piece[8][8];

    }

    public void setNormalGame() {
        fields[0][0] = new Piece(ChessColors.WHITE, PieceTypes.ROOK, 0, 0, this);
        fields[0][1] = new Piece(ChessColors.WHITE, PieceTypes.KNIGHT, 0, 1, this);
        fields[0][2] = new Piece(ChessColors.WHITE, PieceTypes.BISHOP, 0, 2, this);
        fields[0][3] = new Piece(ChessColors.WHITE, PieceTypes.QUEEN, 0, 3, this);
        fields[0][4] = new Piece(ChessColors.WHITE, PieceTypes.KING, 0, 4, this);
        fields[0][5] = new Piece(ChessColors.WHITE, PieceTypes.BISHOP, 0, 5, this);
        fields[0][6] = new Piece(ChessColors.WHITE, PieceTypes.KNIGHT, 0, 6, this);
        fields[0][7] = new Piece(ChessColors.WHITE, PieceTypes.ROOK, 0, 7, this);
        for (int i = 0; i < 8; i++) {
            fields[1][i] = new Piece(ChessColors.WHITE, PieceTypes.PAWN, 1, i, this);
        }
        for (int i = 0; i < 8; i++) {
            fields[6][i] = new Piece(ChessColors.WHITE, PieceTypes.PAWN, 6, i, this);
        }
        fields[7][0] = new Piece(ChessColors.BLACK, PieceTypes.ROOK, 7, 0, this);
        fields[7][1] = new Piece(ChessColors.BLACK, PieceTypes.KNIGHT, 7, 1, this);
        fields[7][2] = new Piece(ChessColors.BLACK, PieceTypes.BISHOP, 7, 2, this);
        fields[7][3] = new Piece(ChessColors.BLACK, PieceTypes.QUEEN, 7, 3, this);
        fields[7][4] = new Piece(ChessColors.BLACK, PieceTypes.KING, 7, 4, this);
        fields[7][5] = new Piece(ChessColors.BLACK, PieceTypes.BISHOP, 7, 5, this);
        fields[7][6] = new Piece(ChessColors.BLACK, PieceTypes.KNIGHT, 7, 6, this);
        fields[7][7] = new Piece(ChessColors.BLACK, PieceTypes.ROOK, 7, 7, this);
    }

    public ArrayList<InterfaceChange> makeMove(int fromX, int fromY, int toX, int toY, ChessColors color, char kind, PieceTypes type) {
        switch (kind) {
            case 'M', 'A' -> {
                return makeNormalMove(fromX, fromY, toX, toY, color);
            }
            case 'P' -> {
                return makePromoteMove(fromX, fromY, toX, toY, color, type);
            }
            case 'R' -> {
                return makeCastlingMove(fromX, fromY, toX, toY, color);
            }
        }
        return null;
    }

    public ArrayList<Triplet> getPossibleMoves(int X, int Y, ChessColors color) {
        return fields[X][Y].getPossibleMoves(color);
    }

    public ArrayList<Triplet> getPossibleCheckedMoves(int X, int Y, ChessColors color) {
        return fields[X][Y].getPossibleCheckedMoves(color);
    }

    public Piece getPiece(int X, int Y) {
        return fields[X][Y];
    }

    private ArrayList<InterfaceChange> makeNormalMove(int fromX, int fromY, int toX, int toY, ChessColors color) {
        ArrayList<InterfaceChange> list = new ArrayList<>();
        list.add(new InterfaceChange(fromX, fromY, "blank"));
        fields[toX][toY] = fields[fromX][fromY].setCoordinates(toX, toY);
        fields[fromX][fromY] = null;
        switch (fields[toX][toY].getType()) {
            case PAWN -> list.add(new InterfaceChange(toX, toY, "pawn"));
            case ROOK -> list.add(new InterfaceChange(toX, toY, "rook"));
            case KNIGHT -> list.add(new InterfaceChange(toX, toY, "knight"));
            case BISHOP -> list.add(new InterfaceChange(toX, toY, "bishop"));
            case QUEEN -> list.add(new InterfaceChange(toX, toY, "queen"));
            case KING -> list.add(new InterfaceChange(toX, toY, "king"));
        }
        ArrayList<Triplet> moves = fields[toX][toY].getPossibleMoves(color);
        for (Triplet t : moves) {
            if (t.take == 'A' && fields[t.X][t.Y].getType() == PieceTypes.KING && color != fields[t.X][t.Y].getColor()) {
                if (color == ChessColors.BLACK) {
                    checkingWhite = fields[toX][toY];
                    list.add(new InterfaceChange(t.X, t.Y, "check white"));
                } else {
                    checkingBlack = fields[toX][toY];
                    list.add(new InterfaceChange(t.X, t.Y, "check black"));
                }
                break;
            }
        }
        return list;
    }

    private ArrayList<InterfaceChange> makePromoteMove(int fromX, int fromY, int toX, int toY, ChessColors color, PieceTypes type) {
        ArrayList<InterfaceChange> list = new ArrayList<>();
        list.add(new InterfaceChange(fromX, fromY, "blank"));
        fields[toX][toY] = new Piece(color, type, toX, toY, this);
        switch (type) {
            case PAWN -> list.add(new InterfaceChange(toX, toY, "pawn"));
            case ROOK -> list.add(new InterfaceChange(toX, toY, "rook"));
            case KNIGHT -> list.add(new InterfaceChange(toX, toY, "knight"));
            case BISHOP -> list.add(new InterfaceChange(toX, toY, "bishop"));
            case QUEEN -> list.add(new InterfaceChange(toX, toY, "queen"));
            case KING -> list.add(new InterfaceChange(toX, toY, "king"));
        }
        ArrayList<Triplet> moves = fields[toX][toY].getPossibleMoves(color);
        for (Triplet t : moves) {
            if (t.take == 'A' && fields[t.X][t.Y].getType() == PieceTypes.KING && color != fields[t.X][t.Y].getColor()) {
                if (color == ChessColors.BLACK) {
                    checkingWhite = fields[toX][toY];
                    list.add(new InterfaceChange(t.X, t.Y, "check white"));
                } else {
                    checkingBlack = fields[toX][toY];
                    list.add(new InterfaceChange(t.X, t.Y, "check black"));
                }
                break;
            }
        }
        return list;
    }

    private ArrayList<InterfaceChange> makeCastlingMove(int fromX, int fromY, int toX, int toY, ChessColors color) {
        ArrayList<InterfaceChange> list = new ArrayList<>();
        list.add(new InterfaceChange(fromX, fromY, "blank"));
        StringBuilder n = color == ChessColors.WHITE ? new StringBuilder("white ") : new StringBuilder("black ");
        if (fields[toX][toY + 1] != null && fields[toX][toY + 1].getType() == PieceTypes.ROOK) {
            list.add(new InterfaceChange(toX, toY + 1, "blank"));
            list.add(new InterfaceChange(toX, toY - 1, n.append("rook").toString()));
            list.add(new InterfaceChange(toX, toY, n.append("king").toString()));
            fields[toX][toY] = fields[fromX][fromY];
            fields[toX][toY - 1] = fields[toX][toY + 1];
            fields[fromX][fromY] = null;
            fields[toX][toY + 1] = null;
        } else {
            list.add(new InterfaceChange(toX, toY - 1, "blank"));
            list.add(new InterfaceChange(toX, toY + 1, n.append("rook").toString()));
            list.add(new InterfaceChange(toX, toY, n.append("king").toString()));
            fields[toX][toY] = fields[fromX][fromY];
            fields[fromX][fromY] = null;
            fields[toX][toY + 1] = fields[toX][toY - 1];
            fields[toX][toY - 1] = null;
        }
        ArrayList<Triplet> moves = fields[toX][toY].getPossibleMoves(color);
        for (Triplet t : moves) {
            if (t.take == 'A' && fields[t.X][t.Y].getType() == PieceTypes.KING && color != fields[t.X][t.Y].getColor()) {
                if (color == ChessColors.BLACK) {
                    checkingWhite = fields[toX][toY];
                    list.add(new InterfaceChange(t.X, t.Y, "check white"));
                } else {
                    checkingBlack = fields[toX][toY];
                    list.add(new InterfaceChange(t.X, t.Y, "check black"));
                }
                break;
            }
        }
        return list;
    }
}
