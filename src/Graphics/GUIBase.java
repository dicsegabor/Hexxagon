package Graphics;

import Controls.Coordinate;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GUIBase {

    protected Rectangle2D primaryScreenBounds;
    protected double widthRatio, heightRatio, backgroundX, backgroundY;

    public GUIBase() {

        primaryScreenBounds = Screen.getPrimary().getBounds();
        loadBackGround("Void");
    }

    protected Image loadImage(String fileName) {

        try { return new Image(new FileInputStream("Graphics\\1920x1080\\" + fileName + ".png")); }
        catch (FileNotFoundException e) { System.out.println("File \"" + fileName + ".png\" not found!"); }

        return null;
    }

    protected ImageView loadBackGround(String fileName){

        Image image = loadImage(fileName);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(primaryScreenBounds.getHeight());
        imageView.setFitWidth(primaryScreenBounds.getWidth());

        centerImage(imageView);

        widthRatio = imageView.getLayoutBounds().getWidth() / 1728;
        heightRatio = imageView.getLayoutBounds().getHeight() / 1080;

        return imageView;
    }

    protected void centerImage(ImageView imageView){

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

    protected ImageView makeImageView(String fileName, Point2D place){

        ImageView imageView = new ImageView(loadImage(fileName));

        imageView.setX(backgroundX + place.getX() * widthRatio);
        imageView.setY(backgroundY + place.getY() * heightRatio);
        imageView.setFitWidth(imageView.getImage().getWidth() * widthRatio);
        imageView.setFitWidth(imageView.getImage().getHeight() * heightRatio);

        return imageView;
    }

    protected Button makeButton(String fileName, Point2D place){

        Button button = imageToButton(fileName);

        setButtonPlace(button, place);

        return button;
    }

    protected Button imageToButton(String fileName) {

        Button bt = new Button();

        Image image = loadImage(fileName);

        bt.setBackground(new Background(imageToBackgroundImage(image)));
        bt.setMinSize(image.getWidth() * widthRatio, image.getHeight() * heightRatio);

        return bt;
    }

    protected BackgroundImage imageToBackgroundImage(Image image) {

        BackgroundSize size = new BackgroundSize(image.getWidth() * widthRatio, image.getHeight() * heightRatio, false, false, false, false);
        return new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, size);
    }

    protected void setButtonPlace(Button button, Point2D place){

        button.setLayoutX(backgroundX + place.getX() * widthRatio);
        button.setLayoutY(backgroundY + place.getY() * heightRatio);
    }

    protected void setButtonBackground(Button button, String fileName) {

        button.setBackground(new Background(imageToBackgroundImage(loadImage(fileName))));
    }

    public Coordinate pointToCoordinate(Point2D point){

        Coordinate c = null;

        for(int i = 0; i < 17; i++)
            if((((21 + i * 59.5) * heightRatio) < point.getY() && ((102 + i * 59.5) * heightRatio) > point.getY()))
                for (int j = 0; j < 9; j++)
                    if ((((95 + j * 178) * widthRatio) < point.getX() && ((198 + j * 178) * widthRatio) > point.getX()))
                        c = new Coordinate(j, i);

        return c;
    }
}
