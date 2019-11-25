package Enums;

public enum UnitType {

    BLUE, RED, EMPTY, HOLE, SPACE;

    public UnitType getOpposite(){

        if(this.isEmpty())
            return EMPTY;

        return this.equals(RED) ? BLUE : RED;
    }

    public int getValue(){ return (this.isEmpty() ? 0 : (this.equals(RED) ? 1 : -1)); }

    public Boolean isEmpty(){

        return this.equals(EMPTY);
    }

    public Boolean isUsable() { return !(this.equals(HOLE) || this.equals(SPACE)); }

    public static UnitType parseUnitType(String s){

        switch (s) {

            case "RED": return UnitType.RED;
            case "BLUE": return UnitType.BLUE;
            case "EMPTY": return UnitType.EMPTY;
            case "HOLE": return UnitType.HOLE;
            default: return UnitType.SPACE;
        }
    }
}
