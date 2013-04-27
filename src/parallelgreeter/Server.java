package parallelgreeter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

    public static void main(String[] args) {
        ServerSocket listen = null;
        int serverPort = 5555;

        try {
         
            listen = new ServerSocket(serverPort);

            while (true) {
                Socket client = listen.accept();
                SocketThread t = new SocketThread(client);
                t.start();

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

    private static class SocketThread extends Thread {

        private Socket client;

        public SocketThread(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {


            try {
                PrintWriter pw = new PrintWriter(client.getOutputStream());

                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                while (client.isConnected()) {
                    String reply = "" + br.readLine();
                    String rMess = "Hello, " + reply;
       

                    pw.print(rMess + "\n");
                    pw.flush();
                }
            } catch (SocketException e) {
                System.err.println(e);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}