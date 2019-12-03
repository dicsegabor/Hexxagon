package Graphics;

import Controls.AI;
import Controls.Board;
import IO.BoardIOHandler;
import javafx.stage.Stage;

public class Controller {

    public Menu menu;
    public Game game;
    public Editor editor;

    public Board gameBoard;
    public AI red , blue;

    public Stage primaryStage;

    public Controller(Stage primaryStage){

        this.primaryStage = primaryStage;

        gameBoard = BoardIOHandler.load("Save 1");
        red = null;
        blue = null;

        menu = new Menu(this);
        game = new Game(this);
        editor = new Editor(this);
    }
}
