package rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 简单的RPC框架
 * 服务端暴露服务，绑定一个端口，利用Socket轮询，等待接受客户端的请求。
 * 客户端引用服务，利用动态代理，隐藏掉每个接口方法的实际调用。
 * 客户端将方法名、参数类型、方法所需参数传给服务端，服务端接受到客户端传过来的方法名、参数类型、方法所需参数之后，利用反射，执行具体的接口方法，然后将执行结果返回给客户端
 *
 * 这里呢，我只是简单的传递了方法名、参数类型、方法所需参数。
 * 当需要完成更复杂的交互时，我们可以指定一个协议,然后由Server和Client根据该协议对数据的进行编码解码，根据协议内容做出相应的决策。
 * 总而言之，RPC的核心是动态代理 。
 * 客户端看到的是接口的行为（这个行为没有被实现），
 * 服务端放的是接口行为的具体实现。
 * 客户端把行为和行为入参提供给服务端，然后服务端的接口实现执行这个行为，最后再把执行结果返回给客户端。
 * 看起来是客户端执行了行为，但其实是通过动态代理交给服务端执行的。其中，行为和入参这些数据通过socket由客户端传给了服务端。
 */
public class RpcFramework {

    private static ExecutorService executorService = Executors.newFixedThreadPool(20);

    /**
     * 服务端暴露服务
     *
     * @param service 服务实现
     * @param port    服务端口
     */
    public static void export(final Object service, int port) throws IOException {
        if (service == null) {
            throw new IllegalArgumentException("service instance == null");
        }
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port " + port);
        }
        System.out.println("Export service " + service.getClass().getName() + " on port " + port);
        ServerSocket server = new ServerSocket(port);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                     Socket socket = null;
                    try {
                        socket = server.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Socket finalSocket = socket;
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                try {
                                    //服务端接受客户端传过来的方法名、参数类型、方法所需参数，然后执行方法
                                    ObjectInputStream input = new ObjectInputStream(finalSocket.getInputStream());
                                    try {
                                        String methodName = input.readUTF();
                                        Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                                        Object[] arguments = (Object[]) input.readObject();
                                        ObjectOutputStream output = new ObjectOutputStream(finalSocket.getOutputStream());
                                        try {
                                            Method method = service.getClass().getMethod(methodName, parameterTypes);
                                            Object result = method.invoke(service, arguments);
                                            output.writeObject(result);
                                        } catch (Throwable t) {
                                            output.writeObject(t);
                                        } finally {
                                            output.close();
                                        }
                                    } finally {
                                        input.close();
                                    }
                                } finally {
                                    finalSocket.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 客户端引用服务
     *
     * @param <T>            接口泛型
     * @param interfaceClass 接口类型
     * @param host           服务器主机名
     * @param port           服务器端口
     * @return 远程服务
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T refer(final Class<T> interfaceClass, final String host, final int port) throws Exception {
        if (interfaceClass == null)
            throw new IllegalArgumentException("Interface class == null");
        if (!interfaceClass.isInterface())
            throw new IllegalArgumentException("The " + interfaceClass.getName() + " must be interface class!");
        if (host == null || host.length() == 0)
            throw new IllegalArgumentException("Host == null!");
        if (port <= 0 || port > 65535)
            throw new IllegalArgumentException("Invalid port " + port);
        System.out.println("Get remote service " + interfaceClass.getName() + " from server " + host + ":" + port);

        //利用动态代理，对每个接口类的方法调用进行的隐藏
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
                Socket socket = new Socket(host, port);
                try {
                    //客户端将方法名、参数类型、方法所需参数传给服务端
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    try {
                        output.writeUTF(method.getName());
                        output.writeObject(method.getParameterTypes());
                        output.writeObject(arguments);
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                        try {
                            Object result = input.readObject();
                            if (result instanceof Throwable) {
                                throw (Throwable) result;
                            }
                            return result;
                        } finally {
                            input.close();
                        }
                    } finally {
                        output.close();
                    }
                } finally {
                    socket.close();
                }
            }
        });
    }
}