package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import TCP.TCPClient;
import user.User;

public class LoginListener implements ActionListener {
    User[] users = new User[4];
    JTextField ip, port, username, password;
    TCPClient clientServer;

    public LoginListener(JTextField ip, JTextField port, JTextField usernameField, JTextField passwordField,
            TCPClient clientServer) {
        System.out.println("initiating user");
        createUser();
        this.ip = ip;
        this.port = port;
        this.username = usernameField;
        this.password = passwordField;
        this.clientServer = clientServer;
    }

    private void createUser() {
        for (int i = 0; i < users.length; i++) {
            users[i] = new User("user" + i, "0000", "user" + 1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String str_ip = ip.getText();
        String str_port = port.getText();

        String str_username = username.getText();
        String str_password = password.getText();

        try {
            Login(str_username, str_password);
            String[] args = { str_ip, str_port };
            clientServer.initData(args);
            clientServer.startClient();
            clientServer.sendArgument("cd /server/" + str_username);
            TimeUnit.SECONDS.sleep(2);
            clientServer.sendArgument("ls");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Utilisateur introuvable , verifiez vos informations", ex.getMessage(),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void Login(String username, String password) throws Exception {
        for (int i = 0; i < users.length; i++) {
            if (users[i].getUsername().equals(username) && users[i].getPassword().equals(password)) {
                return;
            }
        }
        throw new Exception();
    }
}
