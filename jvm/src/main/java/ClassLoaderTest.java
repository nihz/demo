public class ClassLoaderTest {

    public static void main(String[] args) {

        System.out.println(System.getProperty("sun.boot.class.path"));

        ClassLoader cl = Test.class.getClassLoader();
        System.out.println(cl.toString());
        System.out.println(cl.getParent().toString());

        cl = String.class.getClassLoader();

        System.out.println(cl.toString());
    }
}


class Test {

}
