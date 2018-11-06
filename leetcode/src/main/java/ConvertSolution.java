public class ConvertSolution {

    public static void main(String[] args) {

        System.out.println(convert("AB", 1));
    }

    public static String convert(String s, int numRows) {
        if (numRows == 1) return s;

        char[] c = s.toCharArray();
        char[][] r = new char[numRows][s.length()];
        int j = 0, k = 0;
        boolean inc = true;

        for (int i = 0; i < c.length; i++) {

            if (inc) {
                r[j][k] = c[i];
                j++;
            } else {
                j--;
                k++;
                r[j][k] = c[i];
            }
            if (j == numRows) {
                inc = false;
                j--;
            } if (j == 0) {
                inc = true;
                j++;
            }
        }

        String rc = "";
        for (int i = 0; i < numRows; i++) {
            for (int t = 0; t < r[i].length; t++) {

                if (r[i][t] != '\u0000') {
                    rc += r[i][t];
                }
            }
        }
        return rc;
    }
}
