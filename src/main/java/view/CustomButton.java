package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ChessColors;
import model.PieceTypes;
import model.Triplet;
import viewmodel.GameStatus;
import viewmodel.InterfaceChange;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomButton extends Button {
    GameInterface gameInterface;
    int X;
    int Y;
    boolean active;
    char move;

    CustomButton(GameInterface game) {
        super();
        this.gameInterface = game;
        this.setMaxWidth(50);
        this.setMaxHeight(50);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-radius: 0");
        CustomButton bt = this;
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (bt.active) {
                    bt.setInactive();
                    for (Triplet t : gameInterface.list) {
                        gameInterface.chessboard[t.X][t.Y].setInactive();
                        switch (t.take) {
                            case 'M', 'P', 'C' -> {
                                gameInterface.chessboard[t.X][t.Y].setGraphic(null);
                                if ((t.X + t.Y) % 2 == 0)
                                    gameInterface.chessboard[t.X][t.Y].
                                            setStyle("-fx-background-color: #A52A2A");
                                else gameInterface.chessboard[t.X][t.Y].
                                        setStyle("-fx-background-color: #FFFFFF");
                            }
                            case 'A', 'T' -> {
                                if ((t.X + t.Y) % 2 == 0)
                                    gameInterface.chessboard[t.X][t.Y].
                                            setStyle("-fx-background-color: #A52A2A");
                                else gameInterface.chessboard[t.X][t.Y].
                                        setStyle("-fx-background-color: #FFFFFF");
                            }
                        }
                    }
                    if(bt.move == 'P' || bt.move == 'E'){
                        getPromoteStage(gameInterface.current.X, gameInterface.current.Y, bt.X, bt.Y).show();
                        gameInterface.current = null;
                        gameInterface.list = null;
                        return;
                    }
                    ArrayList<InterfaceChange> list = gameInterface.game.makeMove(
                            gameInterface.current.X, gameInterface.current.Y,
                            bt.X, bt.Y, bt.move, null);
                    for (InterfaceChange i : list) {
                        if(i.X==-1){
                            endStage().show();
                            return;
                        }
                        if (i.change == null) gameInterface.chessboard[i.X][i.Y].setGraphic(null);
                        else
                            if(i.flag) gameInterface.chessboard[i.X][i.Y].setStyle("-fx-background-color: #FF0000");
                            else gameInterface.chessboard[i.X][i.Y].setGraphic(gameInterface.set.getImageView(i.color, i.change));
                    }
                    gameInterface.current = null;
                    gameInterface.list = null;
                    gameInterface.game.print();
                    return;
                }
                if (gameInterface.game.isTaken(X, Y)) {
                    System.out.println(X + " " + Y);
                    if (gameInterface.current == null) {
                        ArrayList<Triplet> list = gameInterface.game.getPossibleMoves(X, Y);
                        if (list != null && !list.isEmpty()) {
                            gameInterface.list = list;
                            gameInterface.current = bt;
                        } else return;
                        for (Triplet t : list) {
                            gameInterface.chessboard[t.X][t.Y].setActive();
                            gameInterface.chessboard[t.X][t.Y].move = t.take;
                            switch (t.take) {
                                case 'M', 'P', 'C' -> {
                                    ImageView img = new ImageView(new Image("/green_circle.png"));
                                    img.setFitHeight(15);
                                    img.setFitWidth(15);
                                    gameInterface.chessboard[t.X][t.Y].setGraphic(img);
                                }
                                case 'A', 'E', 'T' -> {
                                    gameInterface.chessboard[t.X][t.Y].setStyle("-fx-background-color: #FF0000");
                                }
                            }
                        }
                        return;
                    }
                    if (gameInterface.current == bt) {
                        for (Triplet t : gameInterface.list) {
                            gameInterface.chessboard[t.X][t.Y].setInactive();
                            switch (t.take) {
                                case 'M', 'P', 'C' -> {
                                    gameInterface.chessboard[t.X][t.Y].setGraphic(null);
                                    if ((t.X + t.Y) % 2 == 0)
                                        gameInterface.chessboard[t.X][t.Y].
                                                setStyle("-fx-background-color: #A52A2A");
                                    else gameInterface.chessboard[t.X][t.Y].
                                            setStyle("-fx-background-color: #FFFFFF");
                                }
                                case 'A', 'E', 'T' -> {
                                    if ((t.X + t.Y) % 2 == 0)
                                        gameInterface.chessboard[t.X][t.Y].
                                                setStyle("-fx-background-color: #A52A2A");
                                    else gameInterface.chessboard[t.X][t.Y].
                                            setStyle("-fx-background-color: #FFFFFF");
                                }
                            }
                        }
                        gameInterface.current = null;
                        gameInterface.list = null;
                    }
                }
            }
        });
    }

    void setActive() {
        active = true;
    }

    void setInactive() {
        active = false;
    }

    void setFigure(PieceTypes piece, ChessColors color) {
        this.setGraphic(gameInterface.set.getImageView(color, piece));
    }

    Stage getPromoteStage(int fromX, int fromY, int toX, int toY) {
        Stage stage = new Stage();
        stage.setTitle("Wybierz figurę");
        GridPane pane = new GridPane();
        Button rook = new Button("Rook");
        rook.setPrefHeight(70);
        rook.setPrefWidth(120);
        rook.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<InterfaceChange> list = gameInterface.game.
                        makeMove(fromX, fromY, toX, toY, 'P', PieceTypes.ROOK);
                for (InterfaceChange i : list) {
                    if (i.change == null) gameInterface.chessboard[i.X][i.Y].setGraphic(null);
                    else
                        gameInterface.chessboard[i.X][i.Y].setGraphic(gameInterface.set.getImageView(i.color, i.change));
                }
                stage.close();
            }
        });
        Button bishop = new Button("Bishop");
        bishop.setPrefWidth(120);
        bishop.setPrefHeight(70);
        bishop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<InterfaceChange> list = gameInterface.game.
                        makeMove(fromX, fromY, toX, toY, 'P', PieceTypes.BISHOP);
                for (InterfaceChange i : list) {
                    if (i.change == null) gameInterface.chessboard[i.X][i.Y].setGraphic(null);
                    else
                        gameInterface.chessboard[i.X][i.Y].setGraphic(gameInterface.set.getImageView(i.color, i.change));
                }
                stage.close();
            }
        });
        Button knight = new Button("Knight");
        knight.setPrefWidth(120);
        knight.setPrefHeight(70);
        knight.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<InterfaceChange> list = gameInterface.game.
                        makeMove(fromX, fromY, toX, toY, 'P', PieceTypes.KNIGHT);
                for (InterfaceChange i : list) {
                    if (i.change == null) gameInterface.chessboard[i.X][i.Y].setGraphic(null);
                    else
                        gameInterface.chessboard[i.X][i.Y].setGraphic(gameInterface.set.getImageView(i.color, i.change));
                }
                stage.close();
            }
        });
        Button queen = new Button("Queen");
        queen.setPrefWidth(120);
        queen.setPrefHeight(70);
        queen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<InterfaceChange> list = gameInterface.game.
                        makeMove(fromX, fromY, toX, toY, 'P', PieceTypes.QUEEN);
                for (InterfaceChange i : list) {
                    if (i.change == null) gameInterface.chessboard[i.X][i.Y].setGraphic(null);
                    else
                        gameInterface.chessboard[i.X][i.Y].setGraphic(gameInterface.set.getImageView(i.color, i.change));
                }
                stage.close();
            }
        });
        pane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(pane, 300, 200);
        stage.setScene(scene);
        pane.add(rook, 1, 1);
        pane.add(bishop, 1, 2);
        pane.add(knight, 2, 1);
        pane.add(queen, 2, 2);
        return stage;
    }

    Stage endStage(){
        Stage stage = new Stage();
        Text text = new Text(gameInterface.game.currentGameStatus == GameStatus.BLACKMATE? "White wins!!!" : "Black wins!!!");
        Button saveGame = new Button("Save game");
        saveGame.setPrefHeight(70);
        saveGame.setPrefWidth(120);
        Button endGame = new Button("End game");
        endGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
                gameInterface.close();
            }
        });
        endGame.setPrefHeight(70);
        endGame.setPrefWidth(120);
        VBox root = new VBox(text, /*saveGame,*/ endGame);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(30);
        Scene scene = new Scene(root, 250, 300);
        stage.setScene(scene);
        return stage;
    }
}

