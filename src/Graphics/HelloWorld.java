package Graphics;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HelloWorld extends Application {

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        Image image1 = new Image(new FileInputStream("Graphics\\Menu.png"));
        ImageView imageView1 = new ImageView(image1);
        imageView1.setX(0);
        imageView1.setY(0);

        Button bt = new Button("HE");
        bt.setLayoutY(40);
        bt.setLayoutX(40);
        bt.setOnAction(value -> { System.out.println("hello"); } );

        Group root = new Group(imageView1, bt);
        Scene scene = new Scene(root, 960, 617);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}