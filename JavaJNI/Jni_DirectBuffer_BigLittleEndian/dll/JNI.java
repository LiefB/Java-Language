import java.nio.ByteBuffer;

public class JNI {
    public native void call(ByteBuffer buffer, int len);

    static {
        //System.loadLibrary("jniCall");
        System.load("C:\\Users\\liefb\\Desktop\\JNIBigLittleEndian\\JNIdll.dll");
    }
}
