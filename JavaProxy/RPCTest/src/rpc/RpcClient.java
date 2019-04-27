package rpc;

public class RpcClient {

    public static void main(String[] args) {
        try {
            //引用服务
            HelloService service = RpcFramework.refer(HelloService.class, "127.0.0.1", 8989);
            //RPC调用服务和本地调用服务一样
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                String result = service.sayHello("rpc" + i);
                System.out.println(result);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}