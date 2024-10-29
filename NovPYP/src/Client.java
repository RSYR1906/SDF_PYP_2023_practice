import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        try {
            Socket socket;

            // Establish connection based on arguments or default
            if (args.length == 0) {
                socket = new Socket("localhost", 3000);
            } else if (args.length == 1) {
                int portNumber = Integer.parseInt(args[0]);
                socket = new Socket("localhost", portNumber);
            } else {
                String serverName = args[0];
                int portNumber = Integer.parseInt(args[1]);
                socket = new Socket(serverName, portNumber);
            }

            // Receive data from the server
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            int requestIDFromServer = ois.readInt();
            int itemCount = ois.readInt();
            int budget = ois.readInt();
            ArrayList<String[]> list = (ArrayList<String[]>) ois.readObject();

            System.out.println("SENT FROM SERVER: \n" +
                    "RequestID: " + requestIDFromServer +
                    "\nBudget: " + budget +
                    "\nItem Count: " + itemCount +
                    "\nList of Products (start)");

            // Print original product list
            for (String[] product : list) {
                System.out.println(Arrays.toString(product));
            }
            System.out.println("List of Products (end)\n");

            // Sort the list by rating (index 2) and then by price (index 1) in descending order
            list.sort((a, b) -> {
                int ratingCompare = Integer.compare(Integer.parseInt(b[2]), Integer.parseInt(a[2]));
                if (ratingCompare != 0) {
                    return ratingCompare;
                }
                return Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1]));
            });

            // Select products that fit within the budget
            ArrayList<String[]> selectedProducts = new ArrayList<>();
            int remainingBudget = budget;
            int totalPrice = 0;

            for (String[] product : list) {
                int price = Integer.parseInt(product[1]);
                if (remainingBudget >= price) {
                    selectedProducts.add(product);
                    remainingBudget -= price;
                    totalPrice += price; // Update total price
                }
            }

            // Print selected products
            System.out.println("Selected Products within Budget:");
            for (String[] product : selectedProducts) {
                System.out.println(Arrays.toString(product));
            }
            System.out.println("Remaining Budget: " + remainingBudget);

            // Get user input for name and email
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();

            // Prepare data to send back to server
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("requestID", requestIDFromServer);
            responseData.put("name", name);
            responseData.put("email", email);
            responseData.put("itemsBought", selectedProducts);
            responseData.put("totalPrice", totalPrice);
            responseData.put("remainingBudget", remainingBudget);

            // Send data back to the server
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(responseData);
            oos.flush();

            // Close resources
            ois.close();
            oos.close();
            socket.close();
            scanner.close(); // Close scanner
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}