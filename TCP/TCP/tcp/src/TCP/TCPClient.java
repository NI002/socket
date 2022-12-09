package TCP;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import userInterface.ServerFileExplorer;

public class TCPClient {
    String sentence;
    String modifiedSentence;
    StringBuilder everything;
    String line;
    BufferedReader inFromUser;
    Socket clientSocket;
    DataOutputStream outToServer;
    BufferedInputStream bytesIn;
    BufferedReader inFromServer;
    boolean sendingFile;
    String[] array;
    String[] directory;

    ServerFileExplorer serverFileExplorer;

    public TCPClient(ServerFileExplorer serverFileExplorer) {
        this.serverFileExplorer = serverFileExplorer;
        sentence = "";
        inFromUser = new BufferedReader(new InputStreamReader(System.in));
    }

    public void initData(String argv[]) throws Exception {
        if (argv.length < 2) {
            print("Missing arguments, the right way to run the process is: TCPClient <SERVER_IP_ADDRESS> <SERVER_PORT>");
            System.exit(0);
        }
        try {
            clientSocket = new Socket(argv[0], Integer.parseInt(argv[1]));
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            bytesIn = new BufferedInputStream(clientSocket.getInputStream());
            inFromServer = new BufferedReader(new InputStreamReader(bytesIn, StandardCharsets.UTF_8));
            sendingFile = false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Information sur le serveur incorrecte ou verifier l'etat du serveur ",
                    e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void startClient() {
        TimerTask task = new TimerTask() {
            public void run() {
                try {
                    initClient();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Timer myTimer = new Timer("uitimer");
        myTimer.schedule(task, 0, 700);
    }

    public void initClient() throws Exception {
        try {
            if (sentence != "") {
                sendingFile = false;
                System.out.print("client> ");
                outToServer.writeBytes(sentence + '\n');
                System.out.println(sentence);
                array = sentence.split("[ ]+");
                if (array[0].equals("put")) {
                    sendingFile = true;
                    String filename = "";
                    for (int i = 1; i < array.length; ++i) {
                        filename += array[i];
                        if (i != array.length - 1) {
                            filename += " ";
                        }
                    }
                    System.out.println("file name " + filename);
                    Path path = Paths.get(currDir(), filename);
                    System.out.println("path " + path);
                    if (!Files.exists(path)) {
                        System.out.println("File doesnt not exist");
                        sendingFile = false;
                    } else {
                        File inputFile = new File(currDir(), filename);
                        byte[] data = new byte[(int) inputFile.length()];
                        FileInputStream fis = new FileInputStream(inputFile);
                        OutputStream outToClient2 = clientSocket.getOutputStream();
                        outToServer.writeBytes("<StartOfFile>\n");
                        outToServer.writeBytes("<NumberOfBytes>\n");
                        outToServer.writeBytes(data.length + "\n");
                        outToServer.writeBytes("<FileName>\n");
                        outToServer.writeBytes(filename + "\n");

                        TimeUnit.SECONDS.sleep(1);
                        int count;
                        while ((count = fis.read(data, 0, data.length)) > 0) {
                            outToClient2.write(data, 0, count);
                        }

                        outToClient2.flush();
                        fis.close();
                        sendingFile = true;
                    }
                }
                everything = new StringBuilder();
                line = "";
                while (!((line = inFromServer.readLine()).equals("<EndOfStream>")) && !sendingFile) {
                    if (line.equals("<StartOfFile>")) {
                        InputStream bytesIn2 = clientSocket.getInputStream();
                        line = inFromServer.readLine();
                        byte[] data = new byte[1];
                        int byteNum = 0;
                        if (line.equals("<NumberOfBytes>")) {
                            line = inFromServer.readLine();
                            data = new byte[Integer.parseInt(line)];
                            byteNum = Integer.parseInt(line);
                        }
                        line = inFromServer.readLine();
                        String name = "";
                        if (line.equals("<FileName>")) {
                            line = inFromServer.readLine();
                            name = line;
                        }
                        File fileCreated = new File(currDir(), name);//path make directory D:
                        fileCreated.createNewFile();
                        FileOutputStream newFile = new FileOutputStream(fileCreated);
                        int count;
                        int i = 0;
                        while ((count = bytesIn2.read(data, 0, data.length)) > 0) {
                            newFile.write(data, 0, count);
                            i += count;
                            if (i >= byteNum - 1) {
                                break;
                            }
                        }
                        newFile.close();

                        everything.append("File succesfully transfered.\n");
                        break;
                    }
                    everything.append(line + "\n");
                }

                System.out.println(everything.toString());
                String grossList = everything.toString();
                String[] list = grossList.split("\n");
                if (ifCommande(list)) {
                    this.directory = list;
                    serverFileExplorer.refreshTabel(list, this);
                }
            }
            sentence = "";
        } catch (ConnectException e) {
            System.out.println("Error: " + e.getMessage()
                    + "\nPlease make sure you put the right server info and that the server is listening.");
            System.exit(1);
        } catch (SocketException e) {
            System.out.println("Error: " + e.getMessage()
                    + "\nSeems like there has been a problem connecting to the server, please make sure the server is running.");
            System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage() + "\nConnection was lost, please try to reconnect.");
            System.exit(1);
        }
    }

    private boolean ifCommande(String[] list) {
        if (list[0].equals("")) {
            return false;
        }
        if (list[0].indexOf("Current Directory:") != -1) {
            return false;
        }
        if (list[0].equals("File succesfully transfered.")) {
            return false;
        }
        return true;
    }

    public void sendArgument(String argument) {
        sentence = argument;
    }

    public void closeConnexion() throws Exception {
        try {
            clientSocket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // returns the current directory
    private static String currDir() {
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec("cmd /c cd path");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public String[] getFolderListe() {
        return this.directory;
    }

    public static void print(String stuff) {
        System.out.println(stuff);
    }
}