package B4_retakePYP;

import java.io.*;

public class MainTask1 {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Input <CSV file> <template file>");
            System.exit(-1);
        }

        String csv = args[0];
        String txt = args[1];

        File inputFile = new File(csv);
        File template = new File(txt);

        if (!inputFile.exists() || !template.exists()) {
            System.out.println("file does not exist");
            System.exit(-1);
        }



        FileReader fr = new FileReader(inputFile);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();    // title


        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            String firstName = data[0].trim();
            String lastName = data[1].trim();
            String address = data[2].trim();
            Integer years = Integer.parseInt(data[3].trim());
            //System.out.println(firstName + " " + lastName + " " + address + " " + years);

            String mailLine = "";

            FileReader fr2 = new FileReader(template);
            BufferedReader br2 = new BufferedReader(fr2);

            while ((mailLine = br2.readLine()) != null) {
                if (mailLine.contains("__address__")) {
                    mailLine = mailLine.replace("__address__", address);
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

    }
}
