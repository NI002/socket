package userInterface;

import javax.swing.JButton;
import javax.swing.JPanel;

import TCP.TCPClient;

public class TransferPanel extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    TCPClient tcpClient;
    JButton upload = new JButton("upload");
    JButton donwload = new JButton("download");

    public TransferPanel(TCPClient tcpClient, ClientFileExplorer clientfileExplorer) {
        this.tcpClient = tcpClient;
        upload.addMouseListener(new SendingListener(tcpClient, clientfileExplorer));
        this.add(upload);
        this.add(donwload);
    }

}
