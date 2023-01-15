/**
 * Name: Abhishek Biswas Deep
 */

//This program is going to communicate with the server.
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class VigenereCipherClient {
    public static void main(String[] args) throws IOException{

        //Initializing a socket object, PrintWriter object and a BufferedReader object called link, output and
        // input respectively.
        Socket link = null;
        PrintWriter output = null;
        BufferedReader input = null;

        //The link object using the local machine IP address is created.
        //The port number is created.
        try{
            link = new Socket("127.0.0.1", 50000);

            //This is an output stream from the socket.
            output = new PrintWriter(link.getOutputStream(), true);

            //This is used to get an input stream from the socket.
            input = new BufferedReader(new InputStreamReader(link.getInputStream()));
        }

        //This is used if the above failed.
        catch(UnknownHostException e)
        {
            System.out.println("Unknown Host");
            System.exit(1);
        }
        catch (IOException e){
            System.out.println("Cannot connect to host");
            System.exit(1);
        }

        //The client is now ready to send and receive data.
        //The BufferedReader object is going to receive messages.
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        boolean encryptMode = false;
        String usrInput;
        while ((usrInput = stdIn.readLine())!=null){
            output.println(usrInput);

            if (!encryptMode) {
                if (usrInput.equals("Please send the Keystring")) {
                    encryptMode = true;
                }
                System.out.println("Echo from Server: " + input.readLine());
            }

        }

        //The connections are closed.
        output.close();
        input.close();
        stdIn.close();
        link.close();
    }
}
