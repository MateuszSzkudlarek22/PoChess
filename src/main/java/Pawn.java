import javafx.util.Pair;

import java.util.ArrayList;

public class Pawn extends Piece{

    public Pawn(int X, int Y, ChessColors color, Field field){
        this.X = X;
        this.Y = Y;
        type = PieceTypes.PAWN;
        this.color = color;
        this.field = field;
    }

    @Override
    public void checked() {}

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public void promote(PieceTypes type){
        this.type = type;
    }
}
