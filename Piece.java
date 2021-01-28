import java.awt.*;

public class Piece {

    private int y;
    private int x;
    private Color color;

    public Piece(int y, int x, Color color) {
        this.y = y;
        this.x = x;
        this.color = color;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Color getColor() {
        return color;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

}
