package Controls;

import Enums.UnitType;
import Exeptions.NoValidMoveException;
import Players.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AI implements Player {

    private int level;
    private Board GameBoard;
    private UnitType team;

    public AI(Board board, UnitType team, int level){

        GameBoard = new Board(board);
        this.team = team;
        this.level = level;
    }

    @Override
    public Move thinkOutMove() throws NoValidMoveException {

        ArrayList<Move> moves = getPossibleMoves();

        calculateMoveValues(moves);

        return getRandomMove(getBestMoves(moves));
    }

    private ArrayList<Move> getPossibleMoves() throws NoValidMoveException{

        ArrayList<Coordinate> sources = getPossibleSources();
        ArrayList<Move> moves = new ArrayList<>();

        for(Coordinate source : sources){

            ArrayList<Coordinate> moveLocations = GameBoard.getFieldCoordinatesInRange(source, 2);
            GameBoard.selectEmptyFields(moveLocations);

            for(Coordinate moveLocation : moveLocations){

                Move move = new Move(source, moveLocation);

                if(move.isValid()){

                    moves.removeIf((m) -> move.sameResult(m));
                    moves.add(move);
                }
            }
        }

        return moves;
    }

    private ArrayList<Move> getBestMoves(ArrayList<Move> moves){

        int bestScore;
        ArrayList<Move> bestMoves = new ArrayList<>();

        if(team.equals(UnitType.RED))
            bestScore = Collections.max(moves, new MoveValueComparator()).value;

        else
            bestScore = Collections.min(moves, new MoveValueComparator()).value;

        for(Move m : moves)
            if(m.value == bestScore)
                bestMoves.add(m);

        return bestMoves;
    }

    private ArrayList<Coordinate> getPossibleSources() throws NoValidMoveException{

        ArrayList<Coordinate> sources = new ArrayList<>();

        for(Coordinate c : GameBoard.usefulCoordinates)
            if(GameBoard.getField(c).getContent().getOpposite().equals(team))
                sources.add(c);

        return sources;
    }

    private void calculateMoveValues(ArrayList<Move> moves) throws NoValidMoveException{

        Board calculatorBoard = new Board(GameBoard);

        for(Move m : moves){

            calculatorBoard.makeMove(m);

            if(level > 1){

                AI lowerLevel = new AI(calculatorBoard, team.getOpposite(), level - 1);
                calculatorBoard.makeMove(lowerLevel.thinkOutMove());
            }

            m.value = calculatorBoard.getValue();
        }
    }

    private Move getRandomMove(ArrayList<Move> moves){

        return moves.get(new Random().nextInt(moves.size()));
    }

    @Override
    public UnitType getTeam() {

        return team;
    }
}
