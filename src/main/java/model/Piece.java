package model;

import java.util.ArrayList;

public class Piece {
    ChessColors color;
    int X;
    int Y;
    Board board;
    boolean moved = false;
    PieceTypes type;

    public Piece(ChessColors color, PieceTypes type, int X, int Y, Board board) {
        this.color = color;
        this.X = X;
        this.Y = Y;
        this.board = board;
        this.type = type;
    }

    ChessColors getColor() {
        return color;
    }

    PieceTypes getType() {
        return type;
    }

    Piece setCoordinates(int X, int Y) {
        this.X = X;
        this.Y = Y;
        return this;
    }

    boolean isMoved() {
        return moved;
    }

    public ArrayList<Triplet> getPossibleMoves(ChessColors color) {
        if (color != this.color) return null;
        switch (type) {
            case PAWN -> {
                return getPossibleMovesPawn();
            }
            case ROOK -> {
                return getPossibleMovesRook();
            }
            case KNIGHT -> {
                return getPossibleMovesKnight();
            }
            case BISHOP -> {
                return getPossibleMovesBishop();
            }
            case QUEEN -> {
                return getPossibleMovesQueen();
            }
            case KING -> {
                return getPossibleMovesKing();
            }
        }
        return null;
    }

    public ArrayList<Triplet> getPossibleCheckedMoves(ChessColors color) {
        if (color != this.color) return null;
        ArrayList<Triplet> list = new ArrayList<>();
        ArrayList<Triplet> listThis = this.getPossibleMoves(color);
        ArrayList<Triplet> listChecking = null;
        if (color == ChessColors.WHITE) {
            if (board.checkingWhite.getType() != PieceTypes.KNIGHT)
                listChecking = board.checkingWhite.getPossibleCheckedMoves(color);
            for (Triplet t : listThis) {
                if (t.X == board.checkingWhite.X && t.Y == board.checkingWhite.Y) {
                    list.add(new Triplet('A', t.X, t.Y));
                }
                if (listChecking != null){
                    for(Triplet t1: listChecking){
                        if(t.X==t1.X && t.Y==t1.Y) list.add(new Triplet('M', t.X, t.Y));
                    }
                }
            }
        }
        return list;
    }

    private ArrayList<Triplet> getPossibleMovesPawn() {
        ArrayList<Triplet> list = new ArrayList<>();
        if (this.color == ChessColors.WHITE) {
            if (Y + 1 == 7 && board.getPiece(X, Y + 1) == null) {
                list.add(new Triplet('P', X, Y + 1));
            }
            if (Y + 1 < 7 && board.getPiece(X, Y + 1) == null) {
                list.add(new Triplet('M', X, Y + 1));
            }
            if (!moved && board.getPiece(X, Y + 2) == null) {
                list.add(new Triplet('M', X, Y + 2));
            }
            if (X - 1 > 0 && Y + 1 < 8 && board.getPiece(X - 1, Y + 1) == null) {
                list.add(new Triplet('A', X - 1, Y + 1));
            }
            if (X + 1 < 8 && Y + 1 < 8 && board.getPiece(X + 1, Y + 1) == null) {
                list.add(new Triplet('A', X + 1, Y + 1));
            }
        } else {
            if (Y - 1 == 0 && board.getPiece(X, 0) == null) {
                list.add(new Triplet('P', X, Y - 1));
            }
            if (Y - 1 > 0 && board.getPiece(X, Y - 1) == null) {
                list.add(new Triplet('M', X, Y - 1));
            }
            if (moved && board.getPiece(X, Y - 2) == null) {
                list.add(new Triplet('M', X, Y - 2));
            }
            if (X - 1 > 0 && Y - 1 > 0 && board.getPiece(X - 1, Y - 1) == null) {
                list.add(new Triplet('A', X - 1, Y - 1));
            }
            if (X + 1 < 8 && Y - 1 > 0 && board.getPiece(X + 1, Y - 1) == null) {
                list.add(new Triplet('A', X + 1, Y - 1));
            }
        }
        return list;
    }

