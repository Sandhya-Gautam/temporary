import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class  ScoreBoard extends JPanel {
    String user_Name, highScore;
    BufferedImage backgroundImage;
    JPanel logo;
    protected static JLabel score,level, uN;

    ScoreBoard( String user){
        //setting username from login
        user_Name="Username:"+user;

        //setting score from gamePanel
        highScore="Highest-Score:"+GamePanel.highest;

        logo= new JPanel();
        level= new JLabel(highScore);
        uN = new JLabel(user_Name);
        score=new JLabel("Score:"+GamePanel.score);

        setBackground(new Color(121,198,136));
        setForeground(Color.green);
        logo.setBackground(new Color(121,198,136));



        score.setFont(new Font("Rust",Font.BOLD,20));
        uN.setFont(new Font("Rust",Font.BOLD,20));
        level.setFont(new Font("Rust",Font.BOLD,20));

        this.setVisible(true);
        setLayout(new GridLayout());
        add(logo);add(level); add(uN);add(score);

        try {
            backgroundImage = ImageIO.read(new File("src/logo.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 10, 20, 50, 34, logo);
        }
    }
    public void getScore(int score){
        this.score.setText("Score:"+score);
    }
}