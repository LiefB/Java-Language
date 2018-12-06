package JavaSerializable.TransferOnNet;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketClass {

    public static void openObjectServer() throws IOException {

        ServerSocket svrsock = null;
        try {
            svrsock = new ServerSocket(9999);
            System.out.println("Wait clients connect on port:9999...");
            while (true) {
                final Socket clisock = svrsock.accept();

                new Runnable() {

                    public void run() {
                        try {
                            OutputStream os = clisock.getOutputStream();
                            os.write("Welcome connect server:127.0.0.1 on port:9999...".getBytes());

                            InputStream is = clisock.getInputStream();
                            ObjectInputStream ois = new ObjectInputStream(is);
                            Object object = ois.readObject();
                            System.out.println(object.toString());

                            //close socket
                            clisock.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (clisock != null)
                                try {
                                    clisock.close();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                        }
                    }
                }.run();

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            System.out.println("Server close connection！");
            try {
                if (svrsock != null)
                    svrsock.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        openObjectServer();
    }

}
