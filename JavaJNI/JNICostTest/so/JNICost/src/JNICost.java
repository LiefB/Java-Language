public class JNICost {
    public static void main(String[] args) {
        int n = 100000000;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++){
            arr[i] = i;
        }

        long startTime = System.currentTimeMillis();
        JNI_Func jni_func = new JNI_Func();
        jni_func.calculate(arr, n);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

        for (int i = 0; i < 10; i++){
            System.out.println(arr[i]);
        }

    }
}
