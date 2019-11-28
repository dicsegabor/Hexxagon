package Application;

import Controls.Board;
import Enums.UnitType;
import Game.Game;
import IO.BoardIOHandler;
import Players.AI;
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

        AI red = new AI(UnitType.RED, 1);
        AI blue = new AI(UnitType.BLUE, 1);
        Game game = new Game(b, red, blue);

        while (game.nextPlayer());

        System.out.println(b);
        System.out.println(b.getWinner());
    }
}
