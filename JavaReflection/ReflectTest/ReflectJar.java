import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ReflectJar {
    public static void main(String[] args) throws Exception {

        /*
         * 动态加载指定jar包,调用其中某个类的方法
         */
        File file = new File("D:/HelloString.jar");//指定jar包的路径
        URL url = file.toURI().toURL();
        URLClassLoader loader = new URLClassLoader(new URL[]{url});//创建类加载器
        Class<?> cls = loader.loadClass("com.liefb.test.HelloString");//加载指定类，注意一定要带上类的包名
        Object obj = cls.newInstance();//初始化一个实例
        Method method_print = cls.getMethod("print",String.class);//方法名和对应的各个参数的类型
        method_print.invoke(obj,"World");//调用得到的上边的方法method(静态方法，第一个参数可以为null)
        Method method_staticPrint = cls.getMethod("staticPrint",String.class);//方法名和对应的各个参数的类型
        method_staticPrint.invoke(null,"World");//调用得到的上边的方法method(静态方法，第一个参数可以为null)

    }
}
