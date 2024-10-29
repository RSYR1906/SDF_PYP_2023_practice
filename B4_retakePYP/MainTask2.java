package B4_retakePYP;

import java.io.*;
import java.net.*;
import java.util.*;

public class MainTask2 {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Input <port> <docroot>");
            System.exit(-1);
        }

        Integer port = Integer.parseInt(args[0]);
        String docroot = args[1];

        ServerSocket server = new ServerSocket(port);

        while (true) {
            Socket socket = server.accept();

            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            while (true) {
                String[] command = br.readLine().split(" ");
                System.out.println(Arrays.toString(command));
                String resource = command[1].substring(1);
                String version = command[2];

                if (!resource.endsWith("html")) {
                    resource += "index.html";
                }
                System.out.println(docroot + "/" + resource);

                File file = new File(docroot + "/" + resource);
                if (file.exists()) {
                    bw.write("%s 200 OK\r\n".formatted(version));
                    bw.write("Content-Type: text/html\r\n");
                    bw.write("\r\n");
                    bw.flush();

                    FileReader fr = new FileReader(file);
                    BufferedReader br2 = new BufferedReader(fr);
                    String line = "";

                    while ((line = br2.readLine()) != null) {
                        bw.write(line);
                    }
                    bw.flush();
                    System.out.println("success");
                } else {
                    bw.write("%s 404 Not Found".formatted(version));
                    bw.write("Content-Type: text/html\r\n");
                    bw.write("\r\n");
                    bw.write("Resource %s not found".formatted(resource));
                    bw.flush();
                    System.out.println("fail");
                }
                break;
            }
        }
    }
}
