public class ReverseSolution {
    public static void main(String[] args) {

        System.out.println(reverse2(123));
    }

    public static int reverse(int x) {

        boolean flag = false;
        if (x < 0) {
            x = -x;
            flag = true;
        }
        String s = x + "";
        char[] c = s.toCharArray();
        String r = "";
        for (int i = c.length - 1; i >= 0; i--)
            r += c[i];

        while (r.startsWith("0"))
            r = r.substring(1, r.length());

        if (flag)
            r = "-" + r;
        try {
            return Integer.parseInt(r);
        } catch (Exception e) {
            return 0;
        }
    }
    public static int reverse2(int x) {
        int r = 0;
        while (x != 0) {
            int temp = r * 10 + x % 10;
            if ((temp - x % 10) / 10 != r) return 0;
            r = temp;
            x /= 10;
        }
        return r;
    }

}
