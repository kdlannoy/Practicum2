package greeter;

import java.net.*;
import java.io.*;


/*
 * De readers en writers moeten nog worden afgesloten om zuivere code te hebben, maar wegens tijdsgebrek
 * is dit niet gebeurd. We zijn er dus van bewust dat we de code nog veel beter hadden kunnen commentarieren
 * en de gebruikte readers en writers proper af sluiten. Onze excuses.
 * Jan Van de Vyver en Kianni Lannoye
 */
public class Client {

    public static void main(String[] args) {
        InetAddress host = null;
        int serverPort = 5555;
        Socket socket = null;
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(
                System.in));
        String name = "";
        try {
            host = InetAddress.getLocalHost();
            socket = new Socket(host, serverPort);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            do {
                // read in a name
                System.out.println("Enter a name : ");
                consoleInput = new BufferedReader(new InputStreamReader(
                        System.in));
                name = consoleInput.readLine();

                if (!(name.equals("stop"))) {
                    
                    bw.write(name+"\n");
                    bw.newLine();
                    bw.flush();

                    //receive reply
                    String reply = br.readLine();

                    // print the greeting
                    System.out.println("Reply from server = "
                            + reply);
                }
            } while (!(name.equals("stop")));
        } catch (UnknownHostException e) {
            System.err.println(e);
        } catch (SocketException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println(e);
                }
            }

        }
    }
}
