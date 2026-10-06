import java.awt.*;
import java.awt.event.*;
public class SnackGame extends Frame{
    SnackGame(){
        setTitle("Snack Game");
        setSize(600,600);

        add(new GamePanel());
        setVisible(true);

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    }
    public static void main(String[] args){
        new SnackGame();
    }
}