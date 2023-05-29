package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class CustomButton extends Button {
    int X;
    int Y;
    CustomGridPane pane;

    public void initialize(int X, int Y, CustomGridPane pane) {
        this.X = X;
        this.Y = Y;
        this.pane = pane;
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (pane.button == null){
                    pane.setInterface(pane.game.getPossibleMoves(X, Y));
                }
            }
        });
    }
}
