package Controls;

import Enums.UnitType;
import IO.BoardIOHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTests {

    private Board testBoard, emptyBoard;
    private Move testMove;

    @BeforeEach
    public void setup(){

        testBoard = BoardIOHandler.load("defaultTestBoard");
        emptyBoard = BoardIOHandler.load("emptyBoard");
        testMove = new Move(new Coordinate(4, 16), new Coordinate(4, 14));
    }

    @Test
    public void IOTest(){

        BoardIOHandler.save(testBoard, "testBoard_1");
        Board testedBoard = BoardIOHandler.load("testBoard_1");
        assertEquals(testBoard, testedBoard);
    }

    @Test
    public void copyConstructorTest(){

        Board testedBoard = new Board(testBoard);
        assertEquals(testedBoard, testBoard);
    }

    @Test
    public void getUsefulCoordinatesTest(){

        assertEquals(testBoard.usefulCoordinates, emptyBoard.usefulCoordinates);
    }

    @Test
    public void makeMoveTest(){

        testBoard.makeMove(testMove);
        assertEquals(1, testBoard.getValue());
    }

    @Test
    public void conquerAdjacentFieldsTest(){

        Coordinate adjacentField = new Coordinate(4, 12);
        testBoard.getField(adjacentField).setContent(UnitType.BLUE);
        testBoard.makeMove(testMove);
        assertEquals(2, testBoard.getValue());
    }

    @Test
    public void resetTest(){

        Board testedBoard = new Board(testBoard);
        testedBoard.makeMove(testMove);
        testedBoard.resetBoard();
        assertEquals(testedBoard, testBoard);
    }

    @Test
    public void EndTesterTest() {

        assertTrue(emptyBoard.testForEnd());
        assertFalse(testBoard.testForEnd());
    }

    @Test
    public void getValueTest(){

        assertEquals(0, testBoard.getValue());
        testBoard.makeMove(testMove);
        assertEquals(1, testBoard.getValue());
    }

    @Test
    public void getPreviousPlayerTest(){

        assertEquals(UnitType.BLUE, testBoard.getPreviousPlayer());
    }
}
