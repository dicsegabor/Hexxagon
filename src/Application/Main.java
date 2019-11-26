package Application;

import Controls.Board;
import Enums.UnitType;
import Game.Game;
import IO.BoardIOHandler;

public class Main {

    public static void main(String[] args) {

        Board.refreshBoards();
        Board defaultBoard = BoardIOHandler.load("defaultBoard");

        Game game = new Game(defaultBoard);
        game.addAI(UnitType.RED, 1);
        game.addAI(UnitType.BLUE, 1);

        game.start();
    }
}
