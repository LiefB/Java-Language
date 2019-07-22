import java.nio.ByteBuffer;

public class JNI_Func {
    public native void calculate(ByteBuffer arr, int n);

    static {
        System.load("C:\\Users\\liefb\\Desktop\\a2\\jni_calculate.dll");
    }
}
