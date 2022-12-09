package userInterface;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import TCP.TCPClient;
import java.awt.Color;

public class Login extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    JLabel ip = new JLabel("ip :");
    JLabel port = new JLabel("port :");
    JLabel username = new JLabel("username :");
    JLabel password = new JLabel("password :");

    JTextField ipField = new JTextField("192.168.56.1", 10);
    JTextField portField = new JTextField("9090", 10);
    JTextField usernameField = new JTextField("user2", 10);
    JTextField passwordField = new JTextField("0000", 10);
    JButton login = new JButton("se connecter au serveur");

    public Login(TCPClient clientServer) {
        super();
        this.add(ip);
        this.add(ipField);
        this.add(port);
        this.add(portField);
        this.add(username);
        this.add(usernameField);
        this.add(password);
        this.add(passwordField);
        this.add(login);
        login.addActionListener(new LoginListener(ipField, portField, usernameField, passwordField, clientServer));
        javax.swing.border.Border blackline = BorderFactory.createLineBorder(Color.black);
        this.setBorder(blackline);
    }

    public JLabel getIp() {
        return ip;
    }

    public void setIp(JLabel ip) {
        this.ip = ip;
    }

    public JLabel getPort() {
        return port;
    }

    public void setPort(JLabel port) {
        this.port = port;
    }

    public JTextField getIdField() {
        return ipField;
    }

    public void setIdField(JTextField ipField) {
        this.ipField = ipField;
    }

    public JTextField getPortField() {
        return portField;
    }

    public void setPortField(JTextField portField) {
        this.portField = portField;
    }

}
