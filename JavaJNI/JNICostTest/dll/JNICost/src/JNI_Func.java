public class JNI_Func {
    public native void calculate(int[] arr, int n);

    static {
        System.load("C:\\Users\\liefb\\Desktop\\jni_calculate.dll");
    }
}
