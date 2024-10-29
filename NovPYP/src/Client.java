import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException, ClassNotFoundException {
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

        for (String[] product : list) {
            System.out.println(Arrays.toString(product));
        }
        System.out.println("List of Products (end)\n");

        // Sort the list by rating and price in descending order
        list.sort((a, b) -> {
            int ratingCompare = Integer.compare(Integer.parseInt(b[2]), Integer.parseInt(a[2]));
            if (ratingCompare != 0) {
                return ratingCompare;
            }
            return Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1]));
        });

        ArrayList<String[]> selectedProducts = new ArrayList<>();
        int totalPrice = 0;
        int remainingBudget = budget;

        // Select products within the budget
        for (String[] product : list) {
            int price = Integer.parseInt(product[1]);
            if (remainingBudget >= price) {
                selectedProducts.add(product);
                totalPrice += price;
                remainingBudget -= price;
            }
        }

        // Display selected products and remaining budget
        System.out.println("Selected Products:");
        for (String[] product : selectedProducts) {
            System.out.println(Arrays.toString(product));
        }
        System.out.println("Total Price: " + totalPrice);
        System.out.println("Remaining Budget: " + remainingBudget);

        // Sending data back to the server
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write("Request ID: " + requestIDFromServer + "\n");
        writer.write("Name: Ryan\n");
        writer.write("Email: abc@gmail.com\n");

        for (String[] product : selectedProducts) {
            writer.write(product[0] + "," + product[1] + "," + product[2] + "\n");
        }

        writer.write("Total cost: " + totalPrice + "\n");
        writer.write("Remaining budget: " + remainingBudget + "\n");
        writer.flush();

        // Close resources
        ois.close();
        writer.close();
        socket.close();
    }
}