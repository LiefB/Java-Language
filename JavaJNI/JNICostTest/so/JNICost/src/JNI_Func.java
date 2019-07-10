public class JNI_Func {
    public native void calculate(int[] arr, int n);

    static {
        System.load("/mnt/c/Users/liefb/JNICost2/src/jni_calculate.so");
    }
}
