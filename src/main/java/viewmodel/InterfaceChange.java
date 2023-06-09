package viewmodel;

import model.ChessColors;
import model.PieceTypes;

public class InterfaceChange {
    public int X;
    public int Y;
    public PieceTypes change;
    public ChessColors color;
    public boolean flag = false;

    public InterfaceChange(int X, int Y, PieceTypes type, ChessColors color){
        this.X = X;
        this.Y = Y;
        this.change = type;
        this.color = color;
    }

    public InterfaceChange setCheck(){
        flag = true;
        return this;
    }
}
