package Players;

import Application.Main;
import Controls.Board;
import Controls.Coordinate;
import Controls.Move;
import Enums.UnitType;

public class Human implements Player{

    public UnitType team;
    private Main game;

    public Human(UnitType team, Main game) {

        this.team = team;
        this.game = game;
    }

    public Move thinkOutMove(Board board){

        game.makeMove(game.waitForPlayerInteraction());
        return null;
    }

    public UnitType getTeam(){

        return team;
    }
}
