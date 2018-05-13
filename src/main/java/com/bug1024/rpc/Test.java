package com.bug1024.rpc;

import com.sun.tools.javac.Main;

import java.io.*;

/**
 * @author wangyu
 * @date 2018-04-29
 */
public class Test implements Serializable {
    private static final long serialVersionUID = -8657790107252018599L;

    public int num = 123;

    public static void main(String[] args) {
        try {
            //FileOutputStream fileOutputStream = new FileOutputStream("/Users/bug1024/Github/jeet-rpc/a.tmp");
            //ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            //Test test = new Test();
            //objectOutputStream.writeObject(test);
            //objectOutputStream.flush();
            //objectOutputStream.close();

            FileInputStream fileInputStream = new FileInputStream("/Users/bug1024/Github/jeet-rpc/a.tmp");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Test test2 = (Test) objectInputStream.readObject();
            System.out.println(test2.num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
