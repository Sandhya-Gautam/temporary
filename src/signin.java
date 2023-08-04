import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signin extends JPanel implements ActionListener {
    JPanel panel,panel2;
    JLabel []label;
    private JTextField username,message, email,firstname, lastname ;
    private JPasswordField password;
    private JButton sign_in,log_in;
    login log;
    private BufferedImage background;
    private Dimension screenSize;

    public signin(login login)  {
        log=login;
        panel=new JPanel();
        label=new JLabel[5];
        label[0]=new JLabel("First Name");
        label[1]=new JLabel("Last Name");
        label[2]=new JLabel("Email");
        label[3]=new JLabel("Username");
        label[4]=new JLabel("Password");
        firstname=new JTextField();
        lastname=new JTextField();
        email=new JTextField();
        username = new JTextField();
        password = new JPasswordField();
        message= new JTextField("       Already  Have an account?");
        message.setEditable(false);
        log_in= new JButton("Log-in");
        sign_in = new JButton("                Sign-in                      ");
        panel2=new JPanel();
        panel2.add(message);
        panel2.add(log_in);
        panel2.setLayout(new BoxLayout(panel2,BoxLayout.X_AXIS));
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        panel.setPreferredSize(new Dimension(420,(int)screenSize.getHeight()));
        panel.setOpaque(false);
        panel2.setOpaque(false);
        message.setOpaque(false);
        message.setBorder(null);
        log_in.setBorderPainted(false);
        log_in.setContentAreaFilled(false);
        username.setBackground(new Color(182, 255, 128 ));
        password.setBackground(new Color(182, 255, 128));
        sign_in.setBackground(new Color(32, 182, 74));
        firstname.setBackground(new Color(182, 255, 128 ));
        lastname.setBackground(new Color(182, 255, 128 ));
        email.setBackground(new Color(182, 255, 128 ));
        try {
            background = ImageIO.read(new File("src/nyokaback.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalStrut((int)screenSize.getHeight()/4));
        panel.add(label[0]);
        panel.add(firstname);
        panel.add(Box.createVerticalStrut(20));
        panel.add(label[1]);
        panel.add(lastname);
        panel.add(Box.createVerticalStrut(20));
        panel.add(label[2]);
        panel.add(email);
        panel.add(Box.createVerticalStrut(20));
        panel.add(label[3]);
        panel.add(username);
        panel.add(Box.createVerticalStrut(20));
        panel.add(label[4]);
        panel.add(password);
        panel.add(Box.createVerticalStrut(10));
        panel.add(panel2);
        panel.add(Box.createVerticalStrut(10));
        panel.add(sign_in);
        panel.add(Box.createVerticalStrut((int)screenSize.getHeight()/3));
        add(panel);
        setFontForAllComponents(panel, new Font("Rust", Font.PLAIN, 20));
        sign_in.addActionListener(this);
        log_in.addActionListener(this);
        setBackground(new Color(126,199,81));

    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==sign_in) {
            String emailAddress= email.getText();
            char[] pass= password.getPassword();
            String passwordd=pass.toString();
            System.out.println(passwordd);
            String usrname= username.getText();
            if (!isValidEmail(emailAddress)) {
                JOptionPane.showMessageDialog(this, "Invalid email", "Error", JOptionPane.ERROR_MESSAGE);
            }else if(!checkPasswordStrength(passwordd)){
                JOptionPane.showMessageDialog(this, "Weak Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                //inserting data to database and returns true if data is inserted
               boolean inserted= log.connection.keepRecord(usrname,emailAddress,passwordd);
               //if data is inserted it switch back to login page to login
               if(inserted){
                   log.loginPanel();
               }else{
                   JOptionPane.showMessageDialog(this, "Invalid data", "Error", JOptionPane.ERROR_MESSAGE);
                   log.signInPanel();
               }
            }
        }else if(e.getSource()==log_in){
            log.loginPanel();
        }
    }
    private boolean isValidEmail(String email) {
        // Regular expression for validating email addresses
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private boolean checkPasswordStrength(String password) {
        // Length check
        if (password.length() < 6) {
            return false;
        }

        // Presence of uppercase and lowercase letters, numbers, and special characters
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialCharacter = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSpecialCharacter = true;
            }
        }

        if (!(hasUpperCase && hasLowerCase && hasDigit && hasSpecialCharacter)) {
            return false;
        }
        return true;
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

}



