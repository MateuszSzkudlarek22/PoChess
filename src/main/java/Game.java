public abstract class Game {
    ChessColors currentPlayer;
    Board board;
    GameStatus currentGameStatus;

    public abstract void processMove(Move move);
}
