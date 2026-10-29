import java.awt.*;
import java.awt.event.*;

public class TicTacGamePanel extends Panel implements ActionListener{
    Button[] buttons = new Button[9];
    Button restartButton;
    char currentPlayer = 'X';
    boolean gameOver = false;
    Label status;
    Label title;

    Color backgroundColor = new Color(15, 23, 42);
    Color cellColor = new Color(30, 41, 59);
    Color xColor = new Color(56, 189, 248);
    Color oColor = new Color(251, 113, 133);
    Color textColor = new Color(226, 232, 240);

    TicTacGamePanel(){
        setLayout(new BorderLayout(5,5));
        setBackground(backgroundColor);

        //Game Title
        title = new Label("TIC - TAC - TOE",Label.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBackground(xColor);

        //Player status
        status = new Label("Player X's turn",Label.CENTER);
        status.setFont(new Font("Arial", Font.BOLD, 20));
        status.setBackground(textColor);

        //Top Panel
        Panel topPanel = new Panel(new GridLayout(2,1,5,5));
        topPanel.setBackground(backgroundColor);
        topPanel.add(title);
        topPanel.add(status);

        //Game grid
        Panel grid = new Panel(new GridLayout(3,3,5,5));
        grid.setBackground(backgroundColor);

        for(int i=0;i<9;i++){
            buttons[i] = new Button("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));
            buttons[i].setBackground(cellColor);
            buttons[i].setForeground(textColor);
            buttons[i].addActionListener(this);
            grid.add(buttons[i]);
        }

        restartButton = new Button("Restart Game");
        restartButton.setFont(new Font("Arial", Font.BOLD, 16));
        restartButton.setBackground(xColor);
        restartButton.setForeground(Color.black);
        restartButton.addActionListener(this);

        Panel bottomPanel = new Panel(new FlowLayout());
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.add(restartButton);

        //Main layout
        add(status, BorderLayout.NORTH);
        add(grid, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e){
        // Check Restart was Clicked
        if(e.getSource() == restartButton){
            resetGame();
            return;
        }

        // Game ends 
        if(gameOver){
            return;
        }

        Button clicked = (Button) e.getSource();

        //Do not allow overwriting an existing symbol
        if(!clicked.getLabel().equals("")){
            return;
        }

        clicked.setLabel(String.valueOf(currentPlayer));

        if(currentPlayer == 'X'){
            clicked.setForeground(xColor);
        }
        else{
            clicked.setForeground(oColor);
        }

        //Check Winner
        if(checkWinner()){
            status.setText("Player " +  currentPlayer + " Wins!");
            gameOver = true;
            return;
        }

        //Check Draw
        if(isDraw()){
            status.setText("It's a Draw!");
            gameOver = true;
            return;
        }

        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        status.setText("Player " + currentPlayer + "'s turn");
    }

    boolean checkWinner(){
        int[][] winPatterns = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
        };

        for(int[] pattern : winPatterns){
            int a = pattern[0];
            int b = pattern[1];
            int c = pattern[2];

            String first = buttons[a].getLabel();

            if(!first.equals("") && first.equals(buttons[b].getLabel())
            && first.equals(buttons[c].getLabel())){
                return true;
            }
        }
        return false;
    }

    boolean isDraw(){
        for(Button button : buttons){
            if(button.getLabel().equals("")){
                return false;
            }
        }
        return true;
    }

    //Reset the complete game
    void resetGame(){
        for(Button button : buttons){
            button.setLabel("");
            button.setForeground(textColor);
        }

        currentPlayer = 'X';
        gameOver = false;
        status.setText("Player X's turn");
    }
}