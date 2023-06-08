package viewmodel;

import model.ChessColors;
import model.PieceTypes;

public class InterfaceChange {
    public int X;
    public int Y;
    public PieceTypes change;
    public ChessColors color;

    public InterfaceChange(int X, int Y, PieceTypes type, ChessColors color){
        this.X = X;
        this.Y = Y;
        this.change = type;
        this.color = color;
    }
}
