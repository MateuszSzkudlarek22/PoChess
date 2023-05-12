public class King extends Piece{
    boolean checked = false;

    public King(int X, int Y, ChessColors color, Field field){
        this.X = X;
        this.Y = Y;
        this.color = color;
        this.type = PieceTypes.KING;
        this.field = field;
    }

    @Override
    public void checked() {
        checked = true;
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void promote(PieceTypes type) {

    }
}
