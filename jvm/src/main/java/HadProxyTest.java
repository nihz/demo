public class HadProxyTest {


    public static void main(String[] args) {

        Had h = (Had) new HadProxy().bind(new Had());
        h.sayHello();
    }
}
