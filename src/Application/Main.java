package Application;

import Controls.Board;
import Controls.Converter;
import Controls.Coordinate;
import Controls.Move;
import Enums.MoveType;
import Enums.UnitType;
import Game.Game;
import Graphics.*;
import IO.BoardIOHandler;
import Players.AI;
import Players.Human;
import Players.Player;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    private GUIMaker maker = new GUIMaker();
    private Stage primaryStage;
    private Board board = BoardIOHandler.load("Save 1");
    private Player red = new Human(UnitType.RED, this), blue  = new Human(UnitType.BLUE, this);
    private ArrayList<Button> currentSelection = new ArrayList<>();
    private Group currentState;
    private Coordinate from = null, to = null;
    private UnitType currentlyPlacing = UnitType.HOLE;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;

        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setResizable(false);
        maker.setExitKey(primaryStage, KeyCode.ESCAPE);

        menu();

        primaryStage.setScene(maker.makeScene(currentState));
        primaryStage.show();
    }

    private void refresh(){

        primaryStage.getScene().setRoot(currentState);
    }

    private void menu(){

        Group root = maker.menu();

        EventHandler configureBoardButton = value -> { editor(); refresh(); };
        EventHandler quitToDosButton = value -> primaryStage.close();

        ((Button)root.getChildren().get(3)).setOnAction(configureBoardButton);
        ((Button)root.getChildren().get(4)).setOnAction(quitToDosButton);

        ArrayList<Button> buttons = new ArrayList<>();
        for(int i = 5; i < 13; i++)
            buttons.add((Button)root.getChildren().get(i));

        setupMenuPlayerButtons(buttons);

        currentState = root;
    }

    private void setupMenuPlayerButtons(ArrayList<Button> toggleButtons){

        for(int i = 0; i < 4; i++){

            final int fi = i;

            toggleButtons.get(i).setOnAction(value -> {
                if(fi == 0)
                    red = new Human(UnitType.RED, this);

                else
                    red = new AI(UnitType.RED, fi);

                maker.setButtonBackground(toggleButtons.get(fi), "Active Button");
                setButtonBackgrounds(toggleButtons, 0, 4, fi); });
        }

        for(int i = 4; i < 8; i++){

            final int fi = i;

            toggleButtons.get(i).setOnAction(value -> {
                if(fi == 4)
                    red = new Human(UnitType.BLUE, this);

                else
                    red = new AI(UnitType.BLUE, fi);

                maker.setButtonBackground(toggleButtons.get(fi), "Active Button");
                setButtonBackgrounds(toggleButtons, 4, 8, fi); });
        }
    }

    private void setButtonBackgrounds(ArrayList<Button> buttons, int from, int to, int without){

        for(int i = from; i < to; i++)
            if(i != without)
                maker.setButtonBackground(buttons.get(i), "Inactive Button");
    }

    private void editor(){

        Group root = maker.editor();

        EventHandler menuButton = value -> { menu(); refresh(); };

        ((Button)root.getChildren().get(1)).setOnAction(menuButton);

        ArrayList<Button> toggleButtons = new ArrayList<>();
        for(int i = 4; i < 7; i++)
            toggleButtons.add((Button)root.getChildren().get(i));

        setupEditorButtons(toggleButtons);

        currentState = root;
    }

    private void setupEditorButtons(ArrayList<Button> toggleButtons){

        for(int i = 0; i < 3; i++){

            final int fi = i;

            toggleButtons.get(i).setOnAction(value -> {

                if(fi == 0)
                    currentlyPlacing = UnitType.HOLE;

                else if(fi == 1)
                    currentlyPlacing = UnitType.RED;

                else
                    currentlyPlacing = UnitType.BLUE;

                maker.setButtonBackground(toggleButtons.get(fi), "Active Button");
                setButtonBackgrounds(toggleButtons, 0, 4, fi); });
        }
    }

    private Button[][] getBoardContent() {

        Button[][] units = new Button[17][9];

        for(Coordinate c : board.coordinates){

            if(board.getField(c).getContent().hasGraphic()) {

                Button unit;

                switch (board.getField(c).getContent()) {

                    case RED:
                        unit = maker.makeButton("Red", Converter.coordinateToPoint(c));
                        if(board.getPreviousPlayer().equals(UnitType.BLUE))
                            unit.setOnAction(value -> { displayPossibilities(c); from = c; });
                        units[c.y][c.x] = unit;
                        break;

                    case BLUE:
                        unit = maker.makeButton("Blue", Converter.coordinateToPoint(c));
                        if(board.getPreviousPlayer().equals(UnitType.RED))
                            unit.setOnAction(value -> { displayPossibilities(c); from = c; });
                        units[c.y][c.x] = unit;
                        break;

                    case HOLE:
                        units[c.y][c.x] = maker.makeButton("Hole", Converter.coordinateToPointForField(c));
                        break;

                    default:
                        break;
                }
            }

        }

        return units;
    }

    private void displayPossibilities(Coordinate center) {

        currentState.getChildren().removeAll(currentSelection);
        currentSelection.clear();

        ArrayList<Button> buttons = new ArrayList<>();

        ArrayList<Coordinate> shortMoves = new ArrayList<>(board.getSpecifiedFieldsInRange(center, MoveType.SHORT, UnitType.EMPTY));

        for(Coordinate c : shortMoves){

            Button bt = maker.makeButton("Short Move", Converter.coordinateToPointForField(c));
            bt.setOnAction(value -> to = c );
            buttons.add(bt);
        }

        ArrayList<Coordinate> longMoves = new ArrayList<>(board.getSpecifiedFieldsInRange(center, MoveType.LONG, UnitType.EMPTY));

        for(Coordinate c : longMoves){

            Button bt = maker.makeButton("Long Move", Converter.coordinateToPointForField(c));
            bt.setOnAction(value -> to = c );
            buttons.add(bt);
        }

        Button bt = maker.makeButton("Selected", Converter.coordinateToPointForField(center));
        buttons.add(bt);

        currentSelection.addAll(buttons);

        currentState.getChildren().addAll(currentSelection);
    }

    public Move waitForPlayerInteraction(){

        while(from.equals(null) && to.equals(null));

        Move move = new Move(from, to);

        from = null;
        to = null;

        return move;
    }

    public void makeMove(Move move){

        board.makeMove(move);
        currentState.getChildren().removeAll(currentSelection);
        refresh();
    }
}
