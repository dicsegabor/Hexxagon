package Game;

import Controls.Board;
import Enums.UnitType;
import Exeptions.GameEndedException;
import Exeptions.NoValidMoveException;
import Players.AI;
import Players.Human;
import Players.Player;

import java.util.ArrayList;

public class Game {

    private Board GameBoard;
    private ArrayList<Player> players;

    public Game(Board GameBoard, Player red, Player blue) {

            this.GameBoard = GameBoard;
            players = new ArrayList<>(2);

            if(!red.getTeam().equals(blue.getTeam())){

                players.add(red);
                players.add(blue);
            }
    }

    public Boolean nextPlayer() {

        if(GameBoard.testForEnd())
            return false;

        for (Player player : players)
            if (!player.getTeam().equals(GameBoard.getPreviousPlayer())) {

                try{ GameBoard.makeMove(player.thinkOutMove(GameBoard)); }
                catch (NoValidMoveException e) { return false; }
            }

        return true;
    }
}
