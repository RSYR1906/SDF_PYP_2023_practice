import java.util.*;
import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(1200);
        System.out.println("Waiting for connection...");

        Socket conn = server.accept();
        System.out.println("Connection established!");

        // sending information back to the client, separated by a new line
        Random rand = new Random();
        String fileName = "productlist.txt";
        String line = "";
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> emptyList = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(fileName));

        while ((line = br.readLine()) != null) {
            line = line.trim().toLowerCase();
            // String[] list = line.split(",");
            // String title = list[0];
            // String price = list[1];
            // String range = list[2];
            list.add(line);
        }
        br.close();
        // generate a unique request ID
        int uniqueID = rand.nextInt(1000, 9999);
        // randomly generated item count
        int itemCount = rand.nextInt(5);

        // given budget
        int budget = 10;
        // randomly generated list of products
        for (int i = 0; i < itemCount; i++) {
            emptyList.add(list.get(i));
        }
        // prod start (description of a product)
        // in between are the title, price and rating
        // prod end (end of description of a product)

        // eg. prod start
        // > Apple
        // > $1.50
        // > 5.0
        // prod end

        BufferedOutputStream bos = new BufferedOutputStream(conn.getOutputStream());

        String[] messages = { "Unique ID: " + uniqueID + "\n", "item count: " + itemCount + "\n" + //
                "list of products: " + emptyList + "\n" };

        for (String message : messages) {
            bos.write((message).getBytes());
        }

        bos.flush();
    }
}
