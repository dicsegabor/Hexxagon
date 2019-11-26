package Application;

import Enums.UnitType;
import Game.*;
import Controls.*;
import IO.BoardIOHandler;

public class Main {

    public static void main(String[] args) {

        Board defaultBoard = BoardIOHandler.load("defaultBoard");

        Game game = new Game(defaultBoard);
        game.addAI(UnitType.RED, 1);
        game.addAI(UnitType.BLUE, 1);

        game.start();
    }
}
