package Graphics;

import Controls.Coordinate;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class Game extends GUIBase {

    private Controller controller;
    private Group root = new Group();
    
    private ArrayList<Button> currentSelection;
    private ArrayList<Button> currentUnits = new ArrayList<>();

    public Game(Controller controller) {

        this.controller = controller;

        //Making the background
        root.getChildren().add(loadBackGround("Game"));

        //Making the main buttons
        Button menuButton = makeButton("Menu Button", new Point2D(76, 967));
        menuButton.setOnAction(event -> {

            controller.primaryStage.getScene().setRoot(controller.menu.getRoot());
        });
        root.getChildren().add(menuButton);
    }

    public void addUnits() {

        root.getChildren().removeAll(currentUnits);
        currentUnits.clear();

        for (Coordinate coordinate : controller.gameBoard.coordinates) {

            switch (controller.gameBoard.getField(coordinate).getContent()){

                case RED:
                    currentUnits.add(makeButton("Red", Coordinate.coordinateToPoint(coordinate)));
                    
                    break;

                case BLUE:
                    currentUnits.add(makeButton("Blue", Coordinate.coordinateToPoint(coordinate)));
                    break;

                case HOLE:
                    currentUnits.add(makeButton("Hole", Coordinate.coordinateToPoint(coordinate)));
                    break;
            }
        }

        root.getChildren().addAll(currentUnits);
    }

    public Group getRoot(){

        return root;
    }
}
