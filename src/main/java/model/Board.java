package model;

import viewmodel.InterfaceChange;

import java.util.ArrayList;
import java.util.List;

public class Board {
    Piece[][] fields;
    public Piece checkingWhite;
    public Piece checkingBlack;
    int count = 0;

    public Board() {
        this.fields = new Piece[8][8];
    }



    public void setNormalGame() {
        fields[0][0] = new Piece(ChessColors.WHITE, PieceTypes.ROOK, 0, 0, this);
        fields[1][0] = new Piece(ChessColors.WHITE, PieceTypes.KNIGHT, 1, 0, this);
        fields[2][0] = new Piece(ChessColors.WHITE, PieceTypes.BISHOP, 2, 0, this);
        fields[3][0] = new Piece(ChessColors.WHITE, PieceTypes.QUEEN, 3, 0, this);
        fields[4][0] = new Piece(ChessColors.WHITE, PieceTypes.KING, 4, 0, this);
        fields[5][0] = new Piece(ChessColors.WHITE, PieceTypes.BISHOP, 5, 0, this);
        fields[6][0] = new Piece(ChessColors.WHITE, PieceTypes.KNIGHT, 6, 0, this);
        fields[7][0] = new Piece(ChessColors.WHITE, PieceTypes.ROOK, 7, 0, this);
        for (int i = 0; i < 8; i++) {
            fields[i][1] = new Piece(ChessColors.WHITE, PieceTypes.PAWN, i, 1, this);
        }
        for (int i = 0; i < 8; i++) {
            fields[i][6] = new Piece(ChessColors.BLACK, PieceTypes.PAWN, i, 6, this);
        }
        fields[0][7] = new Piece(ChessColors.BLACK, PieceTypes.ROOK, 0, 7, this);
        fields[1][7] = new Piece(ChessColors.BLACK, PieceTypes.KNIGHT, 1, 7, this);
        fields[2][7] = new Piece(ChessColors.BLACK, PieceTypes.BISHOP, 2, 7, this);
        fields[3][7] = new Piece(ChessColors.BLACK, PieceTypes.QUEEN, 3, 7, this);
        fields[4][7] = new Piece(ChessColors.BLACK, PieceTypes.KING, 4, 7, this);
        fields[5][7] = new Piece(ChessColors.BLACK, PieceTypes.BISHOP, 5, 7, this);
        fields[6][7] = new Piece(ChessColors.BLACK, PieceTypes.KNIGHT, 6, 7, this);
        fields[7][7] = new Piece(ChessColors.BLACK, PieceTypes.ROOK, 7, 7, this);
    }

    public ArrayList<InterfaceChange> makeMove(int fromX, int fromY, int toX, int toY, ChessColors color, char kind, PieceTypes type) {
        count++;
        fields[fromX][fromY].lastMoved = count;
        if(fields[fromX][fromY].getType()==PieceTypes.PAWN && Math.abs(toY-fromY) == 2 &&
                !fields[fromX][fromY].moved1) fields[fromX][fromY].moved1 = true;
        else fields[fromX][fromY].moved1 = false;
        fields[fromX][fromY].moved = true;
        if (color == ChessColors.WHITE) checkingWhite = null;
        else checkingBlack = null;

        switch (kind) {
            case 'M', 'A' -> {
                return makeNormalMove(fromX, fromY, toX, toY, color);
            }
            case 'P' -> {
                return makePromoteMove(fromX, fromY, toX, toY, color, type);
            }
            case 'C' -> {
                return makeCastlingMove(fromX, fromY, toX, toY, color);
            }
            case 'T' -> {
                return makeTMove(fromX, fromY, toX, toY, color);
            }
        }
        return null;
    }

