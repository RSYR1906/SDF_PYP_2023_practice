public class RecursivePractice {

    public static void main(String[] args) {

        // System.out.println(countX("xxx"));

        // System.out.println(countHi("hi"));

        // System.out.println(changeXY("xxhixx"));

        // System.out.println(changePi("xpix"));

        // System.out.println(noX("abc"));

        // System.out.println(allStar("abc"));

        // System.out.println(pairStar("abbc"));

        // System.out.println(endX("xxhixx"));

        // System.out.println(countPairs("axbx"));

        // System.out.println(countAbc("abc"));

        System.out.println(parenBit("x(hello)"));

    }

    public static int countX(String str) {
        if (str.isEmpty()) {
            return 0;
        }
        if (str.charAt(0) == 'x') {
            return 1 + countX(str.substring(1));
        } else {
            return countX(str.substring(1));
        }
    }

    public static int countHi(String string) {
        if (string.isEmpty() || string.length() <= 1) {
            return 0;
        } else if (string.charAt(0) == 'h' && string.charAt(1) == 'i') {
            return 1 + countHi(string.substring(2));
        } else {
            return countHi(string.substring(1));
        }
    }

    public static String changeXY(String str) { // changing all the 'x' in the string to 'y'

        // write the base case
        if (str.isEmpty()) {
            return "";
        }
        // recursive call
        char firstChar = str.charAt(0);

        if (firstChar == 'x') {
            return 'y' + changeXY(str.substring(1));
        } else {
            return firstChar + changeXY(str.substring(1));
        }

    }

    public static String changePi(String str) {

        // changePi("xpix") → "x3.14x"
        // changePi("pipi") → "3.143.14"
        // changePi("pip") → "3.14p"

        // base call
        if (str.length() < 2) {
            return str;
        }
        // recursive call
        if (str.startsWith("pi")) {
            return "3.14" + (changePi(str.substring(2)));
        } else {
            return str.charAt(0) + changePi(str.substring(1));
        }

    }

    public static String noX(String str) {

        // noX("xaxb") → "ab"
        // noX("abc") → "abc"
        // noX("xx") → ""

        // base case
        if (str.isEmpty()) {
            return "";
        }
        // recursive call
        if (str.charAt(0) == 'x') {
            return "" + noX(str.substring(1));
        } else {
            return str.charAt(0) + noX(str.substring(1));
        }
    }

    public static boolean array6(int[] nums, int index) {

        // array6([1, 6, 4], 0) → true
        // array6([1, 4], 0) → false
        // array6([6], 0) → true

        if (nums.length == index) {
            return false;
        }

        if (nums[index] == 6) {
            return true;
        }
        return array6(nums, index + 1);

    }

    public static int array11(int[] nums, int index) {
        // array11([1, 2, 11], 0) → 1
        // array11([11, 11], 0) → 2
        // array11([1, 2, 3, 4], 0) → 0
        int count = 0;
        if (nums.length == index) {
            return count;
        }

        if (nums[index] == 11) {
            return count++;
        }
        return array11(nums, index + 1);

    }

    public boolean array220(int[] nums, int index) {
        // array220([1, 2, 20], 0) → true
        // array220([3, 30], 0) → true
        // array220([3], 0) → false

        if (nums.length == index) {
            return false;
        }

        if (nums[index] * 10 == nums[index + 1]) {
            return true;
        }
        return array220(nums, index + 1);
    }

    public static String allStar(String str) {
        // allStar("hello") → "h*e*l*l*o"
        // allStar("abc") → "a*b*c"
        // allStar("ab") → "a*b"

        if (str.length() <= 1) {
            return str;
        }

        return str.charAt(0) + "*" + allStar(str.substring(1));
    }

    public static String pairStar(String str) {
        // pairStar("hello") → "hel*lo"
        // pairStar("xxyy") → "x*xy*y"
        // pairStar("aaaa") → "a*a*a*a"

        if (str.length() <= 1) {
            return str;
        }

        if (str.charAt(0) == str.charAt(1)) {
            return str.charAt(0) + "*" + pairStar(str.substring(2));
        } else {
            return str.charAt(0) + pairStar(str.substring(1));
        }
    }

    public static String endX(String str) {
        // endX("xxre") → "rexx"
        // endX("xxhixx") → "hixxxx"
        // endX("xhixhix") → "hihixxx"
        if (str.length() <= 1) {
            return str;
        }

        if (str.charAt(0) == 'x') {
            return endX(str.substring(1)) + str.charAt(0);
        } else {
            return str.charAt(0) + endX(str.substring(1));
        }
    }

    public static int countPairs(String str) {
        // countPairs("axa") → 1
        // countPairs("axax") → 2
        // countPairs("axbx") → 1
        if (str.length() < 3) {
            return 0;
        }

        if (str.charAt(0) == str.charAt(2)) {
            return 1 + countPairs(str.substring(1));
        }
        return countPairs(str.substring(1));
    }

    public static int countAbc(String str) {
        // countAbc("abc") → 1
        // countAbc("abcxxabc") → 2
        // countAbc("abaxxaba") → 2

        if (str.length() < 3) {
            return 0;
        }

        if (str.charAt(0) == 'a' && str.charAt(1) == 'b' && (str.charAt(2) == 'c' || str.charAt(2) == 'a')) {
            return 1 + countAbc(str.substring(1));
        } else {
            return countAbc(str.substring(1));
        }
    }

    public int strDist(String str, String sub) {
        // strDist("catcowcat", "cat") → 9
        // strDist("catcowcat", "cow") → 3
        // strDist("cccatcowcatxx", "cat") → 9

        if (str.length() < sub.length()) {
            return 0;
        }

        if (str.startsWith(sub) && str.endsWith(sub)) {
            return str.length();
        }

        if (str.startsWith(sub) == false) {
            return strDist(str.substring(1), sub);
        } else {
            return strDist(str.substring(0, str.length() - 1), sub);
        }

    }

    public static boolean strCopies(String str, String sub, int n) {
        if (n == 0) {
            return true;
        }

        if (str.length() < sub.length()) {
            return false;
        }

        if (str.startsWith(sub)) {
            return strCopies(str.substring(1), sub, n - 1);
        } else {
            return strCopies(str.substring(1), sub, n);
        }
    }

    public static String parenBit(String str) {
        // parenBit("xyz(abc)123") → "(abc)"
        // parenBit("x(hello)") → "(hello)"
        // parenBit("(xy)1") → "(xy)"

        if (str.length() < 3){
            return "()";
        }

        if (str.startsWith("(") && str.endsWith(")")){
            return str;
        }
        if (str.startsWith("(")){
            return parenBit(str.substring(0,str.length()-1));
        }
        else{
            return parenBit(str.substring(1));
        }
    }

}