    private ArrayList<Triplet> getPossibleMovesRook() {
        ArrayList<Triplet> list = new ArrayList<>();
        for (int i = X + 1; i < 8; i++) {
            if (board.getPiece(i, Y) == null) {
                list.add(new Triplet('M', i, Y));
            } else {
                if (board.getPiece(i, Y).getColor() != this.color) {
                    list.add(new Triplet('A', i, Y));
                }
                break;
            }
        }
        for (int i = X - 1; i >= 0; i--) {
            if (board.getPiece(i, Y) == null) {
                list.add(new Triplet('M', i, Y));
            } else {
                if (this.board.getPiece(i, Y).getColor() != this.color) {
                    list.add(new Triplet('A', i, Y));
                }
                break;
            }
        }
        for (int i = Y + 1; i < 8; i++) {
            if (board.getPiece(X, i) == null) {
                list.add(new Triplet('M', X, i));
            } else {
                if (board.getPiece(X, i).getColor() != this.color) {
                    list.add(new Triplet('A', X, i));
                }
                break;
            }
        }
        for (int i = Y - 1; i >= 0; i--) {
            if (board.getPiece(X, i) == null) {
                list.add(new Triplet('M', X, i));
            } else {
                if (board.getPiece(X, i).getColor() != color) {
                    list.add(new Triplet('A', X, i));
                }
                break;
            }
        }
        return list;
    }

    private ArrayList<Triplet> getPossibleMovesBishop() {
        ArrayList<Triplet> list = new ArrayList<>();
        for (int i = 1; X - i >= 0 && Y - i >= 0; i++) {
            if (board.getPiece(X - i, Y - i) == null) {
                list.add(new Triplet('M', X - i, Y - i));
            } else {
                if (this.board.getPiece(X - i, Y - i).getColor() != color) {
                    list.add(new Triplet('A', X - i, Y - i));
                }
                break;
            }
        }
        for (int i = 1; X - i >= 0 && Y + i < 8; i++) {
            if (this.board.getPiece(X - i, Y + i) == null) {
                list.add(new Triplet('M', X - i, Y + i));
            } else {
                if (this.board.getPiece(X - i, Y + i).getColor() != color) {
                    list.add(new Triplet('A', X - i, Y + i));
                }
                break;
            }
        }
        for (int i = 1; X + i < 8 && Y + i < 8; i++) {
            if (this.board.getPiece(X + i, Y + i) == null) {
                list.add(new Triplet('M', X + i, Y + i));
            } else {
                if (this.board.getPiece(X + i, Y + i).getColor() !=
                        this.color) {
                    list.add(new Triplet('A', X + i, Y + i));
                }
                break;
            }
        }
        for (int i = 1; X + i < 8 && Y - i >= 0; i++) {
            if (this.board.getPiece(X + i, Y - i) == null) {
                list.add(new Triplet('M', X + i, Y - i));
            } else {
                if (this.board.getPiece(X + i, Y - i).getColor() != color) {
                    list.add(new Triplet('A', X + i, Y - i));
                }
                break;
            }
        }
        return list;
    }

