package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {
        VBox root = new VBox(newText(), getOnePlayerButton(), getTwoPlayersButton());
        root.setSpacing(30);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 600, 500);
        root.setBackground(new Background(new BackgroundImage(
                new Image("/Background.jpg"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0,1.0,false, false, false, true))));
        stage.setScene(scene);
        stage.show();
    }

    private Button getOnePlayerButton(){
        Button button = new Button("1 player");
        button.setPrefHeight(40);
        button.setPrefWidth(250);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = new Stage();
                stage.show();
            }
        });
        return button;
    }

    private Text newText(){
        Text text = new Text("PO_Chess");
        text.setFont(new Font("Calibri", 80));
        text.setFill(Color.BLUE);
        return text;
    }

    private Button getTwoPlayersButton(){
        Button button = new Button("2 players");
        button.setPrefWidth(250);
        button.setPrefHeight(40);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new GameInterface().show();
            }
        });
        return button;
    }
}
