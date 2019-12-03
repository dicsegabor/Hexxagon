package Controls;

import Enums.MoveType;
import Enums.UnitType;
import Exeptions.NoValidMoveException;
import IO.BoardIOHandler;
import IO.Reader;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {

    private final int WIDTH = 9, HEIGHT = 17;
    public final ArrayList<Coordinate> coordinates = new ArrayList<>();
    private final Field[][] OriginalBoard = new Field[HEIGHT][WIDTH];

    private ArrayList<Move> moves = new ArrayList<>();
    private Field[][] GameBoard = new Field[HEIGHT][WIDTH];
    public final ArrayList<Coordinate> usefulCoordinates = new ArrayList<>();

    public Board(String fileName){

        generateCoordinates();

        Reader r = new Reader(fileName);
        String[] layout = r.getBoardLayout();

        int si = 0;
        for(Coordinate c : coordinates)
            GameBoard[c.y][c.x] = new Field(c, UnitType.parseUnitType(layout[si++]));

        getUsefulCoordinates();
        setOriginalBoard();
    }

    public Board(Board board){

        generateCoordinates();
        setGameBoard(board);
        getUsefulCoordinates();
        setOriginalBoard();
    }

    public static void refreshBoards(){

        Board defaultBoard = new Board("defaultBoard");
        BoardIOHandler.save(defaultBoard, "defaultBoard");
        BoardIOHandler.save(defaultBoard, "defaultTestBoard");
        defaultBoard = new Board("emptyBoard");
        BoardIOHandler.save(defaultBoard, "emptyBoard");
    }

    public void reset(){

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

        ArrayList<Coordinate> adjacentFields = getSpecifiedFieldsInRange(center, MoveType.SHORT, getField(center).getContent().getOpposite());

        for(Coordinate c : adjacentFields)
            getField(c).setContent(getField(center).getContent());
    }

    public ArrayList<Coordinate> getSpecifiedFieldsInRange(Coordinate center, MoveType moveType, UnitType content) {

        ArrayList<Coordinate> fields = new ArrayList<>();

        int range;
        if (moveType.equals(MoveType.SHORT)) range = 1;
        else range = 2;

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
                if(getField(c).getContent().equals(content) && center.getDistance(c).equals(moveType))
                    fields.add(c);

        return fields;
    }

    public Field getField( Coordinate coordinate){

        return GameBoard[coordinate.y][coordinate.x];
    }

    public UnitType getPreviousPlayer() {

        if(moves.isEmpty())
            return UnitType.BLUE;

        return getField(moves.get(moves.size() - 1).to).getContent();
    }

    public Boolean testForEnd() {

        AI tester = new AI(getPreviousPlayer().getOpposite(), this, 1);

        try{ tester.bestMove(); }
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

        for (int i = 0; i < this.coordinates.size(); i++) {

            Field thisField = getField(coordinates.get(i));
            Field bField = ((Board) o).getField(((Board) o).coordinates.get(i));

            if (!thisField.equals(bField))
                return false;
        }

        return true;
    }

    @Override
    public String toString() {

        String board = "";

        for(int y = 0; y < HEIGHT; y++) {

            for (int x = 0; x < WIDTH; x++) {

                Coordinate c = new Coordinate(x, y);

                if(!(getField(c) == null) && !getField(c).getContent().equals(UnitType.SPACE))
                    switch (getField(c).getContent()) {

                        case BLUE:
                            board += "|B|";
                            break;

                        case RED:
                            board += "|R|";
                            break;

                        case EMPTY:
                            board += "| |";
                            break;

                        case HOLE:
                            board += "|O|";
                            break;
                    }

                else
                    board += "   ";
            }

            board += "\n";
        }

        return board;
    }

    public void listMoves(){

        moves.forEach(i -> System.out.println(i.toString()));
    }
}