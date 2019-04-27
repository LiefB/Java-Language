package rpc;

public class RpcClient {

    public static void main(String[] args) {
        try {
            //引用服务
            HelloService service = RpcFramework.refer(HelloService.class, "127.0.0.1", 8888);
            AddService addService = RpcFramework.refer(AddService.class, "127.0.0.1", 8889);
            //RPC调用服务和本地调用服务一样
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                String result = service.sayHello("rpc" + i);
                System.out.println(result);
                int result2 = addService.add(i, i);
                System.out.println(result2);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}