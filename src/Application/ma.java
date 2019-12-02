package Application;

import Controls.Board;
import Enums.UnitType;
import IO.BoardIOHandler;
import Controls.AI;
import javafx.application.Application;
import javafx.stage.Stage;

public class ma extends Application {

    @Override
    public void start(Stage primaryStage){

        Board.refreshBoards();
        Board b = new Board("defaultBoard");
        BoardIOHandler.save(b, "defaultBoard");
        BoardIOHandler.save(b, "Save 1");
        BoardIOHandler.save(b, "Save 2");
        BoardIOHandler.save(b, "Save 3");
        BoardIOHandler.save(b, "Save 4");
        BoardIOHandler.save(b, "Save 5");

        System.out.println(b);
    }
}
