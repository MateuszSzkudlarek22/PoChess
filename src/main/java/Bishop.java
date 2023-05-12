public class Bishop extends Piece{

    public Bishop(int X, int Y, ChessColors color, Field field){
        this.X = X;
        this.Y = Y;
        this.color = color;
        this.field = field;
        this.type = PieceTypes.BISHOP;
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
