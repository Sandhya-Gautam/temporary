import java.awt.*;
import javax.swing.*;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.EventListener;
public class GamePanel extends JPanel implements KeyListener {
    private final  int WIDTH;
    public static int highest=0;
    private final int HEIGHT;
    private BufferedImage snakeBodyImage;

    private static final int UNIT_SIZE = 25;
    private static final int DELAY = 200;

    private final List<Integer> snakeX;
    private final List<Integer> snakeY;
    private int appleX;
    private int appleY;
    public static char direction;
    public boolean isRunning;
    public static Timer timer;
    public static int score;
    boolean start=false;
    public GamePanel() {
        setFocusable(true);
        Dimension panelSize=getMaximumSize();
        WIDTH=(int) panelSize.getWidth()/24-20;
        HEIGHT= (int)panelSize.getHeight()/37+25;
        snakeX = new ArrayList<>();
        snakeY = new ArrayList<>();
        direction = 'R';
        isRunning = false;
        setBackground(new Color(82, 187, 102));
        setBorder(BorderFactory.createLineBorder(new Color(13, 161, 31), 10));
        try {
            snakeBodyImage = ImageIO.read(new File("src/snake.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();

    }


    public void startGame() {
        score = 0;
        start=true;
        snakeX.clear();
        snakeY.clear();
        snakeX.add(0); // starting head position
        snakeY.add(0);
        generateApple();
        isRunning = true;
        addKeyListener(this);
        timer = new Timer(DELAY, e -> gameLoop());
        timer.start();

    }

    public void gameLoop() {
        //checks if user clicks new game again
        if(taskbar.start){
           isRunning=false;
        }
       else{
            timer.start();

        }

        //normal condition of running
        if (!isRunning) {
            timer.stop();
            return;
        }
            move();
            checkCollision();
            repaint();
    }

    public void move() {
        for (int i = (snakeX.size()) - 1; i > 0; i--) {
            snakeX.set(i, snakeX.get(i - 1));
            snakeY.set(i, snakeY.get(i - 1));
        }

        switch (direction) {
            case 'U' :
                snakeY.set(0, snakeY.get(0) - UNIT_SIZE);
                break;
            case 'D' :
                snakeY.set(0, snakeY.get(0) + UNIT_SIZE);
                break;
            case 'L' :
                snakeX.set(0, snakeX.get(0) - UNIT_SIZE);
                break;
            case 'R' :
                snakeX.set(0, snakeX.get(0) + UNIT_SIZE);
                break;
        }
    }

    public void checkCollision() {
        // Check if snake collides with itself
        for (int i = 1; i < (snakeX.size()); i++) {
            if (snakeX.get(i).equals(snakeX.get(0)) && snakeY.get(i).equals(snakeY.get(0))) {
                isRunning = false;
                break;
            }
        }

        // Check if snake collides with the boundaries
        if (snakeX.get(0) < 0 || (snakeX.get(0) )>= WIDTH-5 || (snakeY.get(0)) < 0 || (snakeY.get(0))>= HEIGHT-5) {
            isRunning = false;
        }

        // Check if snake collides with the apple
        if (snakeX.get(0).equals(appleX) && snakeY.get(0).equals(appleY)) {
            // Increase the score and generate a new apple
            score++;
            //setting top score
            if(highest<=score){
                highest=score;
            }
            generateApple();

            // Grow the snake by adding a new segment
            snakeX.add(snakeX.get(snakeX.size() - 1));
            snakeY.add(snakeY.get(snakeY.size() - 1));
        }
    }

    public void generateApple() {
    //generates the position of apple
            Random random = new Random();
            appleX = random.nextInt(((WIDTH - 10) / UNIT_SIZE)) * UNIT_SIZE;
            appleY = random.nextInt(((HEIGHT - 10) / UNIT_SIZE)) * UNIT_SIZE;

    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!start) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 60));
            g.drawString("Start Game", WIDTH / 2 - 120, HEIGHT / 2);
        }
        else {
            for (int i = 0; i < snakeX.size(); i++) {
                if (i == 0) {
                    g.drawImage(snakeBodyImage, snakeX.get(i) + 10, snakeY.get(i) + 10, UNIT_SIZE, UNIT_SIZE, this);
                } else {
                    g.setColor(Color.green);
                    g.fillRect(snakeX.get(i) + 10, snakeY.get(i) + 10, UNIT_SIZE, UNIT_SIZE);
                }
            }

            // Draw the apple
            if (isRunning) {
                g.setColor(Color.red);
                g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
                this.repaint();
            }
            // Game over message
            if (!isRunning) {
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 40));
                g.drawString("Game Over", WIDTH / 2 - 100, HEIGHT / 2);
                this.repaint();
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_UP  && direction != 'D') {
            direction = 'U';
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && direction != 'U') {
            direction = 'D';
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && direction != 'R') {
            direction = 'L';
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && direction != 'L') {
            direction = 'R';
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    public int giveScore(){
        return score;
    }

}