    private ArrayList<InterfaceChange> makeTMove(int fromX, int fromY, int toX, int toY, ChessColors color) {
        ArrayList<InterfaceChange> list = new ArrayList<>();
        fields[toX][toY] = fields[fromX][fromY].setCoordinates(toX, toY);
        fields[fromX][fromY] = null;
        list.add(new InterfaceChange(fromX, fromY, null, null));
        if(color == ChessColors.WHITE){
            fields[toX][toY-1] = null;
            list.add(new InterfaceChange(toX, toY-1, null, null));
        }
        else{
            fields[toX][toY+1] = null;
            list.add(new InterfaceChange(toX, toY+1, null, null));
        }
        list.add(new InterfaceChange(toX, toY, PieceTypes.PAWN, color));
        return list;
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
        list.add(new InterfaceChange(fromX, fromY, null, null));
        fields[toX][toY] = fields[fromX][fromY].setCoordinates(toX, toY);
        fields[fromX][fromY] = null;
        list.add(new InterfaceChange(toX, toY, fields[toX][toY].getType(), color));
        ArrayList<Triplet> moves = fields[toX][toY].getPossibleMoves(color);
        for (Triplet t : moves) {
            if (t.take == 'A' && fields[t.X][t.Y].getType() == PieceTypes.KING && color != fields[t.X][t.Y].getColor()) {
                if (color == ChessColors.BLACK) {
                    checkingWhite = fields[toX][toY];
                    list.add(new InterfaceChange(t.X, t.Y, PieceTypes.KING, ChessColors.WHITE).setCheck());
                } else {
                    checkingBlack = fields[toX][toY];
                    list.add(new InterfaceChange(t.X, t.Y, PieceTypes.KING, ChessColors.BLACK).setCheck());
                }
                break;
            }
        }
        return list;
    }

    private ArrayList<InterfaceChange> makePromoteMove(int fromX, int fromY, int toX, int toY, ChessColors color, PieceTypes type) {
        ArrayList<InterfaceChange> list = new ArrayList<>();
        list.add(new InterfaceChange(fromX, fromY, null, null));
        fields[toX][toY] = new Piece(color, type, toX, toY, this);
        fields[fromX][fromY] = null;
        list.add(new InterfaceChange(toX, toY, type, color));
        ArrayList<Triplet> moves = fields[toX][toY].getPossibleMoves(color);
        for (Triplet t : moves) {
            if (t.take == 'A' && fields[t.X][t.Y].getType() == PieceTypes.KING && color != fields[t.X][t.Y].getColor()) {
                if (color == ChessColors.BLACK) {
                    checkingWhite = fields[toX][toY];
                    list.add(new InterfaceChange(t.X, t.Y, PieceTypes.KING, ChessColors.WHITE).setCheck());
                } else {
                    checkingBlack = fields[toX][toY];
                    list.add(new InterfaceChange(t.X, t.Y, PieceTypes.KING, ChessColors.BLACK).setCheck());
                }
                break;
            }
        }
        return list;
    }

    private ArrayList<InterfaceChange> makeCastlingMove(int fromX, int fromY, int toX, int toY, ChessColors color) {
        ArrayList<InterfaceChange> list = new ArrayList<>();
        list.add(new InterfaceChange(fromX, fromY, null, null));
        if (fields[toX + 1][toY] != null && fields[toX + 1][toY].getType() == PieceTypes.ROOK) {
            list.add(new InterfaceChange(toX + 1, toY, null, null));
            list.add(new InterfaceChange(toX - 1, toY, PieceTypes.ROOK, color));
            list.add(new InterfaceChange(toX, toY, PieceTypes.KING, color));
            fields[toX][toY] = fields[fromX][fromY];
            fields[toX - 1][toY] = fields[toX + 1][toY];
            fields[fromX][fromY] = null;
            fields[toX + 1][toY] = null;
        } else {
            list.add(new InterfaceChange(toX - 1, toY, null, null));
            list.add(new InterfaceChange(toX + 1, toY, PieceTypes.ROOK, color));
            list.add(new InterfaceChange(toX, toY, PieceTypes.KING, color));
            fields[toX][toY] = fields[fromX][fromY];
            fields[fromX][fromY] = null;
            fields[toX + 1][toY] = fields[toX - 1][toY];
            fields[toX - 1][toY] = null;
        }
        ArrayList<Triplet> moves = fields[toX][toY].getPossibleMoves(color);
        for (Triplet t : moves) {
            if (t.take == 'A' && fields[t.X][t.Y].getType() == PieceTypes.KING && color != fields[t.X][t.Y].getColor()) {
                if (color == ChessColors.BLACK) {
                    checkingWhite = fields[toX][toY];
                    list.add(new InterfaceChange(t.X, t.Y, PieceTypes.KING, ChessColors.WHITE).setCheck());
                } else {
                    checkingBlack = fields[toX][toY];
                    list.add(new InterfaceChange(t.X, t.Y, PieceTypes.KING, ChessColors.BLACK).setCheck());
                }
                break;
            }
        }
        return list;
    }

    public boolean isCheckmate(ChessColors color) {
        for (Piece[] t : fields) {
            for (Piece p : t) {
                if (p != null && p.getColor() == color) {
                    ArrayList<Triplet> list = p.getPossibleCheckedMoves(color);
                    if (list != null && !list.isEmpty()) return false;
                }
            }
        }
        return true;
    }
}
