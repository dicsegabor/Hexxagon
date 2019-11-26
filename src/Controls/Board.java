package Controls;

import Enums.*;
import Exeptions.NoValidMoveException;
import Players.AI;

import java.io.*;
import java.util.ArrayList;

public class Board implements Serializable {

    private final int WIDTH = 9, HEIGHT = 17;
    private final ArrayList<Coordinate> coordinates = new ArrayList<>();
    private final Field[][] OriginalBoard = new Field[HEIGHT][WIDTH];

    private ArrayList<Move> moves = new ArrayList<>();
    private Field[][] GameBoard = new Field[HEIGHT][WIDTH];
    public final ArrayList<Coordinate> usefulCoordinates = new ArrayList<>();

    public Board(String fileName){}

    public Board(Board board){

        generateCoordinates();
        setGameBoard(board);
        getUsefulCoordinates();
        setOriginalBoard();
    }

    public void resetBoard(){

        for(Coordinate c : usefulCoordinates)
            getField(c).setContent(OriginalBoard[c.y][c.x].getContent());

        moves.clear();
    }

    private void setGameBoard(Board board){

        for(Coordinate c : board.coordinates)
            GameBoard[c.y][c.x] = new Field(board.getField(c).getPosition(), board.getField(c).getContent());
    }

    private void setOriginalBoard(){

        for(Coordinate c : coordinates)
            OriginalBoard[c.y][c.x] = new Field(getField(c).getPosition(), getField(c).getContent());
    }

    private void generateCoordinates(){

        for(int y = 0; y < HEIGHT; y++)
            for(int x = 0; x < WIDTH; x++)
                coordinates.add(new Coordinate(x, y));
    }

    private void getUsefulCoordinates(){

        for(Coordinate c : coordinates)
            if(getField(c).getContent().isUsable())
                usefulCoordinates.add(c);
    }

    public void makeMove( Move move) {

        moves.add(move);

        getField(move.to).setContent(getField(move.from).getContent());

        if(move.type.equals(MoveType.LONG))
            getField(move.from).setContent(UnitType.EMPTY);

        conquerAdjacentFields(move.to);
    }

    private void conquerAdjacentFields(Coordinate center){

        ArrayList<Coordinate> adjacentFields = getFieldCoordinatesInRange(center, 1);
        selectEnemyFields(adjacentFields);

        for(Coordinate c : adjacentFields)
            getField(c).setContent(getField(center).getContent());
    }

    public ArrayList<Coordinate> getFieldCoordinatesInRange( Coordinate center, int range) {

        ArrayList<Coordinate> fields = new ArrayList<>();

        int lowerY = center.y - 2 * range;
        if(lowerY < 0) lowerY = 0;

        int higherY = center.y + 2 * range;
        if (higherY > HEIGHT - 1) higherY = HEIGHT;

        int lowerX = center.x - range;
        if(lowerX < 0) lowerX = 0;

        int higherX = center.x + range;
        if (higherX > WIDTH - 1) higherX = WIDTH;

        Coordinate leftUpper = new Coordinate(lowerX, higherY);
        Coordinate rightLower = new Coordinate(higherX, lowerY);

        for(Coordinate c : coordinates)
            if(c.isBetween(leftUpper, rightLower))
                if(getField(c).getContent().isUsable())
                    fields.add(c);

        return fields;
    }

    public void selectEmptyFields( ArrayList<Coordinate> fields){

        fields.removeIf((f) -> !getField(f).getContent().isEmpty());
    }

    private void selectEnemyFields( ArrayList<Coordinate> fields) {

        UnitType enemy = getPreviousPlayer();
        fields.removeIf((f) -> !getField(f).getContent().equals(enemy.getOpposite()));
    }

    public Field getField( Coordinate coordinate){

        return GameBoard[coordinate.y][coordinate.x];
    }

    public Field getField(double x, double y){

        Coordinate c = null;

        for(int i = 0; i < HEIGHT; i++)
            for(int j = 0; j < WIDTH; j++)
                if(95 + j * 178 > x && 21 + i * 118.5 > y)
                   c = new Coordinate(j - 1, i - 1);

        assert c != null;
        return getField(c);
    }

    public UnitType getPreviousPlayer() {

        if(moves.isEmpty())
            return UnitType.BLUE;

        return getField(moves.get(moves.size() - 1).to).getContent();
    }

    public Boolean testForEnd() {

        Board testBoard = new Board(this);

        AI tester = new AI(getPreviousPlayer().getOpposite(), testBoard, 1);
        try{ tester.thinkOutMove(); }
        catch (NoValidMoveException e) { return true; }

        return false;
    }

    public int getValue() {

        int value = 0;
        for(Coordinate c : usefulCoordinates)
            value += getField(c).getValue();

        return value;
    }

    public UnitType getWinner() {

        int winner = getValue();

        if(winner < 0)
            return UnitType.BLUE;

        else if(winner > 0)
            return UnitType.RED;

        else
            return UnitType.EMPTY;
    }

    @Override
    public boolean equals( Object o){

        if(o.getClass().equals(Board.class)) {

            for (int i = 0; i < this.coordinates.size(); i++) {

                Field thisField = getField(coordinates.get(i));
                Field bField = ((Board) o).getField(((Board) o).coordinates.get(i));

                if (!thisField.equals(bField))
                    return false;
            }

            return true;
        }

        return false;
    }

    @Override
    public String toString() {

        StringBuilder board = new StringBuilder();

        for(int y = 0; y < HEIGHT; y++) {

            for (int x = 0; x < WIDTH; x++) {

                Coordinate c = new Coordinate(x, y);

                if(!(getField(c) == null) && !getField(c).getContent().equals(UnitType.SPACE))
                    switch (getField(c).getContent()) {

                        case BLUE:
                            board.append("|B|");
                            break;

                        case RED:
                            board.append("|R|");
                            break;

                        case EMPTY:
                            board.append("| |");
                            break;

                        case HOLE:
                            board.append("|O|");
                            break;
                    }

                else
                    board.append("   ");
            }

            board.append("\n");
        }

        return board.toString();
    }

    public void listMoves(){

        moves.forEach(i -> System.out.println(i.toString()));
    }
}