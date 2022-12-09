package userInterface;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import TCP.TCPClient;

public class ServerFileExplorer extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JScrollPane spTable;
    JTable serverFolder;
    ServerDirectoryListener serverDirectoryListener = new ServerDirectoryListener();

    public ServerFileExplorer(TCPClient clientServer) {
        String[] header = { "files" };
        String[][] data = { { " " } };
        serverFolder = new JTable(data, header);
        // serverFolder.setModel(new TableModel());
        spTable = new JScrollPane(serverFolder);
        serverFolder.addMouseListener(serverDirectoryListener);
        add(serverFolder.getTableHeader());
        add(spTable);
    }

    public void refreshTabel(String[] dir, TCPClient client) {
        String[] header = { "files", "Type" };
        remove(spTable);
        // this.repaint();
        serverDirectoryListener.setClient(client);
        serverFolder.removeMouseListener(serverDirectoryListener);
        serverFolder = new JTable(initData(dir), header);
        serverFolder.setDefaultEditor(Object.class, null);
        serverFolder.addMouseListener(serverDirectoryListener);
        spTable = new JScrollPane(serverFolder);
        this.add(serverFolder.getTableHeader());
        this.add(spTable);
    }

    private String[][] initData(String[] liste) {
        String[][] data = new String[liste.length + 1][2];
        data[0][0] = "..";
        data[0][1] = "folder";
        for (int i = 1; i < liste.length + 1; i++) {
            data[i][0] = liste[i - 1];
            if (liste[i - 1].indexOf(".") < 0) {
                data[i][1] = "folder";
            } else {
                data[i][1] = "downloadable";
            }
        }
        return data;
    }
}