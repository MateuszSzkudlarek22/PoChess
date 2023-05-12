import java.util.ArrayList;

public class HotSeatGame extends Game{

    public HotSeatGame(){
        this.board = new Board();
        this.board.setNormalGame();
        this.currentPlayer = ChessColors.WHITE;
        this.currentGameStatus = GameStatus.NORMAL;
    }

    @Override
    public void processMove(Move move) {

    }

    public ArrayList<Triplet> getPossibleMoves(int X, int Y){
        return this.board.getPossibleMoves(X, Y, currentPlayer);
    }
}
