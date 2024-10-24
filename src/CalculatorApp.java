package src;

import java.util.Scanner;

public class CalculatorApp {

    public static void main(String[] args) {

        System.out.println("Welcome");
        // Create scanner object
        Scanner scanner = new Scanner(System.in);
        boolean isClosed = false;

        float $list = 0.0f;
        String list = "$list";
        float integer1 = 0.0f;
        float integer2 = 0.0f;

        while (!isClosed) {

            // input separated by space (eg. 1 + 2)
            System.out.print(">");

            String input = scanner.nextLine();
            String[] inputList = input.split(" ");

            // check if $list was used as one of the numerals
            if (inputList[0].equals(list) && inputList[2].equals(list)) {
                integer1 = $list;
                integer2 = $list;
            } else if (inputList[0].equals("$list")) {
                integer1 = $list;
                integer2 = Float.parseFloat(inputList[2]);

            } else if (inputList[2].equals("$list")) {
                integer1 = Float.parseFloat(inputList[0]);
                integer2 = $list;
            } else {
                integer1 = Float.parseFloat(inputList[0]);
                integer2 = Float.parseFloat(inputList[2]);
            }

            // get the operand ("+" , "-" , "/" , "*")
            String operand = inputList[1];

            // swtich cases based on the operand entered:
            switch (operand) {

                case "+":
                    $list = integer1 + integer2;
                    break;

                case "-":
                    $list = integer1 - integer2;
                    break;

                case "*":
                    $list = integer1 * integer2;
                    break;

                case "/":
                    $list = integer1 / integer2;
                    break;

                default:
                    System.out.println("No such operand.");
                    return;
            }

            // print results of the mathematic operation
            System.out.println($list);

            // exit program if enter quit
            if (input.equals("quit")) {
                isClosed = true;
                System.out.println("Goodbye!");
            }

        }
    }
}