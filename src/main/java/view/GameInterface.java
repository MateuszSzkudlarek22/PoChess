package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.ChessColors;
import model.PieceTypes;
import model.Triplet;
import viewmodel.Game;
import viewmodel.HotSeatGame;

import java.util.ArrayList;

public class GameInterface extends Stage{
    CustomButton[][] chessboard;
    CustomButton current;
    ArrayList<Triplet> list;
    PieceSet set;
    HotSeatGame game;

    GameInterface(){
        GridPane gdpane = new GridPane();
        set = new PieceSet();
        gdpane.setGridLinesVisible( true );
        gdpane.getColumnConstraints().addAll( new ColumnConstraints( 50 ), new ColumnConstraints(50), new ColumnConstraints( 50 ), new ColumnConstraints(50), new ColumnConstraints(50), new ColumnConstraints(50), new ColumnConstraints(50), new ColumnConstraints(50));
        gdpane.getRowConstraints().addAll(new RowConstraints(50), new RowConstraints(50), new RowConstraints(50), new RowConstraints(50), new RowConstraints(50), new RowConstraints(50), new RowConstraints(50), new RowConstraints(50));
        BorderPane pane = new BorderPane();
        gdpane.setAlignment(Pos.CENTER);
        chessboard = new CustomButton[8][8];
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                chessboard[i][j] = new CustomButton(this);
                chessboard[i][j].X = i;
                chessboard[i][j].Y = j;
                if((i+j)%2==0) chessboard[i][j].setStyle("-fx-background-color: #A52A2A");
                else chessboard[i][j].setStyle("-fx-background-color: #FFFFFF");
                gdpane.add(chessboard[i][j], i, 7-j);
            }
        }
        this.setScene(new Scene(pane, 500, 600));
        pane.setCenter(gdpane);
        setGame();
        game = new HotSeatGame();
    }

    void setGame(){
        chessboard[0][0].setGraphic(set.getImageView(ChessColors.WHITE, PieceTypes.ROOK));
        chessboard[1][0].setGraphic(set.getImageView(ChessColors.WHITE, PieceTypes.KNIGHT));
        chessboard[2][0].setGraphic(set.getImageView(ChessColors.WHITE, PieceTypes.BISHOP));
        chessboard[3][0].setGraphic(set.getImageView(ChessColors.WHITE, PieceTypes.QUEEN));
        chessboard[4][0].setGraphic(set.getImageView(ChessColors.WHITE, PieceTypes.KING));
        chessboard[5][0].setGraphic(set.getImageView(ChessColors.WHITE, PieceTypes.BISHOP));
        chessboard[6][0].setGraphic(set.getImageView(ChessColors.WHITE, PieceTypes.KNIGHT));
        chessboard[7][0].setGraphic(set.getImageView(ChessColors.WHITE, PieceTypes.ROOK));
        for(int i=0; i<8; i++){
            chessboard[i][6].setGraphic(set.getImageView(ChessColors.BLACK, PieceTypes.PAWN));
            chessboard[i][1].setGraphic(set.getImageView(ChessColors.WHITE, PieceTypes.PAWN));
        }
        chessboard[0][7].setGraphic(set.getImageView(ChessColors.BLACK, PieceTypes.ROOK));
        chessboard[1][7].setGraphic(set.getImageView(ChessColors.BLACK, PieceTypes.KNIGHT));
        chessboard[2][7].setGraphic(set.getImageView(ChessColors.BLACK, PieceTypes.BISHOP));
        chessboard[3][7].setGraphic(set.getImageView(ChessColors.BLACK, PieceTypes.QUEEN));
        chessboard[4][7].setGraphic(set.getImageView(ChessColors.BLACK, PieceTypes.KING));
        chessboard[5][7].setGraphic(set.getImageView(ChessColors.BLACK, PieceTypes.BISHOP));
        chessboard[6][7].setGraphic(set.getImageView(ChessColors.BLACK, PieceTypes.KNIGHT));
        chessboard[7][7].setGraphic(set.getImageView(ChessColors.BLACK, PieceTypes.ROOK));
    }
}
