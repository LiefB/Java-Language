public class CudaJNI {

    public native int printHelloWorldByGPU();

    static {
        System.load("C:\\Users\\liefb\\Desktop\\CudaJNITest\\src\\CudaJNIdll.dll");
    }

}
