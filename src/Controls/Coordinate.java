package Controls;

import Enums.MoveType;

public class Coordinate {

    public int x;
    public int y;

    public Coordinate(int x, int y){

        this.x = x;
        this.y = y;
    }

    public MoveType getDistance(Coordinate c){

        int xDist = Math.abs(this.x - c.x);
        int yDist = Math.abs(this.y - c.y);

        if(c.equals(this))
            return MoveType.INVALID;

        else if (xDist == 1 && yDist == 1 || xDist == 0 && yDist == 2)
            return MoveType.SHORT;

        else if (xDist == 2 && yDist == 0 || xDist == 2 && yDist == 2 || xDist == 1 && yDist == 3 || xDist == 0 && yDist == 4)
            return MoveType.LONG;

        else
            return MoveType.INVALID;
    }

    public Boolean isBetween(Coordinate leftUpper, Coordinate rightLover){

        return x >= leftUpper.x && y <= leftUpper.y && x <= rightLover.x && y >= rightLover.y;
    }

    @Override
    public String toString() {

        return "x = " + x + ", y = " + y;
    }

    @Override
    public boolean equals(Object o){

        return ((Coordinate)o).x == x && ((Coordinate)o).y == y;
    }
}