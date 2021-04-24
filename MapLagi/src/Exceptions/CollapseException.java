package Exceptions;
import Game.Position;

public class CollapseException extends Exception {
    private Position position;
    private String errorMessage;

    public CollapseException(int x, int y) {
        super();
        this.position = new Position(x, y);
        this.errorMessage = "Terjadi kecelakaan pada posisi " + "(" + this.position.getX() + ", " + this.position.getY()
                + ")";
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
