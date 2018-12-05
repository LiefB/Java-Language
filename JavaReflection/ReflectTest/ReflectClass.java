import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ReflectClass {
    public static void main(String[] args) throws Exception {

        /*
         * 动态载入指定类
         */

        File file = new File("D:/");//类路径
        URL url = file.toURI().toURL();
        ClassLoader loader = new URLClassLoader(new URL[]{url});//创建类载入器
        Class<?> cls = loader.loadClass("HelloString");//载入指定类
        Object obj = cls.newInstance();//初始化一个实例
        Method method_print = cls.getMethod("print", String.class);//方法名和相应的參数类型
        method_print.invoke(obj,"World");//调用得到的上边的方法method
        Method method_staticPrint = cls.getMethod("staticPrint", String.class);//方法名和相应的參数类型
        method_staticPrint.invoke(null,"World");//调用得到的上边的静态方法method,不需要实例对象，null

    }
}
