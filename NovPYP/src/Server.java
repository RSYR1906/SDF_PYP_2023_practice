import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        String fileName = "files/productlist.txt";
        Random rand = new Random();
        ArrayList<String[]> list = new ArrayList<>();

        try (ServerSocket server = new ServerSocket(3000)) {
            System.out.println("Waiting for connection...");

            // Accept a connection
            Socket conn = server.accept();
            System.out.println("Connection established");

            // Read product data from file
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                // Split each line by commas
                String[] productDetails = line.split(",");
                if (productDetails.length == 3) {
                    list.add(productDetails); // Add only if it has exactly 3 elements
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
            try (ObjectOutputStream oos = new ObjectOutputStream(conn.getOutputStream())) {
                oos.writeInt(requestID);
                oos.writeInt(itemCount);
                oos.writeInt(budget);
                oos.writeObject(randomSubset);
                oos.flush();
            }

            // Receive response data from client
            try (ObjectInputStream ois = new ObjectInputStream(conn.getInputStream())) {
                Map<String, Object> responseData = (Map<String, Object>) ois.readObject();

                // Extract data
                int clientRequestID = (int) responseData.get("requestID");
                String name = (String) responseData.get("name");
                String email = (String) responseData.get("email");
                ArrayList<String[]> itemsBought = (ArrayList<String[]>) responseData.get("itemsBought");
                int totalPrice = (int) responseData.get("totalPrice");
                int remainingBudget = (int) responseData.get("remainingBudget");

                // Check budget status
                if (remainingBudget >= 0) {
                    System.out.println("Status: SUCCESS");
                } else {
                    System.out.println("Status: FAILED");
                }

                // Output the details
                System.out.println("Client Request ID: " + clientRequestID);
                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Items Bought: " + itemsBought);
                System.out.println("Total Price: " + totalPrice);
                System.out.println("Remaining Budget: " + remainingBudget);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}