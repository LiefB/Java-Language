package JavaNIO_FileChannel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Client {

    public static void main(String[] args) {

        for (int i = 0; i < 3; i++) {
            // 模拟三个发端
            new Thread() {
                public void run() {
                    try {
                        SocketChannel socketChannel = SocketChannel.open();
                        socketChannel.socket().connect(new InetSocketAddress("127.0.0.1", 8888));
                        File file = new File("E:\\" + 11 + ".txt");
                        FileChannel fileChannel = new FileInputStream(file).getChannel();
                        ByteBuffer buffer = ByteBuffer.allocate(64*1024);
                        socketChannel.read(buffer);
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, buffer.limit(), Charset.forName("utf-8")));
                        buffer.clear();
                        int num = 0;
                        while ((num=fileChannel.read(buffer)) > 0) {
                            buffer.flip();
                            //System.out.println(new String(buffer.array(), 0, buffer.limit(), Charset.forName("utf-8")));
                            socketChannel.write(buffer);
                            buffer.clear();
                        }
                        if (num == -1) {
                            fileChannel.close();
                            socketChannel.shutdownOutput();
                        }
                        // 接受服务器
                        socketChannel.read(buffer);
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, buffer.limit(), Charset.forName("utf-8")));
                        buffer.clear();
                        socketChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                };
            }.start();

        }
        Thread.yield();
    }

}