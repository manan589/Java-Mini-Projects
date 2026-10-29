import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends Frame{

    TicTacToe(){
        setSize(700,700);
        setTitle("Tic-Tac-Toe Game");
        setLayout(new BorderLayout());

        setBackground(new Color(15,23,42));

        add(new TicTacGamePanel(),BorderLayout.CENTER);

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                dispose();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args){
        new TicTacToe();
    }
}