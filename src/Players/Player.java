package Players;

import Controls.*;
import Enums.UnitType;
import Exeptions.NoValidMoveException;

public interface Player {

    UnitType team = null;
    Board GameBoard = null;

    Move thinkOutMove() throws NoValidMoveException;

    UnitType getTeam();
}
