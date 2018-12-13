package SimpleBloomFilter;

/**
 * 一个BloomFilter的简单实现
 * 只能添加，不能删除元素；不存在则一定不存在；存在只是大概率有可能存在(与Hash函数个数、数组长度有关)；
 */

public class SimpleBloomFilter {

    //数组长度
    private int arraySize;

    //数组
    private boolean[] array;

    //构造函数
    public SimpleBloomFilter(int arraySize) {
        this.arraySize = arraySize;
        array = new boolean[arraySize];
    }

    /**
     * 写入数据
     * @param key
     */
    public void add(String key) {

        array[hashcode_1(key) % arraySize] = true;
        array[hashcode_2(key) % arraySize] = true;
        array[hashcode_3(key) % arraySize] = true;

    }

    /**
     * 判断数据是否可能存在
     * @param key
     * @return
     */
    public boolean check(String key) {

        if(array[hashcode_1(key) % arraySize] == false)
            return false;

        if(array[hashcode_2(key) % arraySize] == false)
            return false;

        if(array[hashcode_3(key) % arraySize] == false)
            return false;

        return true;

    }


    /**
     * hash 算法1
     * @param key
     * @return
     */
    private int hashcode_1(String key) {
        int hash = 0;
        int i;
        for (i = 0; i < key.length(); ++i) {
            hash = 33 * hash + key.charAt(i);
        }
        return Math.abs(hash);
    }

    /**
     * hash 算法2
     * @param data
     * @return
     */
    private int hashcode_2(String data) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < data.length(); i++) {
            hash = (hash ^ data.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return Math.abs(hash);
    }

    /**
     *  hash 算法3
     * @param key
     * @return
     */
    private int hashcode_3(String key) {
        int hash, i;
        for (hash = 0, i = 0; i < key.length(); ++i) {
            hash += key.charAt(i);
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);
        return Math.abs(hash);
    }

}
