package model;

import java.util.ArrayList;

public class Piece {
    ChessColors color;
    int X;
    int Y;
    int lastMoved;
    Board board;
    boolean moved = false;
    boolean moved1 = false;
    PieceTypes type;

    public Piece(ChessColors color, PieceTypes type, int X, int Y, Board board) {
        this.color = color;
        this.X = X;
        this.Y = Y;
        this.board = board;
        this.type = type;
    }

    public ChessColors getColor() {
        return color;
    }

    public PieceTypes getType() {
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
        System.out.println("Piece");
        System.out.println(color + " " + this.color);
        if (color != this.color) return null;
        switch (type) {
            case PAWN -> {
                System.out.println(X + " " + Y);
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
        if (type == PieceTypes.KING) {
            Piece piece = board.fields[X][Y];
            board.fields[X][Y] = null;
            ArrayList<Triplet> temp = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board.fields[i][j] != null && board.fields[i][j].getColor() != color) {
                        temp.addAll(board.fields[i][j].getPossibleMoves(board.fields[i][j].getColor()));
                    }
                }
            }
            if (X - 1 >= 0) {
                if (board.fields[X - 1][Y] == null) {
                    boolean flag = true;
                    for (Triplet t : temp) {
                        if (t.X == X - 1 && t.Y == Y) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) list.add(new Triplet('M', X - 1, Y));
                } else {
                    if (board.fields[X - 1][Y].getColor() != color) {
                        Piece piece1 = board.fields[X - 1][Y];
                        board.fields[X - 1][Y] = null;
                        ArrayList<Triplet> listTemp = new ArrayList<>();
                        for (Piece[] t : board.fields) {
                            for (Piece p : t) {
                                if (p != null)
                                    listTemp.addAll(p.getPossibleMoves(color == ChessColors.WHITE ? ChessColors.BLACK : ChessColors.WHITE));
                            }
                        }
                        boolean flag = true;
                        for (Triplet t : listTemp) {
                            if (t.X == X - 1 && t.Y == Y) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) list.add(new Triplet('A', X - 1, Y));
                        board.fields[X - 1][Y] = piece1;
                    }
                }
            }
            if (X - 1 >= 0 && Y - 1 >= 0) {
                if (board.fields[X - 1][Y - 1] == null) {
                    boolean flag = true;
                    for (Triplet t : temp) {
                        if (t.X == X - 1 && t.Y == Y - 1) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) list.add(new Triplet('M', X - 1, Y - 1));
                } else {
                    if (board.fields[X - 1][Y - 1].getColor() != color) {
                        Piece piece1 = board.fields[X - 1][Y - 1];
                        board.fields[X - 1][Y - 1] = null;
                        ArrayList<Triplet> listTemp = new ArrayList<>();
                        for (Piece[] t : board.fields) {
                            for (Piece p : t) {
                                if (p != null) {
                                    ArrayList<Triplet> c = p.getPossibleMoves(color == ChessColors.WHITE ? ChessColors.BLACK : ChessColors.WHITE);
                                    if (c != null) listTemp.addAll(c);
                                }
                            }
                        }
                        boolean flag = true;
                        for (Triplet t : listTemp) {
                            if (t.X == X - 1 && t.Y == Y - 1) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) list.add(new Triplet('A', X - 1, Y - 1));
                        board.fields[X - 1][Y - 1] = piece1;
                    }
                }
            }
            if (Y - 1 >= 0 && X + 1 < 8) {
                if (board.fields[X + 1][Y - 1] == null) {
                    boolean flag = true;
                    for (Triplet t : temp) {
                        if (t.X == X + 1 && t.Y == Y - 1) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) list.add(new Triplet('M', X + 1, Y - 1));
                } else {
                    if (board.fields[X + 1][Y - 1].getColor() != color) {
                        Piece piece1 = board.fields[X + 1][Y - 1];
                        board.fields[X + 1][Y - 1] = null;
                        ArrayList<Triplet> listTemp = new ArrayList<>();
                        for (Piece[] t : board.fields) {
                            for (Piece p : t) {
                                if (p != null) {
                                    ArrayList<Triplet> list1 = p.getPossibleMoves(color == ChessColors.WHITE ? ChessColors.BLACK : ChessColors.WHITE);
                                    if (list1 != null) listTemp.addAll(list1);
                                }
                            }
                        }
                        boolean flag = true;
                        for (Triplet t : listTemp) {
                            if (t.X == X + 1 && t.Y == Y - 1) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) list.add(new Triplet('A', X + 1, Y - 1));
                        board.fields[X + 1][Y - 1] = piece1;
                    }
                }
            }
            if (Y - 1 >= 0) {
                if (board.fields[X][Y - 1] == null) {
                    boolean flag = true;
                    for (Triplet t : temp) {
                        if (t.X == X && t.Y == Y - 1) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) list.add(new Triplet('M', X, Y - 1));
                } else {
                    if (board.fields[X][Y - 1].getColor() != color) {
                        Piece piece1 = board.fields[X][Y - 1];
                        board.fields[X][Y - 1] = null;
                        ArrayList<Triplet> listTemp = new ArrayList<>();
                        for (Piece[] t : board.fields) {
                            for (Piece p : t) {
                                if (p != null)
                                    listTemp.addAll(p.getPossibleMoves(color == ChessColors.WHITE ? ChessColors.BLACK : ChessColors.WHITE));
                            }
                        }
                        boolean flag = true;
                        for (Triplet t : listTemp) {
                            if (t.X == X && t.Y == Y - 1) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) list.add(new Triplet('A', X, Y - 1));
                        board.fields[X][Y - 1] = piece1;
                    }
                }
            }
            if (X + 1 < 8) {
                if (board.fields[X + 1][Y] == null) {
                    boolean flag = true;
                    for (Triplet t : temp) {
                        if (t.X == X && t.Y == Y - 1) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) list.add(new Triplet('M', X + 1, Y));
                } else {
                    if (board.fields[X + 1][Y].getColor() != color) {
                        Piece piece1 = board.fields[X + 1][Y];
                        board.fields[X + 1][Y] = null;
                        ArrayList<Triplet> listTemp = new ArrayList<>();
                        for (Piece[] t : board.fields) {
                            for (Piece p : t) {
                                if (p != null)
                                    listTemp.addAll(p.getPossibleMoves(color == ChessColors.WHITE ? ChessColors.BLACK : ChessColors.WHITE));
                            }
                        }
                        boolean flag = true;
                        for (Triplet t : listTemp) {
                            if (t.X == X + 1 && t.Y == Y) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) list.add(new Triplet('A', X + 1, Y));
                        board.fields[X + 1][Y] = piece1;
                    }
                }
            }
            if (X + 1 < 8 && Y + 1 < 8) {
                if (board.fields[X + 1][Y + 1] == null) {
                    boolean flag = true;
                    for (Triplet t : temp) {
                        if (t.X == X + 1 && t.Y == Y + 1) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) list.add(new Triplet('M', X + 1, Y + 1));
                } else {
                    if (board.fields[X + 1][Y + 1].getColor() != color) {
                        Piece piece1 = board.fields[X + 1][Y + 1];
                        board.fields[X + 1][Y + 1] = null;
                        ArrayList<Triplet> listTemp = new ArrayList<>();
                        for (Piece[] t : board.fields) {
                            for (Piece p : t) {
                                if (p != null)
                                    listTemp.addAll(p.getPossibleMoves(color == ChessColors.WHITE ? ChessColors.BLACK : ChessColors.WHITE));
                            }
                        }
                        boolean flag = true;
                        for (Triplet t : listTemp) {
                            if (t.X == X + 1 && t.Y == Y + 1) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) list.add(new Triplet('A', X + 1, Y + 1));
                        board.fields[X + 1][Y + 1] = piece1;
                    }
                }
            }
            if (Y + 1 < 8) {
                if (board.fields[X][Y + 1] == null) {
                    boolean flag = true;
                    for (Triplet t : temp) {
                        if (t.X == X && t.Y == Y - 1) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) list.add(new Triplet('M', X, Y + 1));
                } else {
                    if (board.fields[X][Y + 1].getColor() != color) {
                        Piece piece1 = board.fields[X][Y + 1];
                        board.fields[X][Y + 1] = null;
                        ArrayList<Triplet> listTemp = new ArrayList<>();
                        for (Piece[] t : board.fields) {
                            for (Piece p : t) {
                                if (p != null)
                                    listTemp.addAll(p.getPossibleMoves(color == ChessColors.WHITE ? ChessColors.BLACK : ChessColors.WHITE));
                            }
                        }
                        boolean flag = true;
                        for (Triplet t : listTemp) {
                            if (t.X == X && t.Y == Y + 1) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) list.add(new Triplet('A', X, Y + 1));
                        board.fields[X][Y + 1] = piece1;
                    }
                }
            }
            if (X - 1 >= 0 && Y + 1 < 8) {
                if (board.fields[X - 1][Y + 1] == null) {
                    boolean flag = true;
                    for (Triplet t : temp) {
                        if (t.X == X - 1 && t.Y == Y + 1) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) list.add(new Triplet('M', X - 1, Y + 1));
                } else {
                    if (board.fields[X - 1][Y + 1].getColor() != color) {
                        Piece piece1 = board.fields[X - 1][Y + 1];
                        board.fields[X - 1][Y + 1] = null;
                        ArrayList<Triplet> listTemp = new ArrayList<>();
                        for (Piece[] t : board.fields) {
                            for (Piece p : t) {
                                if (p != null)
                                    listTemp.addAll(p.getPossibleMoves(color == ChessColors.WHITE ? ChessColors.BLACK : ChessColors.WHITE));
                            }
                        }
                        boolean flag = true;
                        for (Triplet t : listTemp) {
                            if (t.X == X - 1 && t.Y == Y + 1) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) list.add(new Triplet('A', X - 1, Y + 1));
                        board.fields[X - 1][Y + 1] = piece1;
                    }
                }
            }
            board.fields[X][Y] = piece;
            return list;
        }
        if (color == ChessColors.WHITE) {
            if (board.checkingWhite.getType() != PieceTypes.KNIGHT)
                listChecking = board.checkingWhite.getPossibleCheckedAttackingMoves();
            for (Triplet t : listThis) {
                if (t.X == board.checkingWhite.X && t.Y == board.checkingWhite.Y) {
                    list.add(new Triplet('A', t.X, t.Y));
                }
                if (listChecking != null) {
                    for (Triplet t1 : listChecking) {
                        if (t.X == t1.X && t.Y == t1.Y) list.add(new Triplet('M', t.X, t.Y));
                    }
                }
            }
        } else {
            if (board.checkingBlack.getType() != PieceTypes.KNIGHT)
                listChecking = board.checkingBlack.getPossibleCheckedAttackingMoves();
            for (Triplet t : listThis) {
                if (t.X == board.checkingBlack.X && t.Y == board.checkingBlack.Y) {
                    list.add(new Triplet('A', t.X, t.Y));
                }
                if (listChecking != null) {
                    for (Triplet t1 : listChecking) {
                        if (t.X == t1.X && t.Y == t1.Y) list.add(new Triplet('M', t.X, t.Y));
                    }
                }
            }
        }
        return list;
    }

    private ArrayList<Triplet> getPossibleMovesPawn() {
        System.out.println("Pawn");
        ArrayList<Triplet> list = new ArrayList<>();
        if (this.color == ChessColors.WHITE) {
            if (X - 1 >= 0 && board.fields[X - 1][Y] != null &&
                    board.fields[X - 1][Y].getType() == PieceTypes.PAWN && board.fields[X - 1][Y].moved &&
                    board.fields[X - 1][Y].moved1 && board.fields[X - 1][Y].getColor() != color &&
                    board.fields[X - 1][Y].lastMoved == board.count) {
                list.add(new Triplet('T', X - 1, Y + 1));
            }
            if (X + 1 < 8 && board.fields[X + 1][Y] != null &&
                    board.fields[X + 1][Y].getType() == PieceTypes.PAWN && board.fields[X + 1][Y].moved &&
                    board.fields[X + 1][Y].moved1 && board.fields[X + 1][Y].getColor() != color &&
                    board.fields[X + 1][Y].lastMoved == board.count) {
                list.add(new Triplet('T', X + 1, Y + 1));
            }
            if (Y + 1 == 7 && board.getPiece(X, Y + 1) == null) {
                list.add(new Triplet('P', X, Y + 1));
            }
            if (Y + 1 < 7 && board.getPiece(X, Y + 1) == null) {
                list.add(new Triplet('M', X, Y + 1));
            }
            if (Y + 2 < 7 && !moved && board.getPiece(X, Y + 2) == null) {
                list.add(new Triplet('M', X, Y + 2));
            }
            if (X - 1 >= 0 && Y + 1 < 8 && board.getPiece(X - 1, Y + 1) != null &&
                    board.getPiece(X - 1, Y + 1).getColor() != color) {
                list.add(new Triplet('A', X - 1, Y + 1));
            }
            if (X - 1 >= 0 && Y + 1 == 7 && board.getPiece(X - 1, Y + 1) != null &&
                    board.getPiece(X - 1, Y + 1).getColor() != color) {
                list.add(new Triplet('E', X - 1, Y + 1));
            }
            if (X + 1 < 8 && Y + 1 < 8 && board.getPiece(X + 1, Y + 1) != null &&
                    board.getPiece(X + 1, Y + 1).getColor() != color) {
                list.add(new Triplet('A', X + 1, Y + 1));
            }
            if (X + 1 < 8 && Y + 1 == 7 && board.getPiece(X + 1, Y + 1) != null &&
                    board.getPiece(X + 1, Y + 1).getColor() != color) {
                list.add(new Triplet('E', X + 1, Y + 1));
            }
        } else {
            System.out.println("Black");

            if (X - 1 >= 0 && board.fields[X - 1][Y] != null &&
                    board.fields[X - 1][Y].getType() == PieceTypes.PAWN && board.fields[X - 1][Y].moved &&
                    board.fields[X - 1][Y].moved1 && board.fields[X - 1][Y].getColor() != color &&
                    board.count == board.fields[X - 1][Y].lastMoved) {
                list.add(new Triplet('T', X - 1, Y - 1));
            }
            if (X + 1 < 8 && board.fields[X + 1][Y] != null &&
                    board.fields[X + 1][Y].getType() == PieceTypes.PAWN && board.fields[X + 1][Y].moved &&
                    board.fields[X + 1][Y].moved1 && board.fields[X + 1][Y].getColor() != color &&
                    board.count == board.fields[X + 1][Y].lastMoved) {
                list.add(new Triplet('T', X + 1, Y - 1));
            }
            if (Y - 1 == 0 && board.getPiece(X, 0) == null) {
                list.add(new Triplet('P', X, Y - 1));
            }
            if (Y - 1 > 0 && board.getPiece(X, Y - 1) == null) {
                list.add(new Triplet('M', X, Y - 1));
            }
            if (!moved && Y - 2 > 0 && board.getPiece(X, Y - 2) == null) {
                list.add(new Triplet('M', X, Y - 2));
            }
            if (X - 1 > 0 && Y - 1 > 0 && board.getPiece(X - 1, Y - 1) != null &&
                    board.getPiece(X - 1, Y - 1).getColor() != color) {
                list.add(new Triplet('A', X - 1, Y - 1));
            }
            if (X - 1 >= 0 && Y - 1 == 0 && board.getPiece(X - 1, Y + 1) != null &&
                    board.getPiece(X - 1, Y - 1).getColor() != color) {
                list.add(new Triplet('E', X - 1, Y + 1));
            }
            if (X + 1 < 8 && Y - 1 > 0 && board.getPiece(X + 1, Y - 1) != null &&
                    board.getPiece(X + 1, Y - 1).getColor() != color) {
                list.add(new Triplet('A', X + 1, Y - 1));
            }
            if (X + 1 < 8 && Y - 1 == 0 && board.getPiece(X + 1, Y + 1) != null &&
                    board.getPiece(X + 1, Y - 1).getColor() != color) {
                list.add(new Triplet('E', X + 1, Y + 1));
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
                list.add(new Triplet('M', X + 2, Y + 1));
            }
        }
        if (X + 1 < 8 && Y + 2 < 8) {
            if (this.board.getPiece(X + 1, Y + 2) != null &&
                    this.board.getPiece(X + 1, Y + 2).getColor() != color) {
                list.add(new Triplet('A', X + 1, Y + 2));
            }
            if (this.board.getPiece(X + 1, Y + 2) == null) {
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
            if (this.board.getPiece(X + 1, Y - 2) == null) {
                list.add(new Triplet('M', X + 1, Y - 2));
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

    private ArrayList<Triplet> getPossibleMovesKing() {
        ArrayList<Triplet> list = new ArrayList<>();
        if (X - 1 >= 0) {
            if (this.board.getPiece(X - 1, Y) != null) {
                if (this.board.getPiece(X - 1, Y).getColor() != color) {
                    list.add(new Triplet('A', X - 1, Y));
                }
            } else {
                list.add(new Triplet('M', X - 1, Y));
            }
        }
        if (X - 1 >= 0 && Y - 1 >= 0) {
            if (this.board.getPiece(X - 1, Y - 1) != null) {
                if (this.board.getPiece(X - 1, Y - 1).getColor() != color) {
                    list.add(new Triplet('A', X - 1, Y - 1));
                }
            } else {
                list.add(new Triplet('M', X - 1, Y - 1));
            }
        }
        if (Y - 1 >= 0) {
            if (this.board.getPiece(X, Y - 1) != null) {
                if (this.board.getPiece(X, Y - 1).getColor() != color) {
                    list.add(new Triplet('A', X, Y - 1));
                }
            } else {
                list.add(new Triplet('M', X, Y - 1));
            }
        }
        if (X + 1 < 8 && Y - 1 >= 0) {
            if (this.board.getPiece(X + 1, Y - 1) != null) {
                if (this.board.getPiece(X + 1, Y - 1).getColor() != color) {
                    list.add(new Triplet('A', X + 1, Y - 1));
                }
            } else {
                list.add(new Triplet('M', X + 1, Y - 1));
            }
        }
        if (X + 1 < 8) {
            if (this.board.getPiece(X + 1, Y) != null) {
                if (this.board.getPiece(X + 1, Y).getColor() != color) {
                    list.add(new Triplet('A', X + 1, Y));
                }
            } else {
                list.add(new Triplet('M', X + 1, Y));
            }
        }
        if (X + 1 < 8 && Y + 1 < 8) {
            if (this.board.getPiece(X + 1, Y + 1) != null) {
                if (this.board.getPiece(X + 1, Y + 1).getColor() != color) {
                    list.add(new Triplet('A', X + 1, Y + 1));
                }
            } else {
                list.add(new Triplet('M', X + 1, Y + 1));
            }
        }
        if (Y + 1 < 8) {
            if (this.board.getPiece(X, Y + 1) != null) {
                if (this.board.getPiece(X, Y + 1).getColor() != color) {
                    list.add(new Triplet('A', X, Y + 1));
                }
            } else {
                list.add(new Triplet('M', X, Y + 1));
            }
        }
        if (Y + 1 < 8 && X - 1 >= 0) {
            if (this.board.getPiece(X - 1, Y + 1) != null) {
                if (this.board.getPiece(X - 1, Y + 1).getColor() != color) {
                    list.add(new Triplet('M', X - 1, Y = 1));
                }
            } else {
                list.add(new Triplet('M', X - 1, Y + 1));
            }
        }
        for (int i = X + 1; i < 8; i++) {
            if (board.fields[i][Y] != null) {
                if (board.fields[i][Y].getType() == PieceTypes.ROOK &&
                        !board.fields[i][Y].isMoved() && !moved) {
                    list.add(new Triplet('C', i - 1, Y));
                }
                break;
            }
        }
        for (int i = X - 1; i >= 0; i--) {
            if (board.fields[i][Y] != null) {
                if (board.fields[i][Y].getType() == PieceTypes.ROOK &&
                        !board.fields[i][Y].isMoved() && !moved) {
                    list.add(new Triplet('C', i + 1, Y));
                }
                break;
            }
        }
        return list;
    }

    private ArrayList<Triplet> getPossibleCheckedAttackingMoves() {
        switch (type) {
            case PAWN, KNIGHT, KING -> {
                return null;
            }
            case ROOK -> {
                return getPossibleCheckedAttackingMovesRook();
            }
            case BISHOP -> {
                return getPossibleCheckedAttackingMovesBishop();
            }
            case QUEEN -> {
                return getPossibleCheckedAttackingMovesQueen();
            }
        }
        return null;
    }

    private ArrayList<Triplet> getPossibleCheckedAttackingMovesRook() {
        ArrayList<Triplet> list = new ArrayList<>();
        for (int i = X + 1; i < 8; i++) {
            if (board.fields[X][i] != null) {
                if (board.fields[i][Y].getType() == PieceTypes.KING &&
                        board.fields[i][Y].getColor() != color) return list;
                break;
            }
            list.add(new Triplet('M', i, Y));
        }
        list = new ArrayList<>();
        for (int i = X - 1; i >= 0; i--) {
            if (board.fields[X][i] != null) {
                if (board.fields[i][Y].getType() == PieceTypes.KING &&
                        board.fields[i][Y].getColor() != color) return list;
                break;
            } else {
                list.add(new Triplet('M', i, Y));
            }
        }
        list = new ArrayList<>();
        for (int i = Y + 1; i < 8; i++) {
            if (board.fields[X][i] != null) {
                if (board.fields[X][i].getType() == PieceTypes.KING &&
                        board.fields[X][i].getColor() != color) return list;
                break;
            } else {
                list.add(new Triplet('M', X, i));
            }
        }
        list = new ArrayList<>();
        for (int i = Y - 1; i >= 0; i--) {
            if (board.fields[X][i] != null) {
                if (board.fields[X][i].getType() == PieceTypes.KING &&
                        board.fields[X][i].getColor() != color) return list;
                break;
            } else {
                list.add(new Triplet('M', X, i));
            }
        }
        return null;
    }

    private ArrayList<Triplet> getPossibleCheckedAttackingMovesBishop() {
        ArrayList<Triplet> list = new ArrayList<>();
        for (int i = 1; X + i < 8 && Y + i < 8; i++) {
            if (board.fields[X + i][Y + i] != null) {
                if (board.fields[X + i][Y + i].getType() == PieceTypes.KING &&
                        board.fields[X + i][Y + i].getColor() != color) return list;
                break;
            } else {
                list.add(new Triplet('M', X + i, Y + i));
            }
        }
        list = new ArrayList<>();
        for (int i = 1; X + i < 8 && Y - i >= 0; i++) {
            if (board.fields[X + i][Y - i] != null) {
                if (board.fields[X + i][Y - i].getType() == PieceTypes.KING &&
                        board.fields[X + i][Y - i].getColor() != color) return list;
                break;
            } else {
                list.add(new Triplet('M', X + i, Y - i));
            }
        }
        list = new ArrayList<>();
        for (int i = 1; X - i >= 0 && Y - i >= 0; i++) {
            if (board.fields[X - i][Y - i] != null) {
                if (board.fields[X - i][Y - i].getType() == PieceTypes.KING &&
                        board.fields[X - i][Y - i].getColor() != color) return list;
                break;
            } else {
                list.add(new Triplet('M', X - i, Y - i));
            }
        }
        list = new ArrayList<>();
        for (int i = 1; X - i >= 0 && Y + i < 8; i++) {
            if (board.fields[X - i][Y + i] != null) {
                if (board.fields[X - i][Y + i].getType() == PieceTypes.KING &&
                        board.fields[X - i][Y + i].getColor() != color) return list;
                break;
            } else {
                list.add(new Triplet('M', X - i, Y + i));
            }
        }
        return null;
    }

    private ArrayList<Triplet> getPossibleCheckedAttackingMovesQueen() {
        ArrayList<Triplet> list = new ArrayList<>();

        for (int i = 1; X + i < 8 && Y + i < 8; i++) {
            if (board.fields[X + i][Y + i] != null) {
                if (board.fields[X + i][Y + i].getType() == PieceTypes.KING &&
                        board.fields[X + i][Y + i].getColor() != color) return list;
                break;
            } else {
                list.add(new Triplet('M', X + i, Y + i));
            }
        }
        list = new ArrayList<>();
        for (int i = 1; X + i < 8 && Y - i >= 0; i++) {
            if (board.fields[X + i][Y - i] != null) {
                if (board.fields[X + i][Y - i].getType() == PieceTypes.KING &&
                        board.fields[X + i][Y - i].getColor() != color) return list;
                break;
            } else {
                list.add(new Triplet('M', X + i, Y - i));
            }
        }
        list = new ArrayList<>();
        for (int i = 1; X - i >= 0 && Y - i >= 0; i++) {
            if (board.fields[X - i][Y - i] != null) {
                if (board.fields[X - i][Y - i].getType() == PieceTypes.KING &&
                        board.fields[X - i][Y - i].getColor() != color) return list;
                break;
            } else {
                list.add(new Triplet('M', X - i, Y - i));
            }
        }
        list = new ArrayList<>();
        for (int i = 1; X - i >= 0 && Y + i < 8; i++) {
            if (board.fields[X - i][Y + i] != null) {
                if (board.fields[X - i][Y + i].getType() == PieceTypes.KING &&
                        board.fields[X - i][Y + i].getColor() != color) return list;
                break;
            } else {
                list.add(new Triplet('M', X - i, Y + i));
            }
        }
        list = new ArrayList<>();
        for (int i = X + 1; i < 8; i++) {
            if (board.fields[X][i] != null) {
                if (board.fields[i][Y].getType() == PieceTypes.KING &&
                        board.fields[i][Y].getColor() != color) return list;
                break;
            }
            list.add(new Triplet('M', i, Y));
        }
        list = new ArrayList<>();
        for (int i = X - 1; i >= 0; i--) {
            if (board.fields[X][i] != null) {
                if (board.fields[i][Y].getType() == PieceTypes.KING &&
                        board.fields[i][Y].getColor() != color) return list;
                break;
            } else {
                list.add(new Triplet('M', i, Y));
            }
        }
        list = new ArrayList<>();
        for (int i = Y + 1; i < 8; i++) {
            if (board.fields[X][i] != null) {
                if (board.fields[X][i].getType() == PieceTypes.KING &&
                        board.fields[X][i].getColor() != color) return list;
                break;
            } else {
                list.add(new Triplet('M', X, i));
            }
        }
        list = new ArrayList<>();
        for (int i = Y - 1; i >= 0; i--) {
            if (board.fields[X][i] != null) {
                if (board.fields[X][i].getType() == PieceTypes.KING &&
                        board.fields[X][i].getColor() != color) return list;
                break;
            } else {
                list.add(new Triplet('M', X, i));
            }
        }
        return null;
    }
}
