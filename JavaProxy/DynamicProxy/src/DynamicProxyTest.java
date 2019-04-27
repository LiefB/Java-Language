import java.lang.reflect.Proxy;

public class DynamicProxyTest {
    public static void main(String[] args) {
        //构造一个XiaoMin
        ILawsuit xiaomin = new XiaoMin();

        //构造一个动态代理
        DynamicProxy proxy = new DynamicProxy(xiaomin);

        //获取被代理类XiaoMin的ClassLoader
        ClassLoader loader = xiaomin.getClass().getClassLoader();

        //动态构造一个代理者律师
        ILawsuit lawyer = (ILawsuit) Proxy.newProxyInstance(loader, new Class[] { ILawsuit.class }, proxy);

        //律师代理提交申请
        lawyer.submit();

        //律师代理进行举证
        lawyer.burden();

        //律师代理进行辩护
        lawyer.defend();

        //律师代理完成诉讼
        lawyer.finish();

    }
}
