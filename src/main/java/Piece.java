import javafx.util.*;
import java.util.*;

public abstract class Piece {
    PieceTypes type;
    ChessColors color;
    Field field;
    int X;
    int Y;
    boolean alive = true;
    boolean moved = false;

    //methods

    public boolean isAlive(){
        return this.alive;
    }

    public void taken(){
        this.alive = false;
    }

    public abstract  void checked();

    public abstract boolean isChecked();

    public abstract void promote(PieceTypes type);

    public PieceTypes getType(){
        return this.type;
    }

    public ChessColors getColor(){
        return this.color;
    }

    public void move(int X, int Y, Field field){
        this.moved = true;
        this.X = X;
        this.Y = Y;
        this.field = field;
    }

    public boolean isMoved(){
        return this.moved;
    }
}
