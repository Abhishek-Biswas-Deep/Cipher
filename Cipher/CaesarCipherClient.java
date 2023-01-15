/**
 * Name: Abhishek Biswas Deep
 */

//This program is used to communicate with the server.
import java.io.*;
import java.net.*;

public class CaesarCipherClient {
    public static void main(String[] args) throws IOException{

        //Initializing a socket object called link, a PrintWriter object called output and a BufferedReader object
        // called input.
        Socket link = null;
        PrintWriter output = null;
        BufferedReader input = null;

        //The client is responsible for creating the link object using the local machine IP address and the port number
        // that the server is looking for.
        //As both the client and the server programs are tested on the same machine, 127.0.0.1 is used as the
        // destination IP address.
        try{
            link = new Socket("127.0.0.1", 50000);

            //The output object is an output stream from the socket.
            output = new PrintWriter(link.getOutputStream(), true);

            //The input object also gets an input stream from the socket.
            input = new BufferedReader(new InputStreamReader(link.getInputStream()));
        }

        //The catch exception works if the above fails.
        catch(UnknownHostException e)
        {
            System.out.println("Unknown Host");
            System.exit(1);
        }
        catch (IOException e){
            System.out.println("Cannot connect to host");
            System.exit(1);
        }

        //The BufferedReader object at the client side will receive messages sent by the PrintWriter object at the
        // server side and vice versa.
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        boolean encryptMode = false;
        String usrInput;
        while ((usrInput = stdIn.readLine())!=null){
            output.println(usrInput);

            if (!encryptMode) {
                if (usrInput.equals("Please send the key")) {
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
