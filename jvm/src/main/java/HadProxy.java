import org.objectweb.asm.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.*;

public class HadProxy extends Had {

    public Object bind(Object realObject) {
        //给代理的真实对象赋值

        try {
            return this.newProxyInstance(new HadClassLoader(), realObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object invoke(Method method) throws Throwable {
        System.out.println(method.getName());
        method.invoke(this);
        return null;
    }

    public void sayHello() {
        System.out.println("had proxy say: hello");
    }

    public Object newProxyInstance(HadClassLoader classLoader, Object obj) throws IllegalAccessException, InstantiationException {

        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;
        AnnotationVisitor av0;

        cw.visit(49,
                ACC_PUBLIC + ACC_SUPER,
                "$Proxy0",
                null,
                "Had",
                null);

        cw.visitSource("$Proxy0.java", null);

        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL,
                    "Had",
                    "<init>",
                    "()V");
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 2);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC,
                    "sayHello",
                    "()V",
                    null,
                    null);
            mv.visitFieldInsn(GETSTATIC,
                "java/lang/System",
                "out",
                "Ljava/io/PrintStream;");
            mv.visitLdcInsn("hello");
            mv.visitMethodInsn(INVOKEVIRTUAL,
                    "java/io/PrintStream",
                    "println",
                    "(Ljava/lang/String;)V");
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 2);
            mv.visitEnd();
        }
        cw.visitEnd();

        byte[] bytes = cw.toByteArray();
        File f = new File("/Users/heikki/git/demo/Proxy.class");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        ClassReader cr = null;
//        try {
//            cr = new ClassReader("Had");
//            ClassWriter cw2 = new ClassWriter(ClassWriter.COMPUTE_MAXS);
//            ClassAdapter classAdapter = new HadProxyClassAdapter(cw2);
//            cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
//            byte[] data = cw2.toByteArray();
//            File file = new File("Had.class");
//            FileOutputStream fout = new FileOutputStream(file);
//            fout.write(data);
//            fout.close();
//
//            Class c = classLoader.findClass("Had", data);
//            return c.newInstance();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Class c = classLoader.findClass("$Proxy0", bytes);
        return c.newInstance();
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}

class HadProxyClassAdapter extends ClassAdapter {

    public HadProxyClassAdapter(ClassVisitor cv) {
        super(cv);
    }

    public MethodVisitor visitMethod(final int access, final String name,
                                     final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        MethodVisitor wrappedMv = mv;
        if (mv != null) {
            if (name.equals("sayHello")) {
                wrappedMv = new HadProxyMethodAdapter(mv);
            }
        }
        return wrappedMv;
    }
}

class HadProxyMethodAdapter extends MethodAdapter {
    public HadProxyMethodAdapter(MethodVisitor mv) {
        super(mv);
    }

    public void visitCode() {
        visitMethodInsn(Opcodes.INVOKESTATIC, "HadEnhancer",
                "sayHello", "()V");
    }
}