class PieceSet {
    HashMap<PieceTypes, String> whiteMap;
    HashMap<PieceTypes, String> blackMap;

    PieceSet() {
        whiteMap = new HashMap<>();
        blackMap = new HashMap<>();
        whiteMap.put(PieceTypes.PAWN, "/pawn_white.png");
        blackMap.put(PieceTypes.PAWN, "/pawn_black.png");
        whiteMap.put(PieceTypes.ROOK, "/rook_white.png");
        blackMap.put(PieceTypes.ROOK, "/rook_black.png");
        whiteMap.put(PieceTypes.KNIGHT, "/knight_white.png");
        blackMap.put(PieceTypes.KNIGHT, "/knight_black.png");
        whiteMap.put(PieceTypes.BISHOP, "/bishop_white.png");
        blackMap.put(PieceTypes.BISHOP, "/bishop_black.png");
        whiteMap.put(PieceTypes.KING, "/king_white.png");
        blackMap.put(PieceTypes.KING, "/king_black.png");
        whiteMap.put(PieceTypes.QUEEN, "/queen_white.png");
        blackMap.put(PieceTypes.QUEEN, "/queen_black.png");
    }

    ImageView getImageView(ChessColors color, PieceTypes piece) {
        String string = color == ChessColors.BLACK ? blackMap.get(piece) : whiteMap.get(piece);
        System.out.println(string);
        ImageView img = new ImageView(new Image(string));
        img.setFitWidth(35);
        img.setFitHeight(35);
        return img;
    }
}
