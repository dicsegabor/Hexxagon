package Players;

import Controls.Board;
import Controls.Move;
import Enums.UnitType;
import Exeptions.NoValidMoveException;

public interface Player {

    UnitType team = null;
    Board GameBoard = null;

    Move thinkOutMove(Board board) throws NoValidMoveException;

    UnitType getTeam();
}
