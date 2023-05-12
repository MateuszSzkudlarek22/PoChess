import java.util.ArrayList;

public class Field {
    int X;
    int Y;
    Piece piece;
    Board board;

    public Field(int X, int Y,  ChessColors color, PieceTypes piece, Board board){
        this.X = X;
        this.Y = Y;
        this.board = board;
        this.piece = null;
        if(piece != null){
            if(piece == PieceTypes.PAWN) this.piece = new Pawn(X, Y, color, this);
            if(piece == PieceTypes.ROOK) this.piece = new Rook(X, Y, color, this);
            if(piece == PieceTypes.BISHOP) this.piece = new Bishop(X, Y, color, this);
            if(piece == PieceTypes.KNIGHT) this.piece = new Knight(X, Y, color, this);
            if(piece == PieceTypes.QUEEN) this.piece = new Queen(X, Y, color, this);
            if(piece == PieceTypes.KING) this.piece = new King(X, Y, color, this);
        }
    }

    public boolean isPiece(){
        return piece!=null;
    }

    public Piece getPiece(){
        return this.piece;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }

    public ArrayList<Triplet> possibleMoves(ChessColors color){
        if(this.piece!=null && this.piece.getColor() == color){
            ArrayList<Triplet> list = new ArrayList<>();
            if(this.piece.getType() == PieceTypes.PAWN){
                if(this.piece.getColor() ==ChessColors.WHITE){
                    if(Y+1==7 && !board.getField(X, Y+1).isPiece()){
                        list.add(new Triplet('P', X, Y+1));
                    }
                    if(Y+1<7 && !board.getField(X, Y+1).isPiece()){
                        list.add(new Triplet('M', X, Y+1));
                    }
                    if(!piece.isMoved() && !board.getField(X, Y+2).isPiece()){
                        list.add(new Triplet('M', X, Y+2));
                    }
                    if(X-1>0 && Y+1<8 && board.getField(X-1, Y+1).isPiece()){
                        list.add(new Triplet('A', X-1, Y+1));
                    }
                    if(X+1<8 && Y+1<8 && board.getField(X+1, Y+1).isPiece()){
                        list.add(new Triplet('A', X+1, Y+1));
                    }
                }
                else{
                    if(Y-1==0 && !board.getField(X, Y-1).isPiece()){
                        list.add(new Triplet('P', X, Y-1));
                    }
                    if(Y-1>0 && !board.getField(X, Y-1).isPiece()){
                        list.add(new Triplet('M', X, Y-1));
                    }
                    if(!piece.isMoved() && !board.getField(X, Y-2).isPiece()){
                        list.add(new Triplet('M', X, Y-2));
                    }
                    if(X-1>0 && Y-1>0 && board.getField(X-1, Y-1).isPiece()){
                        list.add(new Triplet('A', X-1, Y-1));
                    }
                    if(X+1<8 && Y-1>0 && board.getField(X+1, Y-1).isPiece()){
                        list.add(new Triplet('A', X+1, Y-1));
                    }
                }
            }
            if(this.piece.getType() == PieceTypes.ROOK){
                for(int i = X+1; i<8; i++){
                    if(!this.board.getField(i, Y).isPiece()){
                        list.add(new Triplet('M', i, Y));
                    }
                    else{
                        if(this.board.getField(i, Y).getPiece().getColor() != this.piece.getColor()){
                            list.add(new Triplet('A', i, Y));
                        }
                        break;
                    }
                }
                for(int i = X-1; i>=0; i--){
                    if(!this.board.getField(i, Y).isPiece()){
                        list.add(new Triplet('M', i, Y));
                    }
                    else{
                        if(this.board.getField(i, Y).getPiece().getColor() != this.piece.getColor()){
                            list.add(new Triplet('A', i, Y));
                        }
                        break;
                    }
                }
                for(int i = Y+1; i<8; i++){
                    if(!this.board.getField(X, i).isPiece()){
                        list.add(new Triplet('M', X, i));
                    }
                    else{
                        if(this.board.getField(X, i).getPiece().getColor() != this.piece.getColor()){
                            list.add(new Triplet('A', X, i));
                        }
                        else{
                            if(this.board.getField(X, i).getPiece().getType() == PieceTypes.KING &&
                                    this.board.getField(X, i).getPiece().isMoved() && !piece.isMoved()){
                                list.add(new Triplet('R', X, i));
                            }
                        }
                        break;
                    }
                }
                for(int i = Y-1; i>=0; i--){
                    if(!this.board.getField(X, i).isPiece()){
                        list.add(new Triplet('M', X, i));
                    }
                    else{
                        if(this.board.getField(X, i).getPiece().getColor() != this.piece.getColor()){
                            list.add(new Triplet('A', X, i));
                        }
                        else{
                            if(this.board.getField(X, i).getPiece().getType() == PieceTypes.KING &&
                                    this.board.getField(X, i).getPiece().isMoved() && !piece.isMoved()){
                                list.add(new Triplet('R', X, i));
                            }
                        }
                        break;
                    }
                }
            }
            if(this.piece.getType() == PieceTypes.BISHOP){
                for(int i=1; X-i>=0 && Y-i>=0; i++){
                    if(!this.board.getField(X-i, Y-i).isPiece()){
                        list.add(new Triplet('M', X-i, Y-i));
                    }
                    else{
                        if(this.board.getField(X-i, Y-i).getPiece().getColor() !=
                                this.piece.getColor()){
                            list.add(new Triplet('A', X-i, Y-i));
                        }
                        break;
                    }
                }
                for(int i=1; X-i>=0 && Y+i<8; i++){
                    if(!this.board.getField(X-i, Y+i).isPiece()){
                        list.add(new Triplet('M', X-i, Y+i));
                    }
                    else{
                        if(this.board.getField(X-i, Y+i).getPiece().getColor() !=
                                this.piece.getColor()){
                            list.add(new Triplet('A', X-i, Y+i));
                        }
                        break;
                    }
                }
                for(int i=1; X+i<8 && Y+i<8; i++){
                    if(!this.board.getField(X+i, Y+i).isPiece()){
                        list.add(new Triplet('M', X+i, Y+i));
                    }
                    else{
                        if(this.board.getField(X+i, Y+i).getPiece().getColor() !=
                                this.piece.getColor()){
                            list.add(new Triplet('A', X+i, Y+i));
                        }
                        break;
                    }
                }
                for(int i=1; X+i<8 && Y-i>=0; i++){
                    if(!this.board.getField(X+i, Y-i).isPiece()){
                        list.add(new Triplet('M', X+i, Y-i));
                    }
                    else{
                        if(this.board.getField(X+i, Y-i).getPiece().getColor() !=
                                this.piece.getColor()){
                            list.add(new Triplet('A', X+i, Y-i));
                        }
                        break;
                    }
                }
            }
            if(this.piece.getType() == PieceTypes.KNIGHT){
                if(X-2>=0 && Y-1>=0){
                    if(this.board.getField(X-2, Y-1).isPiece() &&
                            this.board.getField(X-2, Y-1).getPiece().getColor() != piece.getColor()){
                        list.add(new Triplet('A', X-2, Y-1));
                    }
                    if(!this.board.getField(X-2, Y-1).isPiece()){
                        list.add(new Triplet('M', X-2, Y-1));
                    }
                }
                if(X-1>=0 && Y-2>=0){
                    if(this.board.getField(X-1, Y-2).isPiece() &&
                            this.board.getField(X-1, Y-2).getPiece().getColor() != piece.getColor()){
                        list.add(new Triplet('A', X-1, Y-2));
                    }
                    if(!this.board.getField(X-1, Y-2).isPiece()){
                        list.add(new Triplet('M', X-1, Y-2));
                    }
                }
                if(X-1>=0 && Y+2<8){
                    if(this.board.getField(X-1, Y+2).isPiece() &&
                            this.board.getField(X-1, Y+2).getPiece().getColor() != piece.getColor()){
                        list.add(new Triplet('A', X-1, Y+2));
                    }
                    if(!this.board.getField(X-1, Y+2).isPiece()){
                        list.add(new Triplet('M', X-1, Y+2));
                    }
                }
                if(X-2>=0 && Y+1<8){
                    if(this.board.getField(X-2, Y+1).isPiece() &&
                            this.board.getField(X-2, Y+1).getPiece().getColor() != piece.getColor()){
                        list.add(new Triplet('A', X-2, Y+1));
                    }
                    if(!this.board.getField(X-2, Y+1).isPiece()){
                        list.add(new Triplet('M', X-2, Y+1));
                    }
                }
                if(X+2<8 && Y+1<8){
                    if(this.board.getField(X+2, Y+1).isPiece() &&
                            this.board.getField(X+2, Y+1).getPiece().getColor() != piece.getColor()){
                        list.add(new Triplet('A', X+2, Y+1));
                    }
                    if(!this.board.getField(X+2, Y+1).isPiece()){
                        list.add(new Triplet('M', X+2, Y+2));
                    }
                }
                if(X+1<8 && Y+2<8){
                    if(this.board.getField(X+1, Y+2).isPiece() &&
                            this.board.getField(X+1, Y+2).getPiece().getColor() != piece.getColor()){
                        list.add(new Triplet('A', X+1, Y+2));
                    }
                    if(!this.board.getField(X+1, Y+2).isPiece()){
                        list.add(new Triplet('M', X+1, Y+2));
                    }
                }
                if(X+2<8 && Y-1>=0){
                    if(this.board.getField(X+2, Y-1).isPiece() &&
                            this.board.getField(X+2, Y-1).getPiece().getColor() != piece.getColor()){
                        list.add(new Triplet('A', X+2, Y-1));
                    }
                    if(!this.board.getField(X+2, Y-1).isPiece()){
                        list.add(new Triplet('M', X+2, Y-1));
                    }
                }
                if(X+1<8 && Y-2>=0){
                    if(this.board.getField(X+1, Y-2).isPiece() &&
                            this.board.getField(X+1, Y-2).getPiece().getColor() != piece.getColor()){
                        list.add(new Triplet('A', X+1, Y-2));
                    }

                }
            }
            if(this.piece.getType() == PieceTypes.QUEEN){
                for(int i=1; X-i>=0; i++){
                    if(!this.board.getField(X-i, Y).isPiece()){
                        list.add(new Triplet('M', X-i, Y));
                    }
                    else{
                        if(this.board.getField(X-i, Y).getPiece().getColor() != piece.getColor()){
                            list.add(new Triplet('A', X-i, Y));
                        }
                        break;
                    }
                }
                for(int i=1; X-i>=0 && Y-i>=0; i++){
                    if(!this.board.getField(X-i, Y-i).isPiece()){
                        list.add(new Triplet('M', X-i, Y-i));
                    }
                    else{
                        if(this.board.getField(X-i, Y-i).getPiece().getColor() != piece.getColor()){
                            list.add(new Triplet('A', X-i, Y-i));
                        }
                        break;
                    }
                }
                for(int i=1; Y-i>=0; i++){
                    if(!this.board.getField(X, Y-i).isPiece()){
                        list.add(new Triplet('M', X, Y-i));
                    }
                    else{
                        if(this.board.getField(X, Y-i).getPiece().getColor() != piece.getColor()){
                            list.add(new Triplet('A', X, Y-i));
                        }
                        break;
                    }
                }
                for(int i=1; X+i<8 && Y-i>=0; i++){
                    if(!this.board.getField(X+i, Y-i).isPiece()){
                        list.add(new Triplet('M', X+i, Y-i));
                    }
                    else{
                        if(this.board.getField(X+i, Y-i).getPiece().getColor() != piece.getColor()){
                            list.add(new Triplet('A', X+i, Y-i));
                        }
                        break;
                    }
                }
                for(int i=1; X+i<8; i++){
                    if(!this.board.getField(X+i, Y).isPiece()){
                        list.add(new Triplet('M', X+i, Y));
                    }
                    else{
                        if(this.board.getField(X+i, Y).getPiece().getColor() != piece.getColor()){
                            list.add(new Triplet('A', X+i, Y));
                        }
                        break;
                    }
                }
                for(int i=1; X+i<8 && Y+i<8; i++){
                    if(!this.board.getField(X+i, Y+i).isPiece()){
                        list.add(new Triplet('M', X+i, Y+i));
                    }
                    else{
                        if(this.board.getField(X+i, Y+i).getPiece().getColor() != piece.getColor()){
                            list.add(new Triplet('A', X+i, Y+i));
                        }
                        break;
                    }
                }
                for(int i=1; Y+i<8; i++){
                    if(!this.board.getField(X, Y+i).isPiece()){
                        list.add(new Triplet('M', X, Y+i));
                    }
                    else{
                        if(this.board.getField(X, Y+i).getPiece().getColor() != piece.getColor()){
                            list.add(new Triplet('A', X, Y+i));
                        }
                        break;
                    }
                }
                for(int i=1; Y+i<8 && X-i>=0; i++){
                    if(!this.board.getField(X-i, Y+i).isPiece()){
                        list.add(new Triplet('M', X-i, Y+i));
                    }
                    else{
                        if(this.board.getField(X-i, Y+i).getPiece().getColor() != piece.getColor()){
                            list.add(new Triplet('A', X-i, Y+i));
                        }
                        break;
                    }
                }
            }
            if(this.piece.getType() == PieceTypes.KING){
                if(X-1>=0){
                    if(this.board.getField(X-1, Y).isPiece()){
                        list.add(new Triplet('A', X-1, Y));
                    }
                    else{
                        list.add(new Triplet('M', X-1, Y));
                    }
                }
                if(X-1>=0 && Y-1>=0){
                    if(this.board.getField(X-1, Y-1).isPiece()){
                        list.add(new Triplet('A', X-1, Y-1));
                    }
                    else{
                        list.add(new Triplet('M', X-1, Y-1));
                    }
                }
                if(Y-1>=0){
                    if(this.board.getField(X, Y-1).isPiece()){
                        list.add(new Triplet('A', X, Y-1));
                    }
                    else{
                        list.add(new Triplet('M', X, Y-1));
                    }
                }
                if(X+1<8 && Y-1>=0){
                    if(this.board.getField(X+1, Y-1).isPiece()){
                        list.add(new Triplet('A', X+1, Y-1));
                    }
                    else{
                        list.add(new Triplet('M', X+1, Y-1));
                    }
                }
                if(X+1<8){
                    if(this.board.getField(X+1, Y).isPiece()){
                        list.add(new Triplet('A', X+1, Y));
                    }
                    else{
                        list.add(new Triplet('M', X+1, Y));
                    }
                }
                if(X+1<8 && Y+1<8){
                    if(this.board.getField(X+1, Y+1).isPiece()){
                        list.add(new Triplet('A', X+1, Y+1));
                    }
                    else{
                        list.add(new Triplet('M', X+1, Y+1));
                    }
                }
                if(Y+1<8){
                    if(this.board.getField(X, Y+1).isPiece()){
                        list.add(new Triplet('A', X, Y+1));
                    }
                    else{
                        list.add(new Triplet('M', X, Y+1));
                    }
                }
                if(Y+1<8 && X-1>=0){
                    if(this.board.getField(X-1, Y+1).isPiece()){
                        list.add(new Triplet('M', X-1, Y=1));
                    }
                    else{
                        list.add(new Triplet('M', X-1, Y+1));
                    }
                }
            }
            return list;
        }
        return null;
    }
}
