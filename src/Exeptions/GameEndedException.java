package Exeptions;

public class GameEndedException extends Exception {

    public GameEndedException(String errorMessage) {

        super(errorMessage);
    }
}