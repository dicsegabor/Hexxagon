package Controls;

import Enums.MoveType;
import org.jetbrains.annotations.NotNull;

public class Move {

    public final Coordinate from;
    public final Coordinate to;
    public final MoveType type;
    public int value;

    public Move(@NotNull Coordinate from, @NotNull Coordinate to){

        this.from = from;
        this.to = to;
        type = from.getDistance(to);
    }

    public Boolean sameResult(@NotNull Move move){

        return to.equals(move.to) && type.equals(MoveType.SHORT);
    }

    public Boolean isValid(){

        return !type.equals(MoveType.INVALID);
    }

    @Override
    public String toString(){

        return "From: " + from + " | To: " + to;
    }
}