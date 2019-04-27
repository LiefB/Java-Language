package rpc;

import java.io.IOException;

public class RpcServer {
    public static void main(String[] args) {
        try {
            //暴露服务
            HelloService service = new HelloServiceImpl();
            RpcFramework.export(service, 8888);
            AddService addService = new AddServiceImpl();
            RpcFramework.export(addService, 8889);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务端对接口的具体实现
     */
    private static class HelloServiceImpl implements HelloService {
        @Override
        public String sayHello(String msg) {
            String result = "hello world " + msg;
            System.out.println(result);
            return result;
        }
    }

    public static class AddServiceImpl implements AddService {
        @Override
        public int add(int a, int b) {
            return a + b;
        }
    }

}
