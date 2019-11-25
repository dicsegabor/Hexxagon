package Controls;

import Enums.UnitType;
import org.jetbrains.annotations.NotNull;

public class Field {

    private final Coordinate position;
    public UnitType content;

    public Field(@NotNull Coordinate position) {

        this.position = position;
        content = UnitType.EMPTY;
    }

    public Field(@NotNull Coordinate position, @NotNull UnitType content) {

        this.position = position;
        setContent(content);
    }

    void setContent(@NotNull UnitType content){

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
    public boolean equals(@NotNull Object o){

        return getValue() == ((Field)o).getValue() && content.equals(((Field)o).content);
    }
}