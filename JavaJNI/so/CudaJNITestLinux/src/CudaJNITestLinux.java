public class CudaJNITestLinux {
    public static void main(String[] args) {
        CudaJNI cudajni = new CudaJNI();
        int ret = cudajni.printHelloWorldByGPU();
    }
}
