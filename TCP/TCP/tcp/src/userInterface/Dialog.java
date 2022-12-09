package userInterface;

//import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Dialog extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JLabel action = new JLabel("HEY ! please connect to the serveur !");

    public Dialog() {
        this.add(action);
    }
}
