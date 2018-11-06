import java.util.UUID;

public class FindMedianSortedArraysSolution {

    public static void main(String[] args) {

        int[] nums1 = new int[]{1};
        int[] nums2 = new int[]{2,3};
        System.out.println(findMedianSortedArrays(nums1, nums2));
    }


    public static double findMedianSortedArray2(int[] nums1, int[] nums2) {
        int length = nums1.length + nums2.length;
        int[] c = new int[length];
        for (int i = 0; i < nums1.length; ) {
            for (int j = 0; j < nums2.length; ) {
                int val;
                if (i >= nums1.length && j < nums2.length) {
                    val = nums2[j];
                    j++;
                } else if (i < nums1.length && j >= nums2.length) {

                    val = nums1[i];
                    i++;
                } else if (i >= nums1.length && j >= nums2.length)
                    break;
                else if (nums1[i] < nums2[j]) {
                    val = nums1[i];
                    i++;
                } else {
                    val = nums2[j];
                    j++;
                }
                c[i + j - 1] = val;
            }
        }
        if (length % 2 == 0) {
            return (c[length / 2] + c[length / 2 + 1]) / 2;
        } else {
            return c[length / 2 + 1];
        }
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length)
            return findMedianSortedArrays(nums2, nums1);
        int m = nums1.length, n = nums2.length;
        if (m == 0) return Double.valueOf(nums2[(n - 1) >> 1] + nums2[n >> 1] ) / 2;
        if (n == 0) return Double.valueOf(nums1[(m - 1) >> 1] + nums1[(m) >> 1] ) / 2;

        int c1, c2, low = 0, l1 = 0, l2 = 0, r1 = 0, r2 = 0, high = m << 1;

        while (low <= high) {

            c1 = (low + high) >> 1;
            c2 = n + m - c1;
            l1 = c1 == 0 ? Integer.MIN_VALUE : nums1[(c1 - 1) >> 1];
            r1 = c1 == m << 1? Integer.MAX_VALUE : nums1[c1 >> 1];
            l2 = c2 == 0 ? Integer.MIN_VALUE : nums2[(c2 - 1) >> 1];
            r2 = c2 == n << 1? Integer.MAX_VALUE : nums2[c2 >> 1];

            if (l1 > r2) high = c1 - 1;
            else if (l2 > r1) low = c1 + 1;
            else break;

        }
        // int l1 = nums1[c1] > nums2[c2]? nums2[c2] : nums1[c1];
        // System.out.println(l1);
        System.out.println(l1);
        System.out.println(l2);
        System.out.println(r1);
        System.out.println(r2);

        return Double.valueOf((Math.max(l1, l2) + Math.min(r1, r2))) / 2;

    }
}
