package Tests;

import Controls.*;
import Enums.UnitType;
import Exeptions.NoValidMoveException;
import Game.*;
import Players.*;
import Players.AI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Tests {

    private Board defaultTestBoard = new Board("defaultTestBoard");
    private Board emptyBoard = new Board("emptyBoard");

    @Test
    public void AIMoveTest() throws NoValidMoveException{

        Board testBoard = new Board(defaultTestBoard);
        AI redPlayer = new AI(UnitType.RED, testBoard, 1);
        testBoard.makeMove(redPlayer.thinkOutMove());

        assertEquals(1, testBoard.getValue());
        testBoard.resetBoard();

        AI bluePlayer = new AI(UnitType.BLUE, testBoard, 1);
        testBoard.makeMove(bluePlayer.thinkOutMove());

        assertEquals(-1, testBoard.getValue());
    }

    @Test
    public void AICalculatorTest() throws NoValidMoveException {

        Board redTestBoard = new Board(defaultTestBoard);

        AI redPlayer = new AI(UnitType.RED, redTestBoard, 1);

        for(int i = 0; i < 1000; i++) {

            redTestBoard.makeMove(redPlayer.thinkOutMove());
            assertEquals(1, redTestBoard.getValue());
            redTestBoard.resetBoard();
        }

        Board blueTestBoard = new Board(defaultTestBoard);

        AI bluePlayer = new AI(UnitType.BLUE, blueTestBoard, 1);

        for(int i = 0; i < 1000; i++) {

            blueTestBoard.makeMove(bluePlayer.thinkOutMove());
            assertEquals(-1, blueTestBoard.getValue());
            blueTestBoard.resetBoard();
        }
    }

    @Test
    public void AIDifficultyTest(){

        Game redWins = new Game(defaultTestBoard);
        redWins.addAI(UnitType.RED, 2);
        redWins.addAI(UnitType.BLUE, 1);

        Game blueWins = new Game(defaultTestBoard);
        blueWins.addAI(UnitType.RED, 1);
        blueWins.addAI(UnitType.BLUE, 2);
    }
}
