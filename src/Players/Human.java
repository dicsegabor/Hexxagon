package Players;

import Controls.Board;
import Controls.Move;
import Enums.UnitType;

public class Human implements Player{

    public UnitType team;
    private Board GameBoard;

    public Human(UnitType team, Board GameBoard) {

        this.team = team;
        this.GameBoard = GameBoard;
    }

    public Move thinkOutMove(){

        return null;
    }

    public UnitType getTeam(){

        return team;
    }
}
