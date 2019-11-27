package Players;

import Controls.Board;
import Controls.Move;
import Enums.UnitType;

public class Human implements Player{

    public UnitType team;
    private Board GameBoard;

    public Human(UnitType team) {

        this.team = team;
    }

    public Move thinkOutMove(Board board){

        GameBoard = board;
        return null;
    }

    public UnitType getTeam(){

        return team;
    }
}
