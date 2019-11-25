package Controls;

import Enums.MoveType;

public class Move {

    public final Coordinate from;
    public final Coordinate to;
    public final MoveType type;
    public int value;

    public Move( Coordinate from,  Coordinate to){

        this.from = from;
        this.to = to;
        type = from.getDistance(to);
    }

    public Boolean sameResult( Move move){

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