    private ArrayList<Triplet> getPossibleMovesKnight() {
        ArrayList<Triplet> list = new ArrayList<>();
        if (X - 2 >= 0 && Y - 1 >= 0) {
            if (this.board.getPiece(X - 2, Y - 1) != null &&
                    this.board.getPiece(X - 2, Y - 1).getColor() != color) {
                list.add(new Triplet('A', X - 2, Y - 1));
            }
            if (this.board.getPiece(X - 2, Y - 1) == null) {
                list.add(new Triplet('M', X - 2, Y - 1));
            }
        }
        if (X - 1 >= 0 && Y - 2 >= 0) {
            if (this.board.getPiece(X - 1, Y - 2) != null &&
                    this.board.getPiece(X - 1, Y - 2).getColor() != color) {
                list.add(new Triplet('A', X - 1, Y - 2));
            }
            if (this.board.getPiece(X - 1, Y - 2) == null) {
                list.add(new Triplet('M', X - 1, Y - 2));
            }
        }
        if (X - 1 >= 0 && Y + 2 < 8) {
            if (this.board.getPiece(X - 1, Y + 2) != null &&
                    this.board.getPiece(X - 1, Y + 2).getColor() != color) {
                list.add(new Triplet('A', X - 1, Y + 2));
            }
            if (this.board.getPiece(X - 1, Y + 2) == null) {
                list.add(new Triplet('M', X - 1, Y + 2));
            }
        }
        if (X - 2 >= 0 && Y + 1 < 8) {
            if (this.board.getPiece(X - 2, Y + 1) != null &&
                    this.board.getPiece(X - 2, Y + 1).getColor() != color) {
                list.add(new Triplet('A', X - 2, Y + 1));
            }
            if (this.board.getPiece(X - 2, Y + 1) == null) {
                list.add(new Triplet('M', X - 2, Y + 1));
            }
        }
        if (X + 2 < 8 && Y + 1 < 8) {
            if (this.board.getPiece(X + 2, Y + 1) != null &&
                    this.board.getPiece(X + 2, Y + 1).getColor() != color) {
                list.add(new Triplet('A', X + 2, Y + 1));
            }
            if (this.board.getPiece(X + 2, Y + 1) == null) {
                list.add(new Triplet('M', X + 2, Y + 2));
            }
        }
        if (X + 1 < 8 && Y + 2 < 8) {
            if (this.board.getPiece(X + 1, Y + 2) != null &&
                    this.board.getPiece(X + 1, Y + 2).getColor() != color) {
                list.add(new Triplet('A', X + 1, Y + 2));
            }
            if (this.board.getPiece(X + 1, Y + 2) != null) {
                list.add(new Triplet('M', X + 1, Y + 2));
            }
        }
        if (X + 2 < 8 && Y - 1 >= 0) {
            if (this.board.getPiece(X + 2, Y - 1) != null &&
                    this.board.getPiece(X + 2, Y - 1).getColor() != color) {
                list.add(new Triplet('A', X + 2, Y - 1));
            }
            if (this.board.getPiece(X + 2, Y - 1) == null) {
                list.add(new Triplet('M', X + 2, Y - 1));
            }
        }
        if (X + 1 < 8 && Y - 2 >= 0) {
            if (this.board.getPiece(X + 1, Y - 2) != null &&
                    this.board.getPiece(X + 1, Y - 2).getColor() != color) {
                list.add(new Triplet('A', X + 1, Y - 2));
            }

        }
        return list;
    }

    private ArrayList<Triplet> getPossibleMovesQueen() {
        ArrayList<Triplet> list = new ArrayList<>();
        for (int i = 1; X - i >= 0 && Y - i >= 0; i++) {
            if (board.getPiece(X - i, Y - i) == null) {
                list.add(new Triplet('M', X - i, Y - i));
            } else {
                if (this.board.getPiece(X - i, Y - i).getColor() != color) {
                    list.add(new Triplet('A', X - i, Y - i));
                }
                break;
            }
        }
        for (int i = 1; X - i >= 0 && Y + i < 8; i++) {
            if (this.board.getPiece(X - i, Y + i) == null) {
                list.add(new Triplet('M', X - i, Y + i));
            } else {
                if (this.board.getPiece(X - i, Y + i).getColor() != color) {
                    list.add(new Triplet('A', X - i, Y + i));
                }
                break;
            }
        }
        for (int i = 1; X + i < 8 && Y + i < 8; i++) {
            if (this.board.getPiece(X + i, Y + i) == null) {
                list.add(new Triplet('M', X + i, Y + i));
            } else {
                if (this.board.getPiece(X + i, Y + i).getColor() !=
                        this.color) {
                    list.add(new Triplet('A', X + i, Y + i));
                }
                break;
            }
        }
        for (int i = 1; X + i < 8 && Y - i >= 0; i++) {
            if (this.board.getPiece(X + i, Y - i) == null) {
                list.add(new Triplet('M', X + i, Y - i));
            } else {
                if (this.board.getPiece(X + i, Y - i).getColor() != color) {
                    list.add(new Triplet('A', X + i, Y - i));
                }
                break;
            }
        }
        for (int i = X + 1; i < 8; i++) {
            if (board.getPiece(i, Y) == null) {
                list.add(new Triplet('M', i, Y));
            } else {
                if (board.getPiece(i, Y).getColor() != this.color) {
                    list.add(new Triplet('A', i, Y));
                }
                break;
            }
        }
        for (int i = X - 1; i >= 0; i--) {
            if (board.getPiece(i, Y) == null) {
                list.add(new Triplet('M', i, Y));
            } else {
                if (this.board.getPiece(i, Y).getColor() != this.color) {
                    list.add(new Triplet('A', i, Y));
                }
                break;
            }
        }
        for (int i = Y + 1; i < 8; i++) {
            if (board.getPiece(X, i) == null) {
                list.add(new Triplet('M', X, i));
            } else {
                if (board.getPiece(X, i).getColor() != this.color) {
                    list.add(new Triplet('A', X, i));
                } else {
                    if (this.board.getPiece(X, i).getType() == PieceTypes.KING &&
                            !this.board.getPiece(X, i).isMoved() && !moved) {
                        list.add(new Triplet('R', X, i));
                    }
                }
                break;
            }
        }
        for (int i = Y - 1; i >= 0; i--) {
            if (board.getPiece(X, i) == null) {
                list.add(new Triplet('M', X, i));
            } else {
                if (board.getPiece(X, i).getColor() != color) {
                    list.add(new Triplet('A', X, i));
                } else {
                    if (board.getPiece(X, i).getType() == PieceTypes.KING &&
                            board.getPiece(X, i).isMoved() && moved) {
                        list.add(new Triplet('R', X, i));
                    }
                }
                break;
            }
        }
        return list;
    }

