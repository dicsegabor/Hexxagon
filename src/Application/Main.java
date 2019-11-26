package Application;

import Enums.UnitType;
import Game.*;
import Controls.*;
import IO.BoardIOHandler;

public class Main {

    public static void main(String[] args) {

        Board defaultBoard = new Board("defaultBoard");
        BoardIOHandler.save(defaultBoard, "defaultBoard");
        BoardIOHandler.save(defaultBoard, "defaultTestBoard");
        defaultBoard = new Board("emptyBoard");
        BoardIOHandler.save(defaultBoard, "emptyBoard");

        Game game = new Game(defaultBoard);
        game.addAI(UnitType.RED, 1);
        game.addAI(UnitType.BLUE, 1);

        game.start();
    }
}
