package Game;

import java.io.Serializable;

public class Position implements Serializable {
    private int x;
    private int y;

    //    Constructor
    public Position() {
        this.x = -1;
        this.y = -1;
    }

    public Position(int _x, int _y) {
        this.x = _x;
        this.y = _y;
    }

    //    Setter Getter
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //Setter
    public void setX(int _x) {
        x = _x;
    }

    public void setY(int _y) {
        y = _y;
    }



    //    DEBUG
    void print() {
        System.out.println("X: " + x);
        System.out.println("Y: " + y);
    }
}
