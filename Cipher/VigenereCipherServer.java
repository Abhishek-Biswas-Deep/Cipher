/**
 * Name: Abhishek Biswas Deep
 */

//This program is going to communicate with the client.
//This program is going to provide a Ciphertext using Vigenere cipher.
import java.net.*;
import java.io.*;
import java.util.Random;

public class VigenereCipherServer {
    public static void main(String[] args) throws IOException{

        //A ServerSocket object is created.
        ServerSocket serverSock = null;

        //The ServerSocket object on a port is instantiated.
        try{
            serverSock = new ServerSocket(50000);
        }

        //If the above fails, this gets activated.
        catch (IOException ie){
            System.out.println("Can't listen on 50000");
            System.exit(1);
        }

        //If the connection succeeds, a client socket is created and later a print-out message is made
        // that says "Listening for connection ...".
        //This is just waiting for a client to connect to the server.
        Socket link = null;
        System.out.println("Listening for connection ...");

        //If the client delivers a request, the connection is accepted using the try-catch block.
        try {
            link = serverSock.accept();
        }
        catch (IOException ie){
            System.out.println("Accept failed");
            System.exit(1);
        }

        //Then again, if the connection is successful, a message is displayed that the server is listening for input.
        System.out.println("Connection successful");
        System.out.println("Listening for input ...");

        //The methods getInputStream and getOutputStream of the Socket is used to get references to stream associated
        // with the socket.
        PrintWriter output = new PrintWriter(link.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(link.getInputStream()));

        //Then using the BufferedReader and the PrintWriter objects, messages are delivered and received.
        //The Vigenere Cipher is used here.
        //A random keystring is generated and a plaintext is set up by the client.
        //Then using the ASCII, a ciphertext is generated.
        //The program ends if the client types "Bye".
        String inputLine;
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        int lengthOfString = 5;
        for (int i = 0; i < lengthOfString; i++) {
            int index = random.nextInt(letters.length());

            char randomCharacter = letters.charAt(index);

            stringBuilder.append(randomCharacter);
        }
        String randomKeyString = stringBuilder.toString();

        boolean encryptMode = false;
        while ((inputLine = input.readLine())!=null) {

            if (!encryptMode) {
                System.out.println("Message from the client: " + inputLine);
                if (inputLine.equals("Please send the Keystring")) {
                    output.println("The Keystring is: " + randomKeyString);
                    encryptMode = true;
                } else {
                    output.println(inputLine);
                }

            } else {
                output.println(inputLine);
                System.out.println("Plaintext: " + inputLine);
                System.out.println("Keystring: " + randomKeyString);
                System.out.println("Ciphertext: " + vigenereCipherText(inputLine, keyStringExpanding(inputLine, randomKeyString)));
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

    /**
     * This method is going to take into account the message typed by the client and then do the
     * calculation for the searching.
     * @param message typed by the client
     * @param keyString typed by the client
     * @return the message
     */
    private static String keyStringExpanding(String message, String keyString) {
        String result = "";
        int number = 0;
        while (number < message.length()) {
            result += String.valueOf(keyString.charAt(number % keyString.length()));
            number++;
        }
        return result;
    }

    /**
     * This method is going to take into account the message and then display the Ciphertext.
     * @param plainText typed by the client
     * @param keyString searched within the 26 letters
     * @return the Ciphertext
     */
    public static String vigenereCipherText(String plainText, String keyString) {
        String result = "";
        String keyExpand = keyStringExpanding(plainText, keyString);
        for (int i = 0; i < plainText.length(); i++) {
            int search = (plainText.charAt(i) + keyExpand.charAt(i)) % 26;
            result += String.valueOf((char)(search + 65));
        }
        return result;
    }
}
