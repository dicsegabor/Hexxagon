package Players;

import Controls.Board;
import Controls.Move;
import Enums.UnitType;
import Exeptions.NoValidMoveException;

public interface Player {

    UnitType team = null;
    Board GameBoard = null;

    Move thinkOutMove() throws NoValidMoveException;

    UnitType getTeam();
}
