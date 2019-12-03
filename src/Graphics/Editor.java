package Graphics;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;

public class Editor extends GUIBase{

    private Controller controller;
    private Group root = new Group();

    public Editor(Controller controller) {

        this.controller = controller;

        //Making the background
        root.getChildren().add(loadBackGround("Editor"));

        //Making the main buttons
        Button menuButton = makeButton("Menu Button", new Point2D(76, 967));
        menuButton.setOnAction(event -> {

            controller.primaryStage.getScene().setRoot(controller.menu.getRoot());
        });
        root.getChildren().add(menuButton);

        Button restoreButton =  makeButton("Restore Button", new Point2D(1393, 81));
        root.getChildren().add(restoreButton);

        Button save1Button = makeButton("Save 1 Button", new Point2D(1355, 5));
        root.getChildren().add(save1Button);

        //Making the placing chooser buttons
        Button chooseHoleButton = makeButton("Active Button", new Point2D(81, 5));
        root.getChildren().add(chooseHoleButton);

        Button chooseRedButton = makeButton("Inactive Button", new Point2D(81, 70));
        root.getChildren().add(chooseRedButton);

        Button chooseBlueButton = makeButton("Inactive Button", new Point2D(81, 135));
        root.getChildren().add(chooseBlueButton);
    }

    public Group getRoot(){

        return root;
    }
}
