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

    public Board GameBoard;
    private ArrayList<Player> players;

    public Game( Board GameBoard) {

            this.GameBoard = new Board(GameBoard);
            players = new ArrayList<>(2);
    }

    public void start(){

        try {
            while (true)
                nextPlayer();
        }

        catch (GameEndedException e) { endGame(); GameBoard.resetBoard(); }
    }

    public void endGame(){

        UnitType winner = GameBoard.getWinner();

        if(winner.equals(UnitType.EMPTY))
            System.out.println("DRAW");

        else
            System.out.println(winner + " WON!");
    }

    public void addHuman(UnitType team) {

        if(!players.isEmpty() && !players.get(0).getTeam().equals(team))
            players.add(new Human(team, GameBoard));

        else
            System.out.println("Players must have different team!");
    }

    public void addAI(UnitType team, int level){

        if(players.isEmpty())
            players.add(new AI(team, GameBoard, level));

        else if(!players.isEmpty())
            if(!players.get(0).getTeam().equals(team))
                players.add(new AI(team, GameBoard, level));

        else
            System.out.println("Players must have different team!");
    }

    public void nextPlayer() throws GameEndedException {

        if(GameBoard.testForEnd())
            throw new GameEndedException("");

        for (Player player : players)
            if (!player.getTeam().equals(GameBoard.getPreviousPlayer())) {

                try{ GameBoard.makeMove(player.thinkOutMove()); }
                catch (NoValidMoveException e) { throw new GameEndedException(""); }
            }
    }
}
