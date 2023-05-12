public class Queen extends Piece{
    public Queen(int X, int Y, ChessColors color, Field field){
        this.X = X;
        this.Y = Y;
        this.color = color;
        this.type = PieceTypes.QUEEN;
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
