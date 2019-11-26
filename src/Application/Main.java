package Application;

import Graphics.*;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    private GUIMaker maker = new GUIMaker();
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        primaryStage.setFullScreen(true);
        maker.setExitKey(primaryStage, KeyCode.ESCAPE);

        primaryStage.setScene(menu());
        primaryStage.show();

    }

    private Scene menu(){

        ArrayList<Node> elements = new ArrayList<>();
        elements.add(maker.loadBackGround("Menu"));

        ArrayList<RIButton> buttons = new ArrayList<>();
        buttons.add(maker.makeButton("Start Game Button", new Point2D(200, 896)));
        buttons.get(0).button.setOnAction(value -> { primaryStage.setScene(game()); primaryStage.setFullScreen(true); });
        buttons.add(maker.makeButton("Return To Demo Button", new Point2D(200, 966)));
        buttons.add(maker.makeButton("Configure Board Button", new Point2D(999, 896)));
        buttons.get(2).button.setOnAction(value -> { primaryStage.setScene(editor()); primaryStage.setFullScreen(true); });
        buttons.add(maker.makeButton("Quit To Dos Button", new Point2D(999, 966)));
        buttons.get(3).button.setOnAction(value -> { primaryStage.close(); });

        for(int j = 0; j < 2; j++)
            for(int i = 0; i < 4; i++)
                buttons.add(maker.makeButton("Inactive Button", new Point2D(184 + j * 799, 205 + i * 65)));

        for(int j = 0; j < 2; j++)
            for(int i = 0; i < 3; i++)
                buttons.add(maker.makeButton("Inactive Button", new Point2D(184 + j * 799, 637 + i * 65)));

        for(RIButton riBt : buttons)
            elements.add(riBt.button);

        return new Scene(new Group(elements), maker.primaryScreenBounds.getWidth(), maker.primaryScreenBounds.getHeight(), Color.BLACK);
    }

    private Scene editor(){

        ArrayList<Node> elements = new ArrayList<>();
        elements.add(maker.loadBackGround("Editor"));

        ArrayList<RIButton> buttons = new ArrayList<>();
        buttons.add(maker.makeButton("Menu Button", new Point2D(76, 967)));
        buttons.get(0).button.setOnAction(value -> { primaryStage.setScene(menu()); primaryStage.setFullScreen(true); } );
        buttons.add(maker.makeButton("Restore Button", new Point2D(1393, 81)));
        buttons.add(maker.makeButton("Save 1 Button", new Point2D(1355, 5)));

        for(int i = 0; i < 3; i++)
            buttons.add(maker.makeButton("Inactive Button", new Point2D(81, 5 + i * 65)));

        for(RIButton riBt : buttons)
            elements.add(riBt.button);

        return new Scene(new Group(elements), maker.primaryScreenBounds.getWidth(), maker.primaryScreenBounds.getHeight(), Color.BLACK);
    }

    private Scene game(){

        ArrayList<Node> elements = new ArrayList<>();
        elements.add(maker.loadBackGround("Game"));

        ArrayList<RIButton> buttons = new ArrayList<>();
        buttons.add(maker.makeButton("Menu Button", new Point2D(76, 967)));
        buttons.get(0).button.setOnAction(value -> { primaryStage.setScene(menu()); primaryStage.setFullScreen(true); } );

        for(RIButton riBt : buttons)
            elements.add(riBt.button);

        return new Scene(new Group(elements), maker.primaryScreenBounds.getWidth(), maker.primaryScreenBounds.getHeight(), Color.BLACK);
    }
}
