public class LongestPalindromeSolution {

    public static void main(String args[]) {
        System.out.println(longestPalindrome("babad"));
    }

    public static String longestPalindrome(String s) {

        char[] t = new char[(s.length() << 1) + 1];
        t[0] = '#';
        char[] c = s.toCharArray();

        for (int i = 0; i < s.length(); i++) {
            t[(i << 1) + 1] = c[i];
            t[(i << 1) + 2] = '#';
        }

        int[] p = new int[t.length];

        int d = 0, max = 0, len = 0, idx = 0;

        for (int i = 1; i < t.length; i ++) {

            p[i] = max > i ? Math.min(p[(d << 1) - i], max - i) : 1;
            while ((i - p[i]) >= 0
                    && (i + p[i]) < t.length
                    && t[i + p[i]] == t[i - p[i]])
                p[i]++;

            if (p[i] + i > max) {
                max = p[i] + i;
                d = i;
            }
            if (p[i] > len) {
                len = p[i];
                idx = i;
            }
            // if ((i + len) > t.length) break;
        }
        System.out.println(idx + ", " + len);
        int start = (idx - len + 1) >> 1;

        return s.substring(start , start + len - 1);
    }
}
