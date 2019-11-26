package Players;

import Controls.Board;
import Controls.Coordinate;
import Controls.Move;
import Controls.MoveValueComparator;
import Enums.UnitType;
import Exeptions.NoValidMoveException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AI implements Player{

    private final UnitType team;
    private Board calculatorBoard;
    private final int deepness;

    public AI(UnitType team, Board GameBoard, int deepness){

        this.team = team;
        this.calculatorBoard = GameBoard;
        this.deepness = deepness;
    }

    public Move thinkOutMove() throws NoValidMoveException {

        ArrayList<Move> moves;

        try{ moves = getPossibleMoves(); }
        catch (NoValidMoveException e) { throw new NoValidMoveException("");}

        calculateMoveValues(moves);

        return getRandomMove(getBestMoves(moves));
    }

    public ArrayList<Move> getPossibleMoves() throws NoValidMoveException {

        ArrayList<Coordinate> sources = getPossibleSources();
        ArrayList<Move> moves = new ArrayList<>();

        if(sources.isEmpty())
            throw new NoValidMoveException("Nincs lehetséges kiindulási pont!");

        for(Coordinate source : sources) {

            ArrayList<Coordinate> possibleFields = calculatorBoard.getFieldCoordinatesInRange(source, 2);
            calculatorBoard.selectEmptyFields(possibleFields);

            for (Coordinate target : possibleFields) {

                    Move move = new Move(source, target);

                    if (move.isValid()) {

                        moves.removeIf((m) -> m.sameResult(move));
                        moves.add(move);
                    }
            }
        }

        if(moves.isEmpty())
            throw new NoValidMoveException("");

        return moves;
    }

    private ArrayList<Coordinate> getPossibleSources() {

        ArrayList<Coordinate> sources = new ArrayList<>();

        for(Coordinate c : calculatorBoard.usefulCoordinates)
            if (calculatorBoard.getField(c).getContent().equals(team))
                sources.add(calculatorBoard.getField(c).getPosition());

        return sources;
    }

    private Move getRandomMove(@NotNull ArrayList<Move> moves){

        Random rand = new Random();
        int chosen = Math.abs(rand.nextInt()) % moves.size();
        return moves.get(chosen);
    }

    private ArrayList<Move> getBestMoves(@NotNull ArrayList<Move> moves){

        int bestScore;

        if(team.equals(UnitType.RED))
            bestScore = Collections.max(moves, new MoveValueComparator()).value;

        else
            bestScore = Collections.min(moves, new MoveValueComparator()).value;

        ArrayList<Move> bestMoves = new ArrayList<Move>();

        for(Move move : moves)
            if(move.value == bestScore)
                bestMoves.add(move);

        return bestMoves;
    }

    private void calculateMoveValues(@NotNull ArrayList<Move> moves){

        Board temp = new Board(calculatorBoard);

        for(Move move : moves) {

            temp.makeMove(move);

            if(deepness > 1){

                AI nextLevel = new AI(team.getOpposite(), temp, deepness - 1);
                try { temp.makeMove(nextLevel.thinkOutMove()); } catch (NoValidMoveException e) { break; }
            }

            move.value = temp.getValue();
            temp.resetBoard();
        }
    }

    public UnitType getTeam(){

        return team;
    }
}