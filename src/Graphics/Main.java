package Graphics;

import Controls.Board;
import Controls.Converter;
import Controls.Coordinate;
import IO.BoardIOHandler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main extends Application {

    private Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
    private double widthRatio, heightRatio, backgroundX, backgroundY;
    private Board board = BoardIOHandler.load("defaultBoard");

    private ImageView loadBackGround(String fileName) throws FileNotFoundException {

        Image image = new Image(new FileInputStream("Graphics\\1920x1080\\" + fileName + ".png"));
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(primaryScreenBounds.getHeight());
        imageView.setFitWidth(primaryScreenBounds.getWidth());

        centerImage(imageView);

        widthRatio = imageView.getLayoutBounds().getWidth() / 1728;
        heightRatio = imageView.getLayoutBounds().getHeight() / 1080;

        return imageView;
    }

    private void centerImage(ImageView imageView){

        double ratioX = imageView.getFitWidth() / imageView.getImage().getWidth();
        double ratioY = imageView.getFitHeight() / imageView.getImage().getHeight();

        double ratio = Math.min(ratioX, ratioY);

        double w = imageView.getImage().getWidth() * ratio;
        double h = imageView.getImage().getHeight() * ratio;

        imageView.setX((imageView.getFitWidth() - w) / 2);
        imageView.setY((imageView.getFitHeight() - h) / 2);
        backgroundX = imageView.getX();
        backgroundY = imageView.getY();

        imageView.setPreserveRatio(true);
    }

    private Button imageToButton(String fileName) throws FileNotFoundException {

        Button bt = new Button();

        Image image = new Image(new FileInputStream("Graphics\\1920x1080\\" + fileName + ".png"));
        BackgroundSize size = new BackgroundSize(image.getWidth() * widthRatio, image.getHeight() * heightRatio, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, size);

        bt.setBackground(new Background(backgroundImage));
        bt.setMinSize(image.getWidth() * widthRatio, image.getHeight() * heightRatio);

        return bt;
    }

    private void placeButton(RIButton riButton){

        riButton.button.setLayoutX(backgroundX + riButton.originalPosition.getX() * widthRatio);
        riButton.button.setLayoutY(backgroundY + riButton.originalPosition.getY() * heightRatio);
    }

    private RIButton makeButton(String fileName, Point2D place){

        RIButton riButton = null;
        try { riButton = new RIButton(imageToButton(fileName), place); }
        catch (FileNotFoundException e) { System.out.println("File not found!"); }

        placeButton(riButton);

        return riButton;
    }

    private Scene makeScene(String fileName) throws FileNotFoundException {

        ArrayList<Node> elements = new ArrayList<>();
        elements.add(loadBackGround(fileName));

        ArrayList<RIButton> buttons = new ArrayList<>();

        buttons.add(makeButton("Menu Button", new Point2D(76, 967)));
        for(Coordinate c : board.usefulCoordinates)
            buttons.add(makeButton("Hole", Converter.coordinateToPointForHole(c)));

        for(RIButton ribt : buttons)
            elements.add(ribt.button);

        Group root = new Group(elements);
        return new Scene(root, primaryScreenBounds.getMinX(), primaryScreenBounds.getMinY(), Color.BLACK);
    }

    private void setExitKey(Stage primaryStage, KeyCode key){

        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {

            if (key == event.getCode())
                primaryStage.close();
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        setExitKey(primaryStage, KeyCode.ESCAPE);
        primaryStage.setFullScreen(true);

        primaryStage.setScene(makeScene("Game"));

        primaryStage.show();
    }
}
