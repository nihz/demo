package com.nee.demo.edu.pattern.prototype.deep;

import java.io.*;

public class QiTianDaSheng extends Monkey implements Cloneable, Serializable {

    public JinGuBang jinGuBang;

    public QiTianDaSheng() {
        jinGuBang = new JinGuBang();
    }

    public Object clone() throws CloneNotSupportedException {
        return deepClone();
    }

    public Object deepClone() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);

            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String args[]) throws CloneNotSupportedException {
        QiTianDaSheng qiTianDaSheng = new QiTianDaSheng();

        QiTianDaSheng clone = (QiTianDaSheng) qiTianDaSheng.clone();

        System.out.println(clone.jinGuBang);
        System.out.println(qiTianDaSheng.jinGuBang);
        System.out.println(clone.jinGuBang == qiTianDaSheng.jinGuBang);
    }

}
