package SimpleBloomFilter;

public class SimpleBloomFilterTest {

    public static void main(String[] args){
        long start, end;

        //初始化BloomFilter，并添加元素
        start = System.currentTimeMillis();
        //本实验：数组长度设置为1000w，实验无误；改为100w，出现错误（结论：与数组长度有关）；
        SimpleBloomFilter simpleBloomFilters = new SimpleBloomFilter(10000000);
        for (int i = 0; i < 10000000; i++) {
            simpleBloomFilters.add(i + "") ;
        }
        end = System.currentTimeMillis();
        System.out.println("初始化BloomFilter并添加元素用时：" + (end - start) + "ms");


        //判断元素是否可能存在
        start = System.currentTimeMillis();
        System.out.println(simpleBloomFilters.check(1 + ""));
        System.out.println(simpleBloomFilters.check(2 + ""));
        System.out.println(simpleBloomFilters.check(3 + ""));
        System.out.println(simpleBloomFilters.check(999999 + ""));
        System.out.println(simpleBloomFilters.check(400230340 + ""));
        end = System.currentTimeMillis();
        System.out.println("判断元素是否可能存在用时：" + (end - start) + "ms");

    }

}
