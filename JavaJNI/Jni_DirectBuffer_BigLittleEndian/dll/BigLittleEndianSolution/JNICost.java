import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class JNICost {
    //reverse int ByteOrder
    public static int reverseBytes(int i) {
        return ((i >>> 24)           ) |
                   ((i >>   8) & 0xFF00  ) |
	   ((i <<   8) & 0xFF0000) |
	   ((i <<  24));
    }

    public static void main(String[] args) {
        int n = 100000000;
        ByteBuffer buffer = ByteBuffer.allocateDirect(n * 4);
        //buffer.order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < n; i++) {
            buffer.putInt(i);
        }

        // for Little-Endian machine  -->  reverseBytes and putInt again
        for (int i = 0; i < n; i++) {
            buffer.putInt(i * 4, reverseBytes(buffer.getInt(i * 4)));
        }

        long startTime = System.currentTimeMillis();
        JNI_Func jni_func = new JNI_Func();
        jni_func.calculate(buffer, n);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

        for (int i = 0; i < 10; i++){
        // for Little-Endian machine  -->  getInt and reverseBytes again
            System.out.println(reverseBytes(buffer.getInt(i * 4)));
        }

    }
}
