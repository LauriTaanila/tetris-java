import java.awt.*;


public class Shape {

    private int[][] currentrotation;

    private int[][][] rotationarray;
    private int rotation = 0;
    private int pivot_y = 1, pivot_x = 5;
    private Piece[] pieces;
    private int start_y = 1, start_x = 5;
    private Piece[][] board;
    private int shape;
    private Color color;

    public Shape(Piece[][] newboard, int newshape) {
        this.board = newboard;

        this.shape = newshape;
        // All possible rotations for the shapes.
        //(-1,-1),(-1,0),(-1,1)
        //( 0,-1),( 0,0),( 0,1)
        //( 1,-1),( 1,0), ( 1,1)
        switch (shape) {
                // I
            case 1:
                rotationarray = new int[][][]{{{-1, 0}, {0, 0}, {1, 0}, {2, 0}},
                        {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
                        {{-2, 0}, {-1, 0}, {0, 0}, {1, 0}},
                        {{0, -2}, {0, -1}, {0, 0}, {0, 1}}};

                currentrotation = rotationarray[0];
                color = Color.cyan;
                break;
                // T
            case 2:
                rotationarray = new int[][][]{{{-1, 0}, {0, -1}, {0, 0}, {0, 1}},
                        {{-1, 0}, {1, 0}, {0, 0}, {0, 1}},
                        {{1, 0}, {0, -1}, {0, 0}, {0, 1}},
                        {{1, 0}, {-1, 0}, {0, -1}, {0, 0}}};

                currentrotation = rotationarray[0];
                color = Color.magenta;
                break;

                // J
            case 3: rotationarray = new int[][][]{{{-1,1}, {-1,0}, {0,0}, {1,0}},
                    {{1,1}, {0,1}, {0,0}, {0,-1}},
                    {{1,-1}, {-1,0}, {0,0}, {1,0}},
                    {{-1,-1}, {0,-1}, {0,0}, {0,1}}};

                currentrotation = rotationarray[0];
                color = Color.blue;
                break;
                // L
            case 4:

                rotationarray = new int[][][]{{{-1,-1}, {-1,0}, {0,0}, {1,0}},
                        {{-1, 1}, {0,1}, {0,0}, {0,-1}},
                        {{1, 1}, {-1,0}, {0,0}, {1,0}},
                        {{1,-1}, {0,-1}, {0,0}, {0,1}}};

                currentrotation = rotationarray[0];
                color = Color.orange;
                break;
                // square
            case 5:
                rotationarray = new int[][][]{{{0,0}, {0,1}, {-1,0}, {-1,1}},
                        {{0,0}, {0,1}, {-1,0}, {-1,1}},
                        {{0,0}, {0,1}, {-1,0}, {-1,1}},
                        {{0,0}, {0,1}, {-1,0}, {-1,1}}};

                currentrotation = rotationarray[0];
                color = Color.yellow;
                break;

                //s
            case 6:
                rotationarray = new int[][][]{{{0,-1},{0,0},{-1,0},{-1,1}},
                        {{-1,0},{0,0},{0,1},{1,1}},
                        {{0,1},{0,0},{1,0},{1,-1}},
                        {{1,0},{0,0},{0,-1},{-1,-1}}};

                currentrotation = rotationarray[0];
                color = Color.green;
                break;
                // Z
            case 7:
                rotationarray = new int[][][]{{{0,1}, {0,0}, {-1,0}, {-1,-1}},
                        {{1,0}, {0,0}, {0,1}, {-1,1}},
                        {{0,-1}, {0,0}, {1,0}, {1,1}},
                        {{1,-1}, {0,0}, {0,-1}, {-1,0}}};

                currentrotation = rotationarray[0];
                color = Color.red;
                break;
        }


        pieces = new Piece[5];
        for (int i = 0; i < 4; i++) {
            pieces[i] = new Piece(start_y + currentrotation[i][0], start_x + currentrotation[i][1], color);
        }

    }


    //checks collision
    public boolean checkCollision(int y, int x) {
        int temp_y;
        int temp_x;
        boolean partofoldshape;


        for (int i = 0; i < pieces.length - 1; i++) {
            partofoldshape = false;
            temp_y = y + pieces[i].getY();
            temp_x = x + pieces[i].getX();

            if (temp_y >= 20 || temp_x < 0 || temp_x == 10) {
                return true;
            }

            for (int j = 0; j < pieces.length - 1; j++) {
                //this is so the piece does not collide on itself
                if (pieces[j].getY() == temp_y && pieces[j].getX() == temp_x) {

                    partofoldshape = true;
                    break;
                }
            }
            if (board[temp_y][temp_x] != null && !partofoldshape) {
                return true;
            }
        }
        return false;
    }
    //locks the piece onto the board
    public void stopShape() {
        for (int i = 0; i < pieces.length - 1; i++) {
            board[pieces[i].getY()][pieces[i].getX()] = new Piece(pieces[i].getY(), pieces[i].getX(), pieces[i].getColor());
        }

    }


    public void moveDown() {


        for (int i = 0; i < pieces.length - 1; i++) {
            pieces[i].setY(pieces[i].getY() + 1);
        }
        pivot_y++;

    }

    public void moveRight() {

        if (checkCollision(0, 1)) {
            return;
        }
        for (int i = 0; i < pieces.length - 1; i++) {
            pieces[i].setX(pieces[i].getX() + 1);
        }

        pivot_x++;
    }


    public void moveLeft() {

        if (checkCollision(0, -1)) {
            return;
        }

        for (int i = 0; i < pieces.length - 1; i++) {
            pieces[i].setX(pieces[i].getX() - 1);
        }

        pivot_x--;
    }

    public void rotate(int r) {
        int oldr = rotation;
        rotation = rotation + r;

        if(rotation == -1){
            rotation = 3;
        }

        if (rotation == 4){
            rotation = 0;
        }
        //checks if rotation is possible
        currentrotation = rotationarray[rotation];
        for (int i = 0; i < pieces.length - 1; i++) {

            if (pivot_x + currentrotation[i][1] >= 10 || pivot_x + currentrotation[i][1] < 0 || pivot_y + currentrotation[i][0] >= 20 ||
                    board[pivot_y + currentrotation[i][0]][pivot_x + currentrotation[i][1]] != null ){
                rotation = oldr;
                currentrotation = rotationarray[rotation];
                return;
            }
        }
        //rotates the shape around it's pivot point
        for (int i = 0; i < pieces.length - 1; i++) {

            pieces[i].setY(pivot_y + currentrotation[i][0]);
            pieces[i].setX(pivot_x + currentrotation[i][1]);
        }

    }

    public void hardDrop(){
        while (!checkCollision(1,0)){
            moveDown();
        }
    }


    public Piece[][] getBoard() {
        return board;
    }

    public Piece[] getPieces() {
        return pieces;
    }
}
