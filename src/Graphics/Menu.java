package Graphics;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Menu extends GUIBase {

    private Controller controller;
    private Group root = new Group();

    public Menu(Controller controller) {

        this.controller = controller;

        //Making the background
        root.getChildren().add(loadBackGround("Menu"));

        //Making the main buttons
        Button startGameButton = makeButton("Start Game Button", new Point2D(200, 896));
        startGameButton.setOnAction(event -> {

            controller.primaryStage.getScene().setRoot(controller.game.getRoot());
        });
        root.getChildren().add(startGameButton);

        root.getChildren().add(makeButton("Return To Demo Button", new Point2D(200, 966)));

        Button configureBoardButton = makeButton("Configure Board Button", new Point2D(999, 896));
        configureBoardButton.setOnAction(event -> {

            controller.primaryStage.getScene().setRoot(controller.editor.getRoot());
        });
        root.getChildren().add(configureBoardButton);

        Button quitToDosButton = makeButton("Quit To Dos Button", new Point2D(999, 966));
        quitToDosButton.setOnAction(event -> {

            controller.primaryStage.close();
        });
        root.getChildren().add(quitToDosButton);

        //Making the red player type chooser buttons
        Button redLevelHuman = makeButton("Active Button", new Point2D(184, 205));
        root.getChildren().add(redLevelHuman);

        Button redLevelAI1 = makeButton("Inactive Button", new Point2D(184, 270));
        root.getChildren().add(redLevelAI1);

        Button redLevelAI2 = makeButton("Inactive Button", new Point2D(184, 335));
        root.getChildren().add(redLevelAI2);

        Button redLevelAI3 = makeButton("Inactive Button", new Point2D(184, 400));
        root.getChildren().add(redLevelAI3);

        //Making the blue player type chooser buttons
        Button blueLevelHuman = makeButton("Active Button", new Point2D(983, 205));
        root.getChildren().add(blueLevelHuman);

        Button blueLevelAI1 = makeButton("Inactive Button", new Point2D(983, 270));
        root.getChildren().add(blueLevelAI1);

        Button blueLevelAI2 = makeButton("Inactive Button", new Point2D(983, 335));
        root.getChildren().add(blueLevelAI2);

        Button blueLevelAI3 = makeButton("Inactive Button", new Point2D(983, 400));
        root.getChildren().add(blueLevelAI3);

        //Making some worthless shiny buttons
        root.getChildren().add(makeButton("Active Button", new Point2D(184 , 637)));
        root.getChildren().add(makeButton("Inactive Button", new Point2D(184 , 702)));
        root.getChildren().add(makeButton("Inactive Button", new Point2D(184 , 767)));
        root.getChildren().add(makeButton("Active Button", new Point2D(983 , 637)));
        root.getChildren().add(makeButton("Inactive Button", new Point2D(983 , 702)));
        root.getChildren().add(makeButton("Inactive Button", new Point2D(983 , 767)));
    }

    public void setupStage(Stage stage){

        stage.setFullScreen(true);
        setExitKey(stage, KeyCode.ESCAPE);
        stage.setFullScreenExitHint("");
    }

    private void setExitKey(Stage stage, KeyCode key){

        stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {

            if (key == event.getCode())
                stage.close();
        });
    }

    public Scene makeScene(){

        return new Scene(root, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight(), Color.BLACK);
    }

    public Group getRoot(){

        return root;
    }
}
