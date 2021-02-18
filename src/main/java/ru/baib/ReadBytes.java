package ru.baib;

import java.io.*;

public class ReadBytes {
    public static void main(String[] args) {
        try (DataOutputStream bos = new DataOutputStream(new FileOutputStream("hhh"));
            DataInputStream dis = new DataInputStream(new FileInputStream("hhh"))) {
            byte[] bytes = {127, 120, 13, 17, 20, 20, 20, 20};
            for (int i = 0; i < bytes.length; i++) {
                bos.write(bytes[i]);
            }
            byte b;
            while (dis.available() > 0) {
                System.out.println(dis.readByte());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
