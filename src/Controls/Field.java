package Controls;

import Enums.UnitType;

public class Field {

    private final Coordinate position;
    public UnitType content;

    public Field( Coordinate position) {

        this.position = position;
        content = UnitType.EMPTY;
    }

    public Field( Coordinate position,  UnitType content) {

        this.position = position;
        setContent(content);
    }

    void setContent( UnitType content){

        this.content = content;
    }

    public Coordinate getPosition(){

        return position;
    }

    public int getValue() {

        return content.getValue();
    }

    public UnitType getContent() {

        return content;
    }

    @Override
    public boolean equals( Object o){

        return getValue() == ((Field)o).getValue() && content.equals(((Field)o).content);
    }
}