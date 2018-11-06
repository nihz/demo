public class AddTwoNumbersSolution {

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode c = new ListNode(0);
        ListNode next = c;
        int carry = 0;
        while (l1 != null || l2 != null || carry == 1) {
            int val = 0;
            if (l1 == null && l2 == null) {
                val = carry;
            } else if (l1 == null) {
                val = l2.val + carry;
                l2 = l2.next;
            } else if (l2 == null) {
                val = l1.val + carry;
                l1 = l1.next;
            } else {
                val = l1.val + l2.val + carry;
                l2 = l2.next;
                l1 = l1.next;
            }
            if (val > 9) {
                val %= 10;
                carry = 1;
            } else carry = 0;

            next.next = new ListNode(val);
            next = next.next;
        }
        return c.next;
    }

    public static void main(String[] args) {

        ListNode l1 = new ListNode(1);
        //l1.next = new ListNode(4);
        //l1.next.next = new ListNode(3);

        ListNode l2 = new ListNode(9);
        l2.next = new ListNode(9);
        //l2.next.next = new ListNode(4);

        ListNode c = addTwoNumbers(l1, l2);

        c.toString();
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    public String toString() {

        ListNode t = this;
        while (t != null) {
            System.out.println(t.val);
            t = t.next;
        }
        return null;
    }
}


