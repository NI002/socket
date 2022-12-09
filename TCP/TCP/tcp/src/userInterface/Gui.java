package userInterface;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import TCP.TCPClient;

public class Gui extends JFrame {

    /**
     *
     */

    private static final long serialVersionUID = 1L;

    TCPClient clientServer;

    public Gui() throws Exception {
        ServerFileExplorer serverFileExplorer = new ServerFileExplorer(clientServer);
        clientServer = new TCPClient(serverFileExplorer);
        ClientFileExplorer clientfileExplorer = new ClientFileExplorer();
        Login login = new Login(clientServer);
        TransferPanel transferPanel = new TransferPanel(clientServer, clientfileExplorer);
        Dialog dialog = new Dialog();
        login.setLayout(new FlowLayout(FlowLayout.LEADING));

        this.setTitle("FTP");
        this.setSize(900, 520);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.getContentPane().add(login, "North");
        this.getContentPane().add(new JScrollPane(clientfileExplorer.getMyTree()), "West");
        this.getContentPane().add(transferPanel, "Center");
        this.getContentPane().add(new JScrollPane(serverFileExplorer), "East");
        this.getContentPane().add(dialog, "South");
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        pack();
    }
}
