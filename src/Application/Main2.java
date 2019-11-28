package Application;

import Controls.Board;
import Controls.Move;
import Enums.UnitType;
import Exeptions.NoValidMoveException;
import Graphics.GUIMaker;
import IO.BoardIOHandler;
import Players.AI;
import Players.Human;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main2 extends Application {

    private Board board = BoardIOHandler.load("Save 1");
    private AI red = new AI(UnitType.RED, 1), blue  = new AI(UnitType.BLUE, 1);
    private int gameType = 0;

    private GUIMaker maker = new GUIMaker();
    private Stage primaryStage;
    private Group menu, game, editor;

    @Override
    public void start(Stage stage) throws Exception {

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
        ((Button)menu.getChildren().get(1)).setOnAction(value ->  { show(game); AIVsHuman(); } );
        ((Button)menu.getChildren().get(3)).setOnAction(value ->  show(editor));
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
                    red = new AI(UnitType.RED, fi);

                maker.setButtonBackground(toggleButtons.get(fi), "Active Button");
                setButtonBackgrounds(toggleButtons, 0, 4, fi); });
        }

        for(int i = 4; i < 8; i++){

            final int fi = i;

            toggleButtons.get(i).setOnAction(value -> {
                if(fi == 4)
                    red = null;

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

    private void setupGameButtons(){

        ((Button)game.getChildren().get(1)).setOnAction(value -> show(menu));
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

    private void HumanVsHuman(){


    }

    private void HumanVsAI(){


    }

    private void AIVsHuman(){

        try {
            red.thinkOutMove(board);
        } catch (NoValidMoveException e) {
            e.printStackTrace();
        }
    }

    private void AIvsAI(){

        try {

            while (true) {

                board.makeMove(red.thinkOutMove(board));
                board.makeMove(blue.thinkOutMove(board));
            }
        }

        catch (NoValidMoveException e ) { show(showWinner(board.getWinner())); }
    }

    private Group showWinner(UnitType winner){

        ArrayList<Node> elements = new ArrayList<>();
        elements.add(maker.loadBackGround("Void"));

        Button endButton = null;

        switch (winner){

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
