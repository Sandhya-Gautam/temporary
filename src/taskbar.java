import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class taskbar extends JPanel  implements ActionListener{

    JButton logOut, newGame,  help;
     public static boolean start=false,logout=false;
    taskbar() {
         logOut= new JButton("Logout");
        newGame = new JButton("New Game");
        help = new JButton("Help");

        setVisible(true);
        setBackground(new Color(121, 198, 136));
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(layout);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(logOut, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(newGame, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(help, gbc);

        Dimension buttonSize = new Dimension(this.getWidth(), 100);
        Component[] components = this.getComponents();
        for (Component component : components) {
                JButton buttonComponent = (JButton) component;
                buttonComponent.setBackground(new Color(47, 196, 112));
                buttonComponent.setPreferredSize(buttonSize);
                buttonComponent.setFont(new Font("Rust", Font.BOLD, 20));
                buttonComponent.addActionListener(this);

        }
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==logOut){
            logout=true;
        }
         else if(e.getSource()==newGame){
            start=true;
         }
         else{
             new help();
        }


    }
}
