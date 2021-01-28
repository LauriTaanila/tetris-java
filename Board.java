import java.awt.*;
import java.lang.reflect.Array;

public class Board {

    private int BOARD_WIDTH = 500;
    private int BOARD_HEIGHT = 500;

    private Piece[][] boardarray;


    public Board(int BOARD_WIDTH, int BOARD_HEIGHT) {
        this.BOARD_WIDTH = BOARD_WIDTH;
        this.BOARD_HEIGHT = BOARD_HEIGHT;
        boardarray = new Piece[22][11];

    }

    public int getBOARD_WIDTH() {
        return BOARD_WIDTH;
    }

    public int getBOARD_HEIGHT() {
        return BOARD_HEIGHT;
    }

    public Piece[][] getBoardarray() {
        return boardarray;
    }

    public void setBoardarray(Piece[][] boardarray) {
        this.boardarray = boardarray;
    }

    public int checkLine() {
        int lines = 0;
        for (int r = 0; r <21; r++){
            int count = 0;
            for (int c = 0; c < 10; c++){
                if (boardarray[r][c] != null) {
                    count++;

                }
                    if(count == 10){
                        for(int i = r; i > 0;i--){
                            for(int j = 0; j < 10; j++){
                                boardarray[i][j] = null;
                                if(boardarray[i-1][j] != null ){

                                    boardarray[i-1][j].setY(boardarray[i-1][j].getY()+1);
                                    boardarray[i][j] = boardarray[i-1][j];
                                    boardarray[i-1][j] = null;

                                }
                            }
                        }
                        lines++;
                    }
                }
            }
        switch (lines){
            case 1:
                    return 40;

            case 2:
                    return 100;

            case 3:
                    return 300;


            case 4:
                    return 1200;

        }

        return 0;
    }




    }


