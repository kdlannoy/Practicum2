package greeter;

import java.net.*;
import java.io.*;

public class Server {

    public static void main(String[] args) {
 
        ServerSocket listen = null;
        int serverPort = 5555;

        try {
            listen = new ServerSocket(serverPort);

            while (true) {
                Socket client = listen.accept();

                PrintWriter pw = new PrintWriter(client.getOutputStream());
                pw.flush();
               BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                while (client.isConnected()) {

                    
                    String reply = "" + br.readLine();
                    String rMess = "Hello, " + reply;

                    pw.print(rMess + "\n");
                    pw.flush();
                }

            }
        } catch (SocketException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            if (listen != null) {
                try {
                    listen.close();
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        }
    }
}
