public class IsPalindromeSolution {

    public static void main(String[] args) {
        System.out.println(isPalindrome2(10));
    }

    private static boolean isPalindrome (int x) {

        if (x < 0) return false;
        char[] c = (x + "").toCharArray();
        int i = 0, j = c.length - 1;
        while (i < j) {
            if (c[++i] != c[--j]) return false;
        }
        return true;
    }

    private static boolean isPalindrome2 (int x) {

        if (x < 0 || (x!=0 && x%10==0)) return false;
        int s = 0;
        while (x > s) {
            s = s * 10 + x % 10;
            x = x / 10;
        }
        return (x == s || s / 10 == x);
    }
}
