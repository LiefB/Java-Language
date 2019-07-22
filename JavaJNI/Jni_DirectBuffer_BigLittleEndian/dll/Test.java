import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Test {
	
	//reverse int ByteOrder
	public static int reverseBytes(int i) {
		return ((i >>> 24)           ) |
		       ((i >>   8) & 0xFF00  ) |
			   ((i <<   8) & 0xFF0000) |
			   ((i <<  24));
	}
	
    public static void main(String[] args) {
        // 创建JNI的对象call
        JNI jniObj = new JNI();

        // 申请JVM堆外内存
		int len = 5;
		ByteBuffer buffer = ByteBuffer.allocateDirect(len * 4);
		/*
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		for (int i = 0; i < len; i++) {
			buffer.putInt(i);
		}
		buffer.order(ByteOrder.BIG_ENDIAN);
		*/
		
		// 调用JNI方法
        jniObj.call(buffer, len);

        // 输出结果
		for (int i = 0; i < len; i++){
			//buffer.order(ByteOrder.LITTLE_ENDIAN);    //defaultByteOrder=BIG_ENDIAN
			//System.out.println(buffer.getInt(i * 4));
			System.out.println(reverseBytes(buffer.getInt(i * 4)));
		}
    }
}
