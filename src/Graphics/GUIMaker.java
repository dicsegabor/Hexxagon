package Graphics;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GUIMaker {

    public Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
    private double widthRatio, heightRatio, backgroundX, backgroundY;

    public ImageView loadBackGround(String fileName){

        Image image = null;
        try { image = new Image(new FileInputStream("Graphics\\1920x1080\\" + fileName + ".png")); }
        catch (FileNotFoundException e) { System.out.println("File not found!");}
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

    public RIButton makeButton(String fileName, Point2D place){

        RIButton riButton = null;
        try { riButton = new RIButton(imageToButton(fileName), place); }
        catch (FileNotFoundException e) { System.out.println("File not found!"); }

        placeButton(riButton);

        return riButton;
    }

    public void setExitKey(Stage primaryStage, KeyCode key){

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {

            if (key == event.getCode())
                primaryStage.close();
        });
    }
}
