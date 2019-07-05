public class JNI {
    // 创建一个native的接口，此接口在C++代码中实现
    public native int call();

    // 静态代码块，加载由C++代码生成的.dll动态链接库文件
    static {
        //System.loadLibrary("JNIdll");
        System.load("C:\\Users\\liefb\\Desktop\\JNITest\\src\\JNIdll.dll");
    }
}
