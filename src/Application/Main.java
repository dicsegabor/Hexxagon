package Application;

import Controls.Board;
import Controls.Coordinate;
import Controls.Move;
import Enums.MoveType;
import Enums.UnitType;
import Exeptions.NoValidMoveException;
import Graphics.GUIMaker;
import IO.BoardIOHandler;
import Controls.AI;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    private Board board = BoardIOHandler.load("Save 1");
    private AI red = null, blue = null;
    private ArrayList<Button> currentSelection = new ArrayList<>();
    private ArrayList<Button> currentUnits = new ArrayList<>();
    private Coordinate from = null, to = null;
    private UnitType placing = UnitType.HOLE;

    private GUIMaker maker = new GUIMaker();
    private Stage primaryStage;
    private Group menu, game, editor;

    @Override
    public void start(Stage stage){

        this.primaryStage = stage;
        setupPrimaryStage();

        menu = maker.menu();
        setupMenuButtons();

        game = maker.game();
        setupGameButtons();

        editor = maker.editor();
        setupEditorButtons();

        primaryStage.setScene(maker.makeScene(menu));
        primaryStage.show();
    }

    private void setupPrimaryStage(){

        primaryStage.setFullScreen(true);
        maker.setExitKey(primaryStage, KeyCode.ESCAPE);
        primaryStage.setFullScreenExitHint("");
    }

    private void show(Group root){

        primaryStage.getScene().setRoot(root);
    }

    private void setupMenuButtons(){

        //setup main buttons
        ((Button)menu.getChildren().get(1)).setOnAction(value ->  { startGame(); } );
        ((Button)menu.getChildren().get(3)).setOnAction(value ->  { show(editor); showUnitsForEditor(); });
        ((Button)menu.getChildren().get(4)).setOnAction(value -> primaryStage.close());

        ArrayList<Button> toggleButtons = new ArrayList<>();
        for(int i = 5; i < 13; i++)
            toggleButtons.add((Button)menu.getChildren().get(i));

        setupMenuPlayerButtons(toggleButtons);
    }

    private void setupMenuPlayerButtons(ArrayList<Button> toggleButtons){

        for(int i = 0; i < 4; i++){

            final int fi = i;

            toggleButtons.get(i).setOnAction(value -> {

                if(fi == 0)
                    red = null;

                else
                    red = new AI(UnitType.RED, board, fi);

                maker.setButtonBackground(toggleButtons.get(fi), "Active Button");
                setButtonBackgrounds(toggleButtons, 0, 4, fi); });
        }

        for(int i = 4; i < 8; i++){

            final int fi = i;

            toggleButtons.get(i).setOnAction(value -> {

                if(fi == 4)
                    blue = null;

                else
                    blue = new AI(UnitType.BLUE, board, fi - 4);

                maker.setButtonBackground(toggleButtons.get(fi), "Active Button");
                setButtonBackgrounds(toggleButtons, 4, 8, fi); });
        }
    }

    private void setButtonBackgrounds(ArrayList<Button> buttons, int from, int to, int without){

        for(int i = from; i < to; i++)
            if(i != without)
                maker.setButtonBackground(buttons.get(i), "Inactive Button");
    }

    private void setupGameButtons(){

        ((Button)game.getChildren().get(1)).setOnAction(value -> { show(menu); board.reset(); });
    }

    private void setupEditorButtons(){

        ((Button)editor.getChildren().get(1)).setOnAction(value -> show(menu));

        ArrayList<Button> toggleButtons = new ArrayList<>();
        for(int i = 4; i < 7; i++)
            toggleButtons.add((Button)editor.getChildren().get(i));

        setupPlaceButtons(toggleButtons);
    }

    private void setupPlaceButtons(ArrayList<Button> toggleButtons){

        for(int i = 0; i < 3; i++){

            final int fi = i;

            toggleButtons.get(i).setOnAction(value -> {

                maker.setButtonBackground(toggleButtons.get(fi), "Active Button");
                setButtonBackgrounds(toggleButtons, 0, 3, fi); });
        }
    }

    private void startGame(){

        if(red == null || blue == null){

            show(game);
            showUnitsForGame();
        }

        if(red != null && blue != null)
            AIvsAI();

        else if(red != null)
            nextPlayer();
    }

    private void nextPlayer(){

        try {

            if (red != null)
                board.makeMove(red.bestMove());

            else if (blue != null)
                board.makeMove(blue.bestMove());
        }

        catch (NoValidMoveException e) { show(endGame()); }

        if(board.testForEnd())
            show(endGame());

        showUnitsForGame();
    }

    private void AIvsAI(){

        try {

            while (true) {

                board.makeMove(red.bestMove());
                board.makeMove(blue.bestMove());
            }
        }

        catch (NoValidMoveException e ) { show(endGame()); }
    }

    private void showUnitsForEditor(){

        editor.getChildren().removeAll(currentUnits);

        ArrayList<Button> units = new ArrayList<>();

        for(Coordinate c : board.coordinates){

            switch (board.getField(c).getContent()){

                case RED:
                    Button btr = maker.makeButton("Red", Coordinate.coordinateToPoint(c));
                    if(board.getPreviousPlayer().equals(UnitType.BLUE))
                        btr.setOnAction(value -> { from = c; displayPossibilities(c); });
                    units.add(btr);
                    break;

                case BLUE:
                    Button btb = maker.makeButton("Blue", Coordinate.coordinateToPoint(c));
                    if(board.getPreviousPlayer().equals(UnitType.RED))
                        btb.setOnAction(value -> { from = c; displayPossibilities(c); });
                    units.add(btb);
                    break;

                case HOLE:
                    units.add(maker.makeButton("Hole", Coordinate.coordinateToPoint(c)));
                    break;
            }
        }

        currentUnits.addAll(units);

        editor.getChildren().addAll(currentUnits);
    }

    private void showUnitsForGame() {

        game.getChildren().removeAll(currentUnits);
        game.getChildren().removeAll(currentSelection);
        currentUnits.clear();

        ArrayList<Button> units = new ArrayList<>();

        for(Coordinate c : board.coordinates){

            switch (board.getField(c).getContent()){

                case RED:
                    Button btr = maker.makeButton("Red", Coordinate.coordinateToPoint(c));
                    if(board.getPreviousPlayer().equals(UnitType.BLUE))
                        btr.setOnAction(value -> { from = c; displayPossibilities(c); });
                    units.add(btr);
                    break;

                case BLUE:
                    Button btb = maker.makeButton("Blue", Coordinate.coordinateToPoint(c));
                    if(board.getPreviousPlayer().equals(UnitType.RED))
                        btb.setOnAction(value -> { from = c; displayPossibilities(c); });
                    units.add(btb);
                    break;

            case HOLE:
                    units.add(maker.makeButton("Hole", Coordinate.coordinateToPoint(c)));
                    break;
            }
        }

        currentUnits.addAll(units);

        game.getChildren().addAll(currentUnits);
    }

    private void displayPossibilities(Coordinate center) {

       game.getChildren().removeAll(currentSelection);
       currentSelection.clear();

        ArrayList<Button> buttons = new ArrayList<>();

        ArrayList<Coordinate> shortMoves = new ArrayList<>(board.getSpecifiedFieldsInRange(center, MoveType.SHORT, UnitType.EMPTY));

        for(Coordinate c : shortMoves){

            Button bt = maker.makeButton("Short Move", Coordinate.coordinateToPoint(c));
            bt.setOnAction(value -> { to = c; makeMove(new Move(from, to)); nextPlayer(); });
            buttons.add(bt);
        }

        ArrayList<Coordinate> longMoves = new ArrayList<>(board.getSpecifiedFieldsInRange(center, MoveType.LONG, UnitType.EMPTY));

        for(Coordinate c : longMoves){

            Button bt = maker.makeButton("Long Move", Coordinate.coordinateToPoint(c));
            bt.setOnAction(value -> { to = c; makeMove(new Move(from, to)); nextPlayer(); } );
            buttons.add(bt);
        }

        Button bt = maker.makeButton("Selected", Coordinate.coordinateToPoint(center));
        buttons.add(bt);

        currentSelection.addAll(buttons);

        game.getChildren().addAll(currentSelection);
    }

    private void makeMove(Move move){

        board.makeMove(move);
        game.getChildren().removeAll(currentSelection);
        showUnitsForGame();
    }

    private Group endGame(){

        ArrayList<Node> elements = new ArrayList<>();
        elements.add(maker.loadBackGround("Void"));

        Button endButton = null;

        switch (board.getWinner()){

            case RED:
                endButton = maker.makeButton("Red Won", new Point2D(533, 323));
                break;

            case BLUE:
                endButton = maker.makeButton("Blue Won", new Point2D(533, 323));
                break;

            case EMPTY:
                endButton = maker.makeButton("Tie", new Point2D(533, 323));
                break;
        }

        endButton.setOnAction(value -> { show(menu); board.reset(); } );
        elements.add(endButton);

        return new Group(elements);
    }
}
