package B4_retakePYP;

import java.io.*;

public class Task1 {

    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.out.println("Invalid input. Please enter paths for both the csv file and txt file.");
            return;
        }

        String csvFilePath = args[0];
        String txtFilePath = args[1];

        FileReader fr = new FileReader(csvFilePath);
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine();

        while ((line = br.readLine()) != null) {
            String[] details = line.split(",");
            String firstName = details[0].trim();
            String lastName = details[1].trim();
            String Address = details[2].trim();
            int years = Integer.parseInt(details[3].trim());

            // System.out.println("Name: " + firstName + "," + "Address: " + Address);

            String mailLine = "";

            FileReader fr2 = new FileReader(txtFilePath);
            BufferedReader br2 = new BufferedReader(fr2);

            while ((mailLine = br2.readLine()) != null) {
                if (mailLine.contains("__address__")) {
                    mailLine = mailLine.replace("__address__", Address);
                    mailLine = mailLine.replaceAll("\\\\n", "\n");
                }
                if (mailLine.contains("__first_name__")) {
                    mailLine = mailLine.replace("__first_name__", firstName);
                }
                if (mailLine.contains("__years__")) {
                    mailLine = mailLine.replace("__years__", String.valueOf(years));
                }
                if (mailLine.contains("__last_name__")) {
                    mailLine = mailLine.replace("__last_name__", lastName);
                }
                System.out.println(mailLine);
            }
        }
        br.close();
    }
}
