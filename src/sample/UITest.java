package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class UITest extends Application {

    public void start(Stage primaryStage) {

        int height = 600;
        int width = 800;

        AnchorPane tileMap = new AnchorPane();
        Scene content = new Scene(tileMap, width, height);
        primaryStage.setScene(content);

        double size = 50, v = Math.sqrt(3) / 2.0;
        for(double y = 0; y < height; y += size * Math.sqrt(3)){

            for(double x = -25, dy = y; x < width; x += (3.0 / 2.0) * size){

                Polygon tile = new Polygon();

                //noinspection RedundantArrayCreation
                tile.getPoints().addAll(new Double[]{
                        x, dy,
                        x+size, dy,
                        x + size * (3.0 / 2.0), dy + size * v,
                        x + size, dy + size * Math.sqrt(3),
                        x, dy + size * Math.sqrt(3),
                        x - (size / 2.0), dy + size * v
                });

                tile.setFill(Paint.valueOf(String.valueOf(Color.GRAY)));
                tile.setStrokeWidth(2);
                tile.setStroke(Paint.valueOf("#000000") );
                tileMap.getChildren().add(tile);

                dy = dy == y ? dy + size * v : y;
            }
        }
        primaryStage.show();
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}