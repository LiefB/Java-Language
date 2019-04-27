public class StaticProxyTest {
    public static void main(String[] args) {
        //构造一个Xiaomin
        ILawsuit xiaomin = new XiaoMin();

        //构造一个代理律师，并将xiaomin作为构造参数传递进去
        ILawsuit lawyer = new Lawyer(xiaomin);

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
