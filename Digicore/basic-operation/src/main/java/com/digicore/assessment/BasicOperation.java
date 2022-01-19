package src.main.java.com.digicore.assessment;

public class BasicOperation {
    public static void main(String[] args){
        System.out.println("input '2','2','+'");
        System.out.println("output "+getresult("2","2","+"));
    }
    public static int getresult(String num1, String num2, String operation){
        switch (operation){
            case "+":
               return stringEquivalence(num1)+stringEquivalence(num2);
            case "-":
                return stringEquivalence(num1)-stringEquivalence(num2);
            case "*":
                return stringEquivalence(num1)*stringEquivalence(num2);
            case "/":
                return stringEquivalence(num1)/stringEquivalence(num2);
            default:
                return 0;
        }
    }
    private static int stringEquivalence(String numStr) {
        char[] ch = numStr.toCharArray();
        int sum = 0;
        int zeroAscii = '0';
        for (char c:ch) {
            sum = (sum*10)+((int) c -zeroAscii);
        }
        return sum;
    }
}
