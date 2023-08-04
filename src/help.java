import javax.swing.*;
import java.awt.*;


public class help extends JFrame {
    private JTextField message;
    private JPanel panel;
    public static  Dimension screenSize;

    public help()  {
        panel=new JPanel();
        message= new JTextField(" sandhya");
        message.setEditable(false);
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        panel.setPreferredSize(screenSize);
        message.setOpaque(false);
        message.setBorder(null);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(message);
        panel.setBackground(new Color(182, 255, 128));
        message.setForeground(new Color(23, 115, 47));
        setVisible(true);
    }
}


