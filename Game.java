import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

class GamePanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        Game.game.repaint(g2);
    }
}

public class Game implements ActionListener, KeyListener {

    public static Game game;
    public final int WIDTH = 400, HEIGHT = 700;
    public GamePanel panel;
    public JLabel scorelabel;

    public Board board;

    public int blocksize = 30;
    public int score = 0;

    public Shape shape;
    public Piece[] currentshape;
    public Piece[][] currentboard;

    public boolean lastmove = false, gameover = false;


    public Game(){

        Timer t = new Timer(200,this);

        JFrame frame = new JFrame();
        scorelabel = new JLabel();
        panel = new GamePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH,HEIGHT);
        frame.setTitle("Tetris");
        frame.setResizable(false);
        frame.setVisible(true);
        frame.addKeyListener(this);
        frame.add(panel);
        panel.add(scorelabel);

        panel.setBackground(Color.gray);
        scorelabel.setFont(new Font("Serif", Font.BOLD, 30));
        scorelabel.setText(Integer.toString(score));

        t.start();

        currentshape = new Piece[1];
        board = new Board(WIDTH-100, HEIGHT - 100);

        addNewPiece();


    }

    public void repaint(Graphics2D g){

        g.setColor(Color.black);
        g.setStroke(new BasicStroke(3));
        g.drawRect(50,50, board.getBOARD_WIDTH(),board.getBOARD_HEIGHT());
        g.setStroke(new BasicStroke(1));

        currentboard = board.getBoardarray();

        if(currentshape != null){
            for(int i = 0; i < currentshape.length -1; i++){
                g.setColor(currentshape[i].getColor());
                g.fillRect(currentshape[i].getX()*blocksize+50,currentshape[i].getY()*blocksize + 50,blocksize,blocksize);
            }
        }


        for (int r = 0; r < 20; r++) {

            for (int c = 0; c < 10; c++) {
                if (currentboard[r][c] != null){
                    g.setColor(currentboard[r][c].getColor());

                    g.fillRect(currentboard[r][c].getX()*blocksize+50,currentboard[r][c].getY()*blocksize + 50,blocksize,blocksize);
                }

            }

        }



    }




    public void addNewPiece(){
        //linechecking
        score = score + board.checkLine();
        scorelabel.setText(Integer.toString(score));

        Random random = new Random();
        int shaperandomizer = 1+ random.nextInt(7);
        //add new shape
        shape = new Shape(board.getBoardarray(),shaperandomizer);
        currentshape = shape.getPieces();
        panel.repaint();

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        currentboard = board.getBoardarray();
        currentshape = shape.getPieces();

        //check gameover
        if(currentboard[1][5] != null){
            gameover = true;
            scorelabel.setText("Score: " + score + " Press r to reset");
        }

        //lastmove gives the player some extra time to move the block
        if (!shape.checkCollision(1,0)){
            lastmove = false;
        }

        if (shape.checkCollision(1,0) && lastmove && !gameover){
            shape.stopShape();
            addNewPiece();
        }

        if (shape.checkCollision(1, 0)) {
            lastmove = true;

        } else {

            shape.moveDown();

        }

        panel.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {


        int key = e.getKeyCode();
        currentshape = shape.getPieces();


        //resets the game
        if(key == KeyEvent.VK_R){
            board = new Board(WIDTH-100, HEIGHT - 100);
            addNewPiece();
            score = 0;
            scorelabel.setText(Integer.toString(score));
            gameover = false;
        }

        if (gameover){
            return;
        }

        if(key == KeyEvent.VK_RIGHT){

                shape.moveRight();

        }
        if(key == KeyEvent.VK_LEFT){

                shape.moveLeft();

        }

        if(key == KeyEvent.VK_UP){

                shape.hardDrop();

        }

        if(key == KeyEvent.VK_Z ){


                shape.rotate(-1);

        }

        if(key == KeyEvent.VK_X ){


            shape.rotate(1);

        }

        if(key == KeyEvent.VK_DOWN){

            if(!shape.checkCollision(1,0)){
                shape.moveDown();
            }

        }

        panel.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {
        game = new Game();
    }
}
