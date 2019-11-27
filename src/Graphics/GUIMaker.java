package Graphics;

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

public class GUIMaker {

    public Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
    private double widthRatio, heightRatio, backgroundX, backgroundY;

    public ImageView loadBackGround(String fileName){

        Image image = loadImage(fileName);
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

    private BackgroundImage imageToBackgroundImage(Image image) {

        BackgroundSize size = new BackgroundSize(image.getWidth() * widthRatio, image.getHeight() * heightRatio, false, false, false, false);
        return new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, size);
    }

    private Image loadImage(String fileName) {

        try { return new Image(new FileInputStream("Graphics\\1920x1080\\" + fileName + ".png")); }
        catch (FileNotFoundException e) { System.out.println("File not found!"); }

        return null;
    }

    public void setButtonBackground(Button button, String fileName){

        button.setBackground(new Background(imageToBackgroundImage(loadImage(fileName))));
    }

    private Button imageToButton(String fileName) {

        Button bt = new Button();

        Image image = loadImage(fileName);

        bt.setBackground(new Background(imageToBackgroundImage(image)));
        bt.setMinSize(image.getWidth() * widthRatio, image.getHeight() * heightRatio);

        return bt;
    }

    private void placeNode(Node node, Point2D place){

        node.setLayoutX(backgroundX + place.getX() * widthRatio);
        node.setLayoutY(backgroundY + place.getY() * heightRatio);
    }

    public Group menu(){

        ArrayList<Node> elements = new ArrayList<>();
        elements.add(loadBackGround("Menu"));

        elements.add(makeButton("Start Game Button", new Point2D(200, 896)));
        elements.add(makeButton("Return To Demo Button", new Point2D(200, 966)));
        elements.add(makeButton("Configure Board Button", new Point2D(999, 896)));
        elements.add(makeButton("Quit To Dos Button", new Point2D(999, 966)));


        ArrayList<Button> toggleButtons = new ArrayList<>();

        for(int i = 0; i < 4; i++)
            toggleButtons.add(makeButton("Inactive Button", new Point2D(184, 205 + i * 65)));

        setButtonBackground(toggleButtons.get(0), "Active Button");

        for(int i = 0; i < 4; i++)
            toggleButtons.add(makeButton("Inactive Button", new Point2D(983, 205 + i * 65)));

        setButtonBackground(toggleButtons.get(4), "Active Button");

        
        for(int i = 0; i < 3; i++)
            toggleButtons.add(makeButton("Inactive Button", new Point2D(184 , 637 + i * 65)));

        setButtonBackground(toggleButtons.get(8), "Active Button");

        for(int i = 0; i < 3; i++)
            toggleButtons.add(makeButton("Inactive Button", new Point2D(983 , 637 + i * 65)));

        setButtonBackground(toggleButtons.get(11), "Active Button");

        elements.addAll(toggleButtons);

        return new Group(elements);
    }

    public Group editor(){

        ArrayList<Node> elements = new ArrayList<>();

        elements.add(loadBackGround("Editor"));
        elements.add(makeButton("Menu Button", new Point2D(76, 967)));
        elements.add(makeButton("Restore Button", new Point2D(1393, 81)));
        elements.add(makeButton("Save 1 Button", new Point2D(1355, 5)));

        for(int i = 0; i < 3; i++)
            elements.add(makeButton("Inactive Button", new Point2D(81, 5 + i * 65)));

        setButtonBackground((Button)elements.get(4), "Active Button");

        return  new Group(elements);
    }

    public Scene makeScene(Group group){

        return new Scene(group, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight(), Color.BLACK);
    }

    public Button makeButton(String fileName, Point2D place){

        Button button = imageToButton(fileName);

        placeNode(button, place);

        return button;
    }

    public void setExitKey(Stage primaryStage, KeyCode key){

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {

            if (key == event.getCode())
                primaryStage.close();
        });
    }
}
