import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class login extends JPanel implements ActionListener {
    JPanel panel,panel2;
    private JTextField username,message;
    private JPasswordField password;
    private JButton login,sign_in;
    public static boolean sign,log;
    DatabaseConnection connection=new DatabaseConnection();
    private BufferedImage background;
    private Dimension screenSize;

    public login()  {
        panel=new JPanel();
        username = new JTextField();
        password = new JPasswordField();
        message= new JTextField("       Dont Have an account?");
        message.setEditable(false);
        sign_in= new JButton("Sign-in  ");

        login = new JButton("               Log-in                       ");
        panel2=new JPanel();
        panel2.add(message);
        panel2.add(sign_in);
        panel2.setLayout(new BoxLayout(panel2,BoxLayout.X_AXIS));
        sign = false;
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        panel.setPreferredSize(new Dimension(420,(int)screenSize.getHeight()));
        panel.setOpaque(false);
        panel2.setOpaque(false);
        message.setOpaque(false);
        message.setBorder(null);
        sign_in.setBorderPainted(false);
        sign_in.setContentAreaFilled(false);
        username.setBackground(new Color(182, 255, 128 ));
        password.setBackground(new Color(182, 255, 128));
        login.setBackground(new Color(32, 182, 74));
        try {
            background = ImageIO.read(new File("src/nyokaback.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalStrut((int)screenSize.getHeight()/3));
        panel.add(username);
        panel.add(Box.createVerticalStrut(20));
        panel.add(password);
        panel.add(Box.createVerticalStrut(10));
        panel.add(panel2);
        panel.add(Box.createVerticalStrut(10));
        panel.add(login);
        panel.add(Box.createVerticalStrut((int)screenSize.getHeight()/2));
        add(panel);
        setFontForAllComponents(panel, new Font("Rust", Font.PLAIN, 20));
        sign_in.addActionListener(this);
        login.addActionListener(this);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight(), this);
        }
    }
    private static void setFontForAllComponents(Component component, Font font) {
        if (component instanceof Container) {
            for (Component childComponent : ((Container) component).getComponents()) {
                setFontForAllComponents(childComponent, font);
                childComponent.setForeground(new Color(23, 115, 47));
            }
        }
        component.setFont(font);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==sign_in){
            // When "Sign-in" field is clicked, show the sign-in panel
            signInPanel();
        }
        else {
            String usrname, pwd;
            usrname= username.getText();
            pwd=password.getText();
            // When "Log-in" field in the signin class is clicked, show the login panel
            connection.record(usrname,pwd);
        }
    }
    // Method to show the sign-in panel
    public void signInPanel() {
        removeAll();
        add(new signin(this)); // Pass "this" to the signin class to handle switching back to login panel
        revalidate();
        repaint();
    }
    // Method to show the login panel

    public void loginPanel() {
        removeAll();
        add(panel);
        revalidate();
        repaint();
    }
    }


