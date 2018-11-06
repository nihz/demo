public class HadClassLoader extends ClassLoader {

    public Class<?> findClass(String name, byte[] bytes) {

        return defineClass(name, bytes, 0, bytes.length);
    }
}
