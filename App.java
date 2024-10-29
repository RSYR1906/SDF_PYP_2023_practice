import java.util.ArrayList;

public class App {

    public static void main(String[] args) {

        Integer[] list = { 5, 6, 8, 9, 20, 30, 1, 2, 12 };

        int largestNumber = findOptimumNumberRecursive(list, 0, list.length - 1);

        System.out.println("Largest number: " + largestNumber);

        // System.out.println("Printing in descending order:");
        // printFibonacci(10); // printing in descending order

        // System.out.println("Printing in ascending order:");
        // printFibonacciReversed(10);
    }

    // public static int factorial(int n) {
    // if (n == 0) {
    // return 1;
    // }
    // return n * factorial(n - 1);
    // }

    public static void printFibonacci(int n) {
        if (n == 5) {
            return;
        }
        System.out.println(n);
        printFibonacci(n - 1);
    }

    public static void printFibonacciReversed(int m) {
        if (m == 5) {
            return;
        }
        printFibonacciReversed(m - 1);
        System.out.println(m);
    }

    public static int findOptimumNumberRecursive(Integer[] listOfNumbers, int start, int end) {

        // stop recursion if reach base case
        // base case: compare till only one number left
        if (start == end) {
            return listOfNumbers[start];
        }

        // recursive call
        int mid = (start + end)/2;
        // compare the odd index with an even index. (this will be a pair)
        int leftMax = findOptimumNumberRecursive(listOfNumbers, start, mid);
        int rightMax = findOptimumNumberRecursive(listOfNumbers, mid + 1, end);
        // find the largest number
        return Math.max(leftMax,rightMax);

    }
}
