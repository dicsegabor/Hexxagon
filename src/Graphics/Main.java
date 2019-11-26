package Graphics;

import javafx.application.Application;
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

    private void placeButton(Button button, double x, double y){

        button.setLayoutX(backgroundX + x * widthRatio);
        button.setLayoutY(backgroundY + y * heightRatio);
    }

    private Button makeButton(String fileName, double x, double y){

        Button bt = null;

        try { bt = imageToButton(fileName); } catch (FileNotFoundException e) { System.out.println("File not found!"); }

        placeButton(bt, x, y);

        return bt;
    }

    private Scene makeScene(String fileName) throws FileNotFoundException {

        ArrayList<Node> elements = new ArrayList<>();
        elements.add(loadBackGround("Game"));
        elements.add(makeButton("Menu Button", 76, 967));
        elements.add(makeButton("Blue", 808, 21));
        Group root = new Group(elements);

        return new Scene(root, primaryScreenBounds.getMinX(), primaryScreenBounds.getMinY(), Color.WHITE);
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
