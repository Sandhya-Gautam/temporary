import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame implements KeyListener {
    ScoreBoard score;
    GamePanel gameBase;
    taskbar task;
    public static Timer timer;
    GameFrame(String user) {
        score = new ScoreBoard(user);
        gameBase = new GamePanel();
        task=new taskbar();
        setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        this.getContentPane().setBackground(new Color(151,217,164));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets= new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx =1;
        gbc.weighty=1;
        gbc.gridwidth =3;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(score,gbc);
        gbc.gridwidth=1;
        gbc.gridheight =2;
        gbc.ipady=500;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(task, gbc);
        gbc.ipady=500;
        gbc.ipadx=800;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth=2;
        gbc.gridheight =2;
        add(gameBase, gbc);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        timer=new Timer(200,e->check());
        timer.start();
        addKeyListener(this);
    }


  //  keep checking the button action
    public void check(){
        checkAction();
    }


    //detect the button action
     public void  checkAction(){
        if(taskbar.start){
            gameBase.startGame();
            taskbar.start=false;
        }
        if(taskbar.logout){


        }
        score.getScore(gameBase.giveScore());

     }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_UP  && GamePanel.direction != 'D') {
            GamePanel.direction = 'U';
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && GamePanel.direction != 'U') {
            GamePanel.direction = 'D';
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && GamePanel.direction != 'R') {
            GamePanel.direction = 'L';
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && GamePanel.direction != 'L') {
            GamePanel.direction = 'R';
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    public static void main(String[] args){
       new GameFrame("Ram");
    }
}