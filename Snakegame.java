import java.awt.Color;

import javax.swing.*;

public class Snakegame extends JFrame{
    JFrame js;
    Snakegame(){
        js = new JFrame("Snake Game");
        js.setBounds(10,10,905,700);
        js.setResizable(false);
        js.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Gamepanel p = new Gamepanel();
        p.setBackground(Color.darkGray);
        js.add(p);



        js.setVisible(true);
    }

    public static void main(String[] args) {
        Snakegame sg = new Snakegame();
    }
    
}