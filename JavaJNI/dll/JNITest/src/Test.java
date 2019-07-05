public class Test {
    public static void main(String[] args) {
        // 创建JNI的对象call
        JNI call = new JNI();

        // 调用call方法
        int i = call.call();

        //输出结果
        System.out.println("调用Java Native Interface，返回结果：" + i);
    }
}
