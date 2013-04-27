package serialization;

import java.io.*;
import java.util.*;
import java.net.*;

public class Server {

    private static ArrayList<Person> db = new ArrayList<Person>();

    public static void main(String[] args) {
        fillInPhoneNumberDataBase();
        ServerSocket listen = null;
        int serverPort = 5555;

        try {
            //socket=new DatagramSocket(serverPort);
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

    private static void fillInPhoneNumberDataBase() {
        Person[] p = {new Person("Jan", "Janssens", new PhoneNumber("32", "9", "44 55 66")),
            new Person("Piet", "Pieters", new PhoneNumber("32", "50", "11 22 33")),
            new Person("Giovanni", "Totti", new PhoneNumber("49", "22", "00 99 88")),
            new Person("Jean", "Lefevre", new PhoneNumber("33", "4", "12 34 56"))};
        for (Person i : p) {
            db.add(i);
        }
    }

    private static Person lookUpPhoneNumber(Person p) {
        int index = db.indexOf(p);
        if (index >= 0) {
            return db.get(index);
        } else {
            p.setPhoneNumber(new PhoneNumber());
            return p;
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
                ObjectOutputStream bw = new ObjectOutputStream(client.getOutputStream());
                bw.flush();
                ObjectInputStream br = new ObjectInputStream(client.getInputStream());
                
                while (client.isConnected()) {

                    

                    
                    Person reply = lookUpPhoneNumber((Person) br.readObject());

                    bw.writeObject(reply);
                }
            } catch (SocketException e) {
                System.err.println(e);
            } catch (IOException e) {
                System.err.println(e);
            } catch (ClassNotFoundException e) {
                System.err.println(e);
            } 
        }
    }
}
