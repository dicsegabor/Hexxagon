package Graphics;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Menu extends Application {

    private Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();

    private ImageView loadBackGround(String fileName) throws FileNotFoundException {

        Image image = new Image(new FileInputStream("Graphics\\1920x1080\\" + fileName + ".png"));
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(primaryScreenBounds.getHeight());
        imageView.setFitWidth(primaryScreenBounds.getWidth());

        centerImage(imageView);

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

        imageView.setPreserveRatio(true);
    }

    private Scene makeScene(String fileName) throws FileNotFoundException {

        ArrayList<Node> elements = new ArrayList<>();
        elements.add(loadBackGround(fileName));
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

        primaryStage.setScene(makeScene("Menu"));

        primaryStage.show();
    }
}
