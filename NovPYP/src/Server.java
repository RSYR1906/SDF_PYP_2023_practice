import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String fileName = "files/productlist.txt";
        Random rand = new Random();
        ArrayList<String[]> list = new ArrayList<>();

        ServerSocket server = new ServerSocket(3000);
        System.out.println("Waiting for connection...");

        // Accept a connection
        Socket conn = server.accept();
        System.out.println("Connection established");

        // Read product data from file
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = br.readLine()) != null) {
            String[] productDetails = line.split(",");
            if (productDetails.length == 3) {
                list.add(productDetails);
            }
        }
        br.close();

        int requestID = rand.nextInt(1000, 9999);
        int itemCount = Math.min(rand.nextInt(1, 6), list.size());
        int budget = 10;

        // Shuffle the list and create a random subset
        Collections.shuffle(list);
        ArrayList<String[]> randomSubset = new ArrayList<>(list.subList(0, Math.min(list.size(), itemCount)));

        // Send data to client
        ObjectOutputStream oos = new ObjectOutputStream(conn.getOutputStream());
        oos.writeInt(requestID);
        oos.writeInt(itemCount);
        oos.writeInt(budget);
        oos.writeObject(randomSubset);
        oos.flush();

        // Reading data sent back from the client
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        List<String[]> itemList = new ArrayList<>();
        double totalCost = 0;
        double remainingBudget = budget;

        System.out.println("Received from Client:");

        // Read Client Details
        String clientInfo;
        while ((clientInfo = reader.readLine()) != null) {
            if (clientInfo.startsWith("Request ID") || clientInfo.startsWith("Name") || clientInfo.startsWith("Email")) {
                System.out.println(clientInfo);
            } else if (clientInfo.startsWith("Total cost")) {
                totalCost = Double.parseDouble(clientInfo.split(": ")[1].trim());
            } else if (clientInfo.startsWith("Remaining budget")) {
                remainingBudget = Double.parseDouble(clientInfo.split(": ")[1].trim());
            } else {
                // Parse the product details sent by the client
                String[] productDetails = clientInfo.split(",");
                itemList.add(productDetails);
            }
        }

        // Display items bought and final budget details
        System.out.println("Items Bought:");
        for (String[] product : itemList) {
            System.out.println("Product Name: " + product[0] + ", Price: " + product[1] + ", Rating: " + product[2]);
        }

        System.out.println("Total Cost: " + totalCost);
        System.out.println("Remaining Budget: " + remainingBudget);

        // Check if budget was sufficient
        if (remainingBudget < 0) {
            System.out.println("Status: FAILED - Insufficient budget");
        } else {
            System.out.println("Status: SUCCESS - Purchase successful");
        }

        // Close connections
        reader.close();
        conn.close();
        server.close();
    }
}