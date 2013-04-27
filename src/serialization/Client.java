package serialization;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {
        InetAddress host = null;
        int serverPort = 5555;
        Socket socket = null;
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(
                System.in));
        String firstName = "";
        String lastName = "";
        try {
            host = InetAddress.getLocalHost();
            socket = new Socket(host, serverPort);
            ObjectOutputStream bw = new ObjectOutputStream(socket.getOutputStream());
            bw.flush();
            ObjectInputStream br = new ObjectInputStream(socket.getInputStream());
            do {
                // read in a name
                consoleInput = new BufferedReader(new InputStreamReader(
                        System.in));
                System.out.println("Enter first name : ");
                firstName = consoleInput.readLine();
                System.out.println("Enter last name : ");
                lastName = consoleInput.readLine();
                if (!(firstName.equals("stop"))) {
                    Person p = new Person(firstName, lastName);
                    bw.writeObject(p);
                    bw.flush();                    
                    Person reply = (Person) br.readObject();
                    System.out.println("Reply from server = " + reply.toString());
                }
            } while (!(firstName.equals("stop")));
        } catch (UnknownHostException e) {
            System.err.println(e);
        } catch (SocketException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }finally {
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
