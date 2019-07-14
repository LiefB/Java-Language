public class CudaJNI {
    public native int printHelloWorldByGPU();

    static {
        System.load("/home/liefb/Desktop/libCudaJNI.so");
    }
}
