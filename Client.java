import java.util.*;
import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws UnknownHostException, IOException {

        Socket socket = null;

        // If no parameter is given then the program should connect to the server
        // running on localhost listening on port 3000; for example
        // java -cp c l a s s e s your package.Main
        if (args.length == 0) {
            socket = new Socket("localhost", 3000);
        }

        // If 1 parameter is given, your program will use that as the port number to
        // connect to localhost; for example
        // j a v a - c pc l a s s e s your .package.Main 5000
        // connects to the server running on localhost listening on port 5000.

        if (args.length == 1) {
            int portNumber = Integer.parseInt(args[0]);
            socket = new Socket("localhost", portNumber);
        }

        // If 2 parameters are provided, your program will use the first parameter
        // as the server name and the second as the port to connect to; for
        // example java - c p c l a s s e s your.package.Main myserver.com 8080
        // connects to the server running on myserver. com listening on port
        // 8080.
        if (args.length == 2) {
            String portName = args[0];
            int portNumber = Integer.parseInt(args[1]);
            socket = new Socket(portName, portNumber);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String message;

        while ((message = br.readLine()) != null) {
            System.out.println(message);
        }

    }
}
