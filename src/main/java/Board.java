import java.util.ArrayList;

public class Board {
    Field[][] fields;

    public Board(){
        this.fields = new Field[8][8];

    }

    public void setNormalGame(){
        fields[0][0] = new Field(0, 0, ChessColors.WHITE, PieceTypes.ROOK, this);
        fields[0][1] = new Field(0, 1, ChessColors.WHITE, PieceTypes.KNIGHT, this);
        fields[0][2] = new Field(0, 2, ChessColors.WHITE, PieceTypes.BISHOP,  this);
        fields[0][3] = new Field(0, 3, ChessColors.WHITE, PieceTypes.QUEEN, this);
        fields[0][4] = new Field(0, 4, ChessColors.WHITE, PieceTypes.KING, this);
        fields[0][5] = new Field(0, 5, ChessColors.WHITE, PieceTypes.BISHOP, this);
        fields[0][6] = new Field(0, 6, ChessColors.WHITE, PieceTypes.KNIGHT, this);
        fields[0][7] = new Field(0, 0, ChessColors.WHITE, PieceTypes.ROOK, this);
        for(int i=0; i<8; i++){
            fields[1][i] = new Field(1, i, ChessColors.WHITE, PieceTypes.PAWN, this);
        }
        for(int i=2; i<6; i++){
            for(int j=0; j<8; j++){
                fields[i][j] = new Field(i, j, null, null, this);
            }
        }
        for(int i=0; i<8; i++){
            fields[6][i] = new Field(1, i, ChessColors.BLACK, PieceTypes.PAWN, this);
        }
        fields[7][0] = new Field(0, 0, ChessColors.BLACK, PieceTypes.ROOK, this);
        fields[7][1] = new Field(0, 1, ChessColors.BLACK, PieceTypes.KNIGHT, this);
        fields[7][2] = new Field(0, 2, ChessColors.BLACK, PieceTypes.BISHOP,  this);
        fields[7][3] = new Field(0, 3, ChessColors.BLACK, PieceTypes.QUEEN, this);
        fields[7][4] = new Field(0, 4, ChessColors.BLACK, PieceTypes.KING, this);
        fields[7][5] = new Field(0, 5, ChessColors.BLACK, PieceTypes.BISHOP, this);
        fields[7][6] = new Field(0, 6, ChessColors.BLACK, PieceTypes.KNIGHT, this);
        fields[7][7] = new Field(0, 0, ChessColors.BLACK, PieceTypes.ROOK, this);
    }

    public Field getField(int X, int Y){
        return this.fields[X][Y+1];
    }

    public void makeMove(){}

    public ArrayList<Triplet> getPossibleMoves(int X, int Y, ChessColors color){
        return fields[X][Y].possibleMoves(color);
    }
}
