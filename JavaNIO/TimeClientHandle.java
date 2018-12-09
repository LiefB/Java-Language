package JavaNIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class TimeClientHandle implements Runnable{
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;
    public TimeClientHandle(String host, int port){
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        try{
            //1.打开SocketChannel,用于创建客户端连接
            socketChannel = SocketChannel.open();
            //2.设置SocketChannel为非阻塞模式
            socketChannel.configureBlocking(false);
            //3.创建多路复用器（在Reactor线程中）
            selector = Selector.open();
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }//TimeClientHandle

    @Override
    public void run() {
        try{
            //4.socketChannel发起连接
            if(socketChannel.connect(new InetSocketAddress(host, port))){
                //5.如果直接连接成功，则注册到多路复用器上
                socketChannel.register(selector, SelectionKey.OP_READ);
                //6.发送请求消息，读应答
                byte[] req = "QUERY TIME ORDER".getBytes();
                ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
                writeBuffer.put(req);
                writeBuffer.flip();
                socketChannel.write(writeBuffer);
                if(!writeBuffer.hasRemaining())
                    System.out.println("Send order to server succeed.");
                writeBuffer.clear();
            }else
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }catch (IOException e){
            e.printStackTrace();
        }
        while (!stop){
            try{
                //7.多路复用器在run的无限循环体内轮询准备就绪的Key
                selector.select(1000);
                Set selectedKeys = selector.selectedKeys();
                Iterator it = selectedKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()){
                    key = (SelectionKey) it.next();
                    it.remove();
                    try{
                        if(key.isValid()){
                            //8.将连接成功的Channel注册到多路复用器上
                            //判断是否连接成功
                            SocketChannel sc = (SocketChannel) key.channel();
                            if(key.isConnectable()){
                                if(sc.finishConnect()){
                                    sc.register(selector, SelectionKey.OP_READ);
                                    //发送请求消息，读应答
                                    byte[] req = "QUERY TIME ORDER".getBytes();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
                                    writeBuffer.put(req);
                                    writeBuffer.flip();
                                    sc.write(writeBuffer);
                                    if(!writeBuffer.hasRemaining())
                                        System.out.println("Send order to server succeed.");
                                    writeBuffer.clear();
                                }else System.exit(1);//连接失败，进程退出
                            }
                            //监听读操作，读取服务器端写回的网络消息
                            if(key.isReadable()){
                                //9.读取信息到缓冲区
                                ByteBuffer readBuffer = ByteBuffer.allocate(64*1024);
                                int readBytes = sc.read(readBuffer);
                                if(readBytes > 0){
                                    readBuffer.flip();
                                    byte[] bytes =new byte[readBuffer.remaining()];
                                    readBuffer.get(bytes);
                                    String body =new String(bytes, "UTF-8");
                                    System.out.println("Client reveive a response: \"" + body + "\"");
                                    readBuffer.clear();
                                    //this.stop =true;
                                    //写应答
                                    byte[] bytes2 = "QUERY TIME ORDER".getBytes();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(bytes2.length);
                                    writeBuffer.put(bytes2);
                                    writeBuffer.flip();
                                    sc.write(writeBuffer);
                                    writeBuffer.clear();
                                }else if(readBytes < 0){
                                    //对端链路关闭
                                    key.cancel();
                                    sc.close();
                                }else ;//读到0字节，忽略
                            }
                        }
                    }catch (Exception e){
                        if(key != null){
                            key.cancel();
                            if(key.channel() != null)
                                key.channel().close();
                        }
                    }
                }//while(it.hasNext())
            }catch (Exception e){
                e.printStackTrace();
                System.exit(1);
            }
        }//while(!stop)
        //多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
        if(selector != null){
            try{
                selector.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }//run
}
