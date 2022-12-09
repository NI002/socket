package userInterface;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import TCP.TCPClient;

public class SendingListener implements MouseMotionListener, MouseListener {
    TCPClient tcpClient;
    ClientFileExplorer clientFileExplorer;

    public SendingListener(TCPClient tcpClient, ClientFileExplorer clientfileExplorer) {
        this.tcpClient = tcpClient;
        this.clientFileExplorer = clientfileExplorer;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String path = recreatePath(clientFileExplorer.getPath());
        tcpClient.sendArgument("put " + path);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    private String recreatePath(String path) {
        path = path.replace('\\', '/');
        String[] list = path.split(":");
        return list[1];
    }

}
