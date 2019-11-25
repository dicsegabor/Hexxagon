package Application;

import Enums.UnitType;
import Game.*;
import Controls.*;

public class Main {

    public static void main(String[] args) {

        Board b = new Board("defaultBoard");

        Game game = new Game(b);
        game.addAI(UnitType.RED, 1);
        game.addAI(UnitType.BLUE, 1);

        game.start();
    }
}
