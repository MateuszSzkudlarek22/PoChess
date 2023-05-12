public class Knight extends Piece{

    public Knight(int X, int Y, ChessColors color, Field field){
        this.X = X;
        this.Y = Y;
        this.color = color;
        this.type = PieceTypes.KNIGHT;
        this.field = field;
    }

    @Override
    public void checked() {

    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public void promote(PieceTypes type) {

    }
}
