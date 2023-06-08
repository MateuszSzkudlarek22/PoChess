package model;

public enum ChessColors {
    WHITE , BLACK;

    public String toString(){
        if(this == WHITE) return "white";
        return "black";
    }
}

