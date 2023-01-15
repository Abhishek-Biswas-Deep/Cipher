/**
 * Name: Abhishek Biswas Deep
 */

//This program is used to communicate with the client.
//This program is going to do CaesarCipher text.
import java.net.*;
import java.io.*;
import java.util.Random;

public class CaesarCipherServer {
    public static void main(String[] args) throws IOException{

        //Creating the ServerSocket object
        ServerSocket serverSock = null;

        //Choosing port from 1024 to 65535
        //Using this the client will send messages to the port.
        //If the connection fails, the catch activates.
        try{
            serverSock = new ServerSocket(50000);
        }
        catch (IOException ie){
            System.out.println("Can't listen on 50000");
            System.exit(1);
        }

        //Putting the server into a waiting state.
        //If the connection becomes successful, then a client socket is created and a message is printed out.
        Socket link = null;
        System.out.println("Listening for connection ...");

        //If the client delivers a request, the connection is accepted using a try-catch block.
        try {
            link = serverSock.accept();
        }
        catch (IOException ie){
            System.out.println("Accept failed");
            System.exit(1);
        }

        //And if the connection is successful, a message is printed out.
        System.out.println("Connection successful");
        System.out.println("Listening for input ...");

        //The methods getInputStream and getOutputStream are used to for getting references to stream associated with
        // the socket.
        //Then the OutputStream object is wrapped with a PrintWriter object and the Input Stream object with a
        // BufferedReader object.
        PrintWriter output = new PrintWriter(link.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(link.getInputStream()));

        //Then the BufferedReader and PrintWriter is set up for sending and receiving data.
        //The readLine is used for receiving data and the method println is used for sending data.
        //Then for Caesar Cipher a random key is generated and sent to the client.
        //After that, the messages delivered are encrypted using the key.
        //The message gets decrypted using the key and the message typed by the user.
        //The program stops after the user types in Bye.
        String inputLine;
        Random random = new Random();
        int key = random.nextInt(25);
        boolean encryptMode = false;
        while ((inputLine = input.readLine())!=null) {

            if (!encryptMode) {
                System.out.println("Message from the client: " + inputLine);
                if (inputLine.equals("Please send the key")) {
                    output.println("The key is: " + key);
                    encryptMode = true;
                } else {
                    output.println(inputLine);
                }

            } else {
                char[] answers = inputLine.toCharArray();
                System.out.print("Message from the client: ");
                for (char i : answers) {
                    if (Character.isUpperCase(i)) {
                        i = (char)((i - 65 + key) % 26 + 65);
                        System.out.print(i);
                    } else if (Character.isLowerCase(i)) {
                        i = (char)((i - 97 + key) % 26 + 97);
                        System.out.print(i);
                    } else if (Character.isWhitespace(i)) {
                        System.out.print(i);
                    }

                }
                System.out.println("");
                System.out.println("Decrypted: " + inputLine);
            }

            if (inputLine.equals("Bye")) {
                System.out.println("Closing connection");
                break;
            }

        }

        //The connections are closed.
        output.close();
        input.close();
        link.close();
        serverSock.close();

    }

}