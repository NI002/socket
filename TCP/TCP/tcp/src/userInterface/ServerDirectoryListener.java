package userInterface;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JTable;

import TCP.TCPClient;

public class ServerDirectoryListener implements MouseMotionListener, MouseListener {
    private int eventCnt = 0;
    java.util.Timer timer = new java.util.Timer("doubleClickTimer", false);
    TCPClient client;

    public void setClient(TCPClient client) {
        this.client = client;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        eventCnt = e.getClickCount();
        if (e.getClickCount() == 1) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (eventCnt == 1) {
                        // System.err.println("You did a single click.");
                    } else if (eventCnt > 1) {
                        // System.err.println("you clicked " + eventCnt + " times.");
                        JTable table = (JTable) e.getSource();
                        int column = table.columnAtPoint(e.getPoint());
                        int row = table.rowAtPoint(e.getPoint());
                        System.out.println("Column: " + column);
                        System.out.println("Row: " + row);
                        String selectedCell = (String) table.getModel().getValueAt(row, column);
                        String type = (String) table.getModel().getValueAt(row, column + 1);
                        if (type.equals("folder")) {
                            System.out.println("entering folder");
                            client.sendArgument("cd " + selectedCell);
                            try {
                                TimeUnit.SECONDS.sleep(2);
                                client.sendArgument("ls " + selectedCell);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            System.out.println("getting file");
                            client.sendArgument("get " + selectedCell);
                        }
                    }
                    eventCnt = 0;
                }
            }, 400);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
