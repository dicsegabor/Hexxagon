package Controls;

import Enums.UnitType;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BoardTests {

    private Board testBoard, emptyBoard;
    private Move testMove;

    @Before
    public void setup(){

        testBoard = new Board("defaultTestBoard");
        emptyBoard = new Board("emptyBoard");
        testMove = new Move(new Coordinate(4, 16), new Coordinate(4, 14));
    }

    @Test
    public void IOTest(){

        testBoard.saveBoard("testBoard_1");
        Board testedBoard = new Board("testBoard_1");
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
