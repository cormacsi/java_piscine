package ex00;

public class Program {
    public static int num = 345679;

    public static void main(String[] args) {
        int res = 0;
        while (num > 0) {
            res += (num % 10);
            num /= 10;
        }
        System.out.println(res);
    }
}
