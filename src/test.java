import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class test extends JPanel implements KeyListener {
    private BufferedImage snakeBodyImage;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final int UNIT_SIZE = 20;
    private static final int GAME_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private static final int DELAY = 200;

    private final List<Integer> snakeX;
    private final List<Integer> snakeY;
    private int appleX;
    private int appleY;
    private char direction;
    private boolean isRunning;
    private Timer timer;
    private int score;

    public test() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);

        snakeX = new ArrayList<>();
        snakeY = new ArrayList<>();
        direction = 'R';
        isRunning = false;
        score = 0;
        try {
            snakeBodyImage = ImageIO.read(new File("src/snake.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        startGame();
    }

    public void startGame() {
        snakeX.clear();
        snakeY.clear();
        snakeX.add(0); // starting head position
        snakeY.add(0);
        generateApple();

        isRunning = true;
        timer = new Timer(DELAY, e -> gameLoop());
        timer.start();
    }

    public void gameLoop() {
        if (!isRunning) {
            timer.stop();
            return;
        }

        move();
        checkCollision();
        repaint();
    }

    public void move() {
        for (int i = snakeX.size() - 1; i > 0; i--) {
            snakeX.set(i, snakeX.get(i - 1));
            snakeY.set(i, snakeY.get(i - 1));
        }

        switch (direction) {
            case 'U' -> snakeY.set(0, snakeY.get(0) - UNIT_SIZE);
            case 'D' -> snakeY.set(0, snakeY.get(0) + UNIT_SIZE);
            case 'L' -> snakeX.set(0, snakeX.get(0) - UNIT_SIZE);
            case 'R' -> snakeX.set(0, snakeX.get(0) + UNIT_SIZE);
        }
    }

    public void checkCollision() {
        // Check if snake collides with itself
        for (int i = 1; i < snakeX.size(); i++) {
            if (snakeX.get(i).equals(snakeX.get(0)) && snakeY.get(i).equals(snakeY.get(0))) {
                isRunning = false;
                break;
            }
        }

        // Check if snake collides with the boundaries
        if (snakeX.get(0) < 0 || snakeX.get(0) >= WIDTH || snakeY.get(0) < 0 || snakeY.get(0) >= HEIGHT) {
            isRunning = false;
        }

        // Check if snake collides with the apple
        if (snakeX.get(0).equals(appleX) && snakeY.get(0).equals(appleY)) {
            // Increase the score and generate a new apple
            score++;
            generateApple();

            // Grow the snake by adding a new segment
            snakeX.add(snakeX.get(snakeX.size() - 1));
            snakeY.add(snakeY.get(snakeY.size() - 1));
        }
    }

    public void generateApple() {
        Random random = new Random();
        appleX = random.nextInt((WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the snake
        for (int i = 0; i < snakeX.size(); i++) {
            if ((i==0)) {
                g.drawImage(snakeBodyImage, snakeX.get(i), snakeY.get(i), UNIT_SIZE, UNIT_SIZE, this);
            }
            else{
                g.setColor(Color.green);
                g.fillRect(snakeX.get(i), snakeY.get(i), UNIT_SIZE, UNIT_SIZE);
            }
        }


        // Draw the apple
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

        // Draw the score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 25);

        // Game over message
        if (!isRunning) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over", WIDTH / 2 - 100, HEIGHT / 2);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && direction != 'D') {
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new test());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}