    private ArrayList<Triplet> getPossibleMovesKing() {
        ArrayList<Triplet> list = new ArrayList<>();
        if (X - 1 >= 0) {
            if (this.board.getPiece(X - 1, Y) != null) {
                list.add(new Triplet('A', X - 1, Y));
            } else {
                list.add(new Triplet('M', X - 1, Y));
            }
        }
        if (X - 1 >= 0 && Y - 1 >= 0) {
            if (this.board.getPiece(X - 1, Y - 1) != null) {
                list.add(new Triplet('A', X - 1, Y - 1));
            } else {
                list.add(new Triplet('M', X - 1, Y - 1));
            }
        }
        if (Y - 1 >= 0) {
            if (this.board.getPiece(X, Y - 1) != null) {
                list.add(new Triplet('A', X, Y - 1));
            } else {
                list.add(new Triplet('M', X, Y - 1));
            }
        }
        if (X + 1 < 8 && Y - 1 >= 0) {
            if (this.board.getPiece(X + 1, Y - 1) != null) {
                list.add(new Triplet('A', X + 1, Y - 1));
            } else {
                list.add(new Triplet('M', X + 1, Y - 1));
            }
        }
        if (X + 1 < 8) {
            if (this.board.getPiece(X + 1, Y) != null) {
                list.add(new Triplet('A', X + 1, Y));
            } else {
                list.add(new Triplet('M', X + 1, Y));
            }
        }
        if (X + 1 < 8 && Y + 1 < 8) {
            if (this.board.getPiece(X + 1, Y + 1) != null) {
                list.add(new Triplet('A', X + 1, Y + 1));
            } else {
                list.add(new Triplet('M', X + 1, Y + 1));
            }
        }
        if (Y + 1 < 8) {
            if (this.board.getPiece(X, Y + 1) != null) {
                list.add(new Triplet('A', X, Y + 1));
            } else {
                list.add(new Triplet('M', X, Y + 1));
            }
        }
        if (Y + 1 < 8 && X - 1 >= 0) {
            if (this.board.getPiece(X - 1, Y + 1) != null) {
                list.add(new Triplet('M', X - 1, Y = 1));
            } else {
                list.add(new Triplet('M', X - 1, Y + 1));
            }
        }
        for (int i = Y + 1; i < 8; i++) {
            if (board.fields[X][i] != null) {
                if (board.fields[X][i].getType() == PieceTypes.KING &&
                        !board.fields[X][i].isMoved() && !moved) {
                    list.add(new Triplet('R', X, i - 1));
                }
                break;
            }
        }
        for (int i = Y - 1; i >= 0; i--) {
            if (board.fields[X][i] != null) {
                if (board.fields[X][i].getType() == PieceTypes.KING &&
                        !board.fields[X][i].isMoved() && !moved) {
                    list.add(new Triplet('R', X, i + 1));
                }
                break;
            }
        }
        return list;
    }
}
