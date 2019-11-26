package Controls;

import javafx.geometry.Point2D;

public class Converter {

    public static Coordinate pointToCoordinate(double x, double y){

        Coordinate c = null;

        for(int i = 0; i < 17; i++)
            for(int j = 0; j < 9; j++)
                if(95 + j * 178 > x && 21 + i * 59.5 > y)
                    c = new Coordinate(j - 1, i - 1);

        assert c != null;
        return c;
    }

    public static Point2D coordinateToPoint(Coordinate c){

        double x = 95 + c.x * 178.25;
        double y = 21 + c.y * 59.5;

        return new Point2D(x, y);
    }
}
