package com.nee.demo.edu.pattern.single.test;

import com.nee.demo.edu.pattern.single.hungry.HungrySingle;
import com.nee.demo.edu.pattern.single.seriable.Seriable;

import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class ThreadSafeTest {

    public static void main(String args[]) {
//        int count = 100;
//        final CountDownLatch latch = new CountDownLatch(100);
//
//        final Set<HungrySingle> syncSet = Collections.synchronizedSet(new HashSet<HungrySingle>());
//
//        for (int i = 0; i < count; i++) {
//            new Thread(new Runnable() {
//                public void run() {
//                    System.out.println(System.currentTimeMillis() + ", " + HungrySingle.getInstance());
//                    latch.countDown();
//                }
//            }).start();
//        }
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Seriable s1 = null;
        Seriable s2 = Seriable.getInstance();

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream("Seriable.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(s2);
            oos.flush();oos.close();

            FileInputStream fis = new FileInputStream("Seriable.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            s1 = (Seriable)ois.readObject();
            System.out.println(s1 == s2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
