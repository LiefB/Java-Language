package JavaNIO;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServerDemo {

    public static void main(String[] args) throws IOException{
        new Thread(new ReactorTask()).start();
    }

    public static class ReactorTask implements Runnable{
        private Selector selector;

        public ReactorTask() {
            try{
                //1.打开ServerSocketChannel，用于监听客户端的连接，它是所有客户端连接的父管道
                ServerSocketChannel acceptorSvr = ServerSocketChannel.open();
                //2.绑定监听端口
                acceptorSvr.socket().bind(new InetSocketAddress(InetAddress.getByName("localhost"), 9999));
                //3.设置连接为非阻塞模式
                acceptorSvr.configureBlocking(false);
                //4.创建Reactor线程，创建多路复用器并启动线程
                selector = Selector.open();
                //5.将ServerSocketChannel注册到Reactor线程的多路复用器Selector上，监听Accept事件
                SelectionKey key = acceptorSvr.register(selector, SelectionKey.OP_ACCEPT);
            } catch (UnknownHostException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }//ReactorTask

        @Override
        public void run() {
            //6.在run方法中的无限循环体内，轮询准备就绪的Key
            while (true){
                try{
                    selector.select(1000);
                    Set selectedKeys = selector.selectedKeys();
                    Iterator it = selectedKeys.iterator();
                    SelectionKey key = null;
                    while (it.hasNext()){
                        key = (SelectionKey) it.next();
                        it.remove();
                        try{
                            if (key.isValid()){
                                //处理新接入的请求消息
                                if(key.isAcceptable()){
                                    //7.多路复用器监听到有新的客户端接入，处理新的接入请求，完成TCP三次握手，建立物理链路
                                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                                    SocketChannel sc = ssc.accept();
                                    //8.设置客户端链路为非阻塞模式
                                    sc.configureBlocking(false);
                                    sc.socket().setReuseAddress(true);
                                    //9.将新接入的客户端连接注册到Reactor线程的多路复用器上，监听读操作，读取客户端发送的网络消息
                                    sc.register(selector, SelectionKey.OP_READ);
                                }
                                if(key.isReadable()){
                                    //10.异步读取客户端请求消息到缓存区
                                    SocketChannel sc = (SocketChannel) key.channel();
                                    ByteBuffer readBuffer = ByteBuffer.allocate(64*1024);
                                    int readBytes = sc.read(readBuffer);
                                    //11.对ByteBuffer进行编解码，如果有半包消息指针reset，继续读取后续的报文
                                    if(readBytes > 0){
                                        readBuffer.flip();
                                        byte[] bytes = new byte[readBuffer.remaining()];
                                        readBuffer.get(bytes);
                                        String body = new String(bytes, "UTF-8");
                                        System.out.println("Server receive a order: \"" + body + "\"");
                                        readBuffer.clear();
                                        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(System.currentTimeMillis()).toString() : "OTHER ORDER";
                                        //写应答
                                        byte[] bytes2 = ("CurrentTime is: " + currentTime).getBytes();
                                        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes2.length);
                                        writeBuffer.put(bytes2);
                                        writeBuffer.flip();
                                        sc.write(writeBuffer);
                                    } else if(readBytes < 0) {
                                        //对端链路关闭
                                        key.cancel();
                                        sc.close();
                                    }else ;// 读到0字节，忽略
                                }
                            }
                        }catch (Exception e){
                            if(key != null){
                                key.cancel();
                                if(key.channel() != null)
                                    key.channel().close();
                            }
                        }
                    }//while
                }catch (Throwable t){
                    t.printStackTrace();
                }
            }//while
        }//run
    }

}
