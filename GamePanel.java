import java.awt.*;
import java.awt.event.*;

public class GamePanel extends Panel implements Runnable , KeyListener{
    int[] snakeX = new int[100];
    int[] snakeY = new int[100];

    String direction = "RIGHT";

    int score = 0;
    int foodX , foodY;

    int snakeLength = 3;
    boolean gameOver = false;
    boolean paused = false;
    boolean gameStarted = false;
    Thread gameThread;

    GamePanel(){
        setBackground(Color.black);

        snakeX[0] = 300;
        snakeY[0] = 300;

        snakeX[1] = 280;
        snakeY[1] = 300;

        snakeX[2] = 260;
        snakeY[2] = 300;

        foodX = 400;
        foodY = 300;

        addKeyListener(this);
        setFocusable(true);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run(){
        while(!gameOver){
            if(gameStarted && !paused){
                moveSnake();
                checkFood();
                checkCollision();
            }
            repaint();

            try{
                Thread.sleep(100);
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        repaint();
    }

    public void checkFood() {

        if(snakeX[0] == foodX && snakeY[0] == foodY) {
            snakeLength++;
            score++;

            generateFood();
        }
    }

    public void generateFood() {
        boolean onSnake;

        do {
            foodX = (int)(Math.random() * 29) * 20;
            foodY = (int)(Math.random() * 29) * 20;

            onSnake = false;

            for(int i = 0; i < snakeLength; i++) {

                if(foodX == snakeX[i] && foodY == snakeY[i]) {

                    onSnake = true;
                    break;
                }
            }
        } while(onSnake);
    }

    public void checkCollision(){
        if(snakeX[0] < 0 || snakeX[0] >= getWidth() || snakeY[0] < 0 || snakeY[0] >= getHeight()){
            gameOver = true;
        }

        for(int i=1;i<snakeLength;i++){
            if(snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]){
                gameOver = true;
            }
        }
    }

    public void moveSnake(){
        for(int i = snakeLength - 1; i > 0; i--){
            snakeX[i] = snakeX[i-1];
            snakeY[i] = snakeY[i-1];
        }

        if(direction.equals("RIGHT")){
            snakeX[0] += 20;
        } 
        else if(direction.equals("LEFT")){
            snakeX[0] -= 20;
        }
        else if(direction.equals("UP")){
            snakeY[0] -= 20;
        }
        else if(direction.equals("DOWN")){
            snakeY[0] += 20;
        }
    }

    public void restartGame(){
        snakeLength = 3;

        snakeX[0] = 300;
        snakeY[0] = 300;

        snakeX[1] = 280;
        snakeY[1] = 300;

        snakeX[2] = 260;
        snakeY[2] = 300;

        foodX = 400;
        foodY = 300;

        score = 0;

        direction = "RIGHT";
        gameOver = false;
        paused = false;
        gameStarted = true;

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void keyPressed(KeyEvent e){

        //Enter -> start game
        if (e.getKeyCode() == KeyEvent.VK_ENTER && !gameStarted) { 
            gameStarted = true; 
            repaint(); 
            return;
        }

        // P -> pause
        if(e.getKeyCode() == KeyEvent.VK_P && gameStarted && !gameOver) {
            paused = !paused;
            repaint();

            return;
        }

        // R -> Restart after game over
        if(e.getKeyCode() == KeyEvent.VK_R && gameOver){
            restartGame();
            return;
        }

        // Movement control
        if(e.getKeyCode() == KeyEvent.VK_RIGHT && !direction.equals("LEFT")){
            direction = "RIGHT";
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && !direction.equals("RIGHT")){
            direction = "LEFT";
        }
        else if(e.getKeyCode() == KeyEvent.VK_UP && !direction.equals("DOWN")){
            direction = "UP";
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && !direction.equals("UP")){
            direction = "DOWN";
        }
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
    
    public void paint(Graphics g){
        super.paint(g);

        // START SCREEN
        if (!gameStarted) { 
            g.setColor(Color.green);
            g.setFont(new Font("Arial", Font.BOLD, 50)); 
            g.drawString("SNAKE GAME", 170, 180); 
            g.setColor(Color.white); 
            g.setFont(new Font("Arial", Font.PLAIN, 22)); 
            g.drawString("Press ENTER to Start", 185, 280); 
            g.drawString("Arrow Keys - Move", 200, 330); 
            g.drawString("P - Pause", 250, 370); 
            return;
        }

        // DRAW SNAKE
        for(int i=0;i<snakeLength;i++){
            if(i == 0){
                g.setColor(Color.orange);
            }
            else{
                g.setColor(Color.green);
            }
            g.fillRect(snakeX[i],snakeY[i],20,20);
        }

        // DRAW FOOD
        g.setColor(Color.red);
        g.fillOval(foodX,foodY,20,20);

        // SCORE
        g.setColor(Color.white);
        g.drawString("Score: "+score,20,30);

        // GAME OVER
        if(gameOver){
            g.setColor(Color.white);

            g.drawString("Game Over",250,280);
            g.drawString("Final Score: "+score,240,310);

            g.drawString("Press R to Restart",225,340);
        }

        // PAUSED
        if(paused && !gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("PAUSED",240,280);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press P to Resume",220,320);
        }
    }
}