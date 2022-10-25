import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;



public class Gamepanel extends JPanel implements ActionListener, KeyListener{
    //for body
    private int[]snakexelength = new int[750];
    private int[]snakeyelength = new int[750];
    private int sankelength = 3;
    private int moves = 0;
    private int score = 0;
    private boolean gameOver = false;

    //for food of snake
    private int[] xpos = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    private int[] ypos = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};

    private Random random = new Random();
    private int foodx, foody;

    // move sanke
    private Timer time;
    private int delay = 100;

    //for mouth
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    
    //For Snake Image
    private ImageIcon sanketitle = new ImageIcon("snaketitle.jpg");
    private ImageIcon sankeleft = new ImageIcon("leftmouth.png");
    private ImageIcon sankeright = new ImageIcon("rightmouth.png");
    private ImageIcon sankeup = new ImageIcon("upmouth.png");
    private ImageIcon sankedown = new ImageIcon("downmouth.png");
    private ImageIcon snakebody = new ImageIcon("snakeimage.png");
    private ImageIcon food = new ImageIcon("enemy.png");



        
    Gamepanel(){
        //To Move snake on Keys
        addKeyListener(this);
        setFocusable(true);//to make key workable on jpanel
        setFocusTraversalKeysEnabled(true);
       
        //To Move snake on desired Speed on Screen
        time = new Timer(delay, this);
        time.start();

        //for food

        newfood();
        
    }

    //To game over if collides with body
    private void collideWithBody() {
        for(int i = sankelength-1; i>0; i--){
            if(snakexelength[i]==snakexelength[0] && snakeyelength[i]==snakeyelength[0]){
                time.stop();
                gameOver = true;
            }
        }
    }

    //To place food for snake Ramdomly on screen
    private void newfood() {
        foodx = xpos[random.nextInt(34)];
        foody = ypos[random.nextInt(23)];

        for(int i=sankelength-1; i>0; i--){
            if(snakexelength[i]==foodx && snakeyelength[i]==foody){
                newfood();
            }
        }
    }
    @Override
    public void paint(Graphics g) {
        
        super.paint(g);
//print rectangles
        g.setColor(Color.white);
        g.drawRect(24, 10, 851, 55);
        g.drawRect(24, 74, 851, 576);
//print title image
       sanketitle.paintIcon(this, g, 25, 11);
//set inital moves
       if(moves==0){
        snakexelength[0] = 100;
        snakexelength[1] = 75;
        snakexelength[2] = 50;

        snakeyelength[0] = 100;
        snakeyelength[1] = 100;
        snakeyelength[2] = 100;

        
       }
//set inital mouth and body position of snake
       if(left){
        sankeleft.paintIcon(this, g, snakexelength[0], snakeyelength[0]);
       }
       if(right){
        sankeright.paintIcon(this, g, snakexelength[0], snakeyelength[0]);
       }
       if(up){
        sankeup.paintIcon(this, g, snakexelength[0], snakeyelength[0]);
       }
       if(down){
        sankedown.paintIcon(this, g, snakexelength[0], snakeyelength[0]);
       }

       for (int i = 1; i < sankelength; i++) {
        snakebody.paintIcon(this, g, snakexelength[i], snakeyelength[i]);
       }

       //to print food for snake randomy at screen
       food.paintIcon(this, g, foodx, foody);

//To print game over on screen
if(gameOver){
    g.setColor(Color.white);
    g.setFont(new Font("Arial", Font.BOLD, 50));
    g.drawString("Game Over", 300, 300);

    g.setFont(new Font("Arial", Font.PLAIN, 20));
    g.drawString("Press Space to Restart", 320, 350);
}
//to print score and length of snake
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.BOLD, 14));
    g.drawString("Score : "+score, 750, 30);
    g.drawString("Snake Length"+sankelength, 750, 50);

//while moving delete earlier portion
       g.dispose();
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        //move snake

        for(int i=sankelength-1; i>0; i--){
            snakexelength[i] = snakexelength[i-1];
            snakeyelength[i] = snakeyelength[i-1];
        }
        
        if(left){
            snakexelength[0] = snakexelength[0]-25;
        }
        if(right){
            snakexelength[0] = snakexelength[0]+25;
        }
        if(up){
            snakeyelength[0] = snakeyelength[0]-25;
        }
        if(down){
            snakeyelength[0] = snakeyelength[0]+25;
        }

        //Make snake come through borders

        if(snakexelength[0]>850)snakexelength[0]=25;
        if(snakexelength[0]<25)snakexelength[0]=850;
        if(snakeyelength[0]>625)snakeyelength[0]=75;
        if(snakeyelength[0]<75)snakeyelength[0]=625;



        //to check collision bettwen snake and food
        collisionWithHead();
        collideWithBody();

        

        //To Repeat The procedures
        repaint();
       
    }
   //When snake eat food
    private void collisionWithHead() {
        if(snakexelength[0]==foodx && snakeyelength[0]==foody){
            newfood();
            sankelength++;
            score++;
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        //keypressing action to snake
        if(e.getKeyCode()==KeyEvent.VK_LEFT &&(!right)){
            left = true;
            right=false;
            up=false;
            down=false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT &&(!left)){
            left = false;
            right=true;
            up=false;
            down=false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP && (!down)){
            left = false;
            right=false;
            up=true;
            down=false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN && (!up)){
            left = false;
            right=false;
            up=false;
            down=true;
            moves++;
        }
        //to restart game after game over
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            restart();
        }
        
    }
    //After snake collides with body we need to restart game
    private void restart() {
        gameOver = false;
        moves = 0;
        score = 0;
        sankelength = 3;
        left = false;
        right = true;
        up = false;
        down = false;
        time.start();
        repaint();
        
    }


    @Override
    public void keyReleased(KeyEvent e) {
        
        
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }
    
}
