package Graphics;

import javafx.geometry.Point2D;

import javafx.scene.control.Button;

public class RIButton {

    public Point2D originalPosition;
    public Button button;

    public RIButton(Button button, Point2D point){

        this.button = button;
        originalPosition = point;
    }
}
