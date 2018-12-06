package JavaSerializable.TransferOnNet;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSocketClass {

    public static void main(String[] args) {
        Socket socket = null;

        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            InputStream is = socket.getInputStream();
            byte[] buf = new byte[64*1024];
            int len = 0;
            if ((len = is.read(buf)) != -1) {
                System.out.println(new String(buf, 0, len));
            }

            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);

            Student student = new Student();
            student.setAge(22);
            student.setName("liefb");
            student.setScore(99);

            oos.writeObject(student);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}