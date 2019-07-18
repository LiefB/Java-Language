public class JNI_Func {
    public native void calculate(int[] arr, int n);

    static {
        System.load("/home/liefb/CudaJNICostTest/src/libJniCalculate.so");
    }
}
