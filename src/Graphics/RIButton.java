package Graphics;

import javafx.geometry.Point2D;

import java.awt.*;

public class RIButton extends Button {

    private Point2D originalPosition;

    public RIButton(double x, double y){

        super();
        originalPosition = new Point2D(x, y);
    }
}
