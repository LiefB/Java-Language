import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JavaSerializeTest{

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //1.write
        FileOutputStream fos = new FileOutputStream("/Users/liefb/Desktop/serialize.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(new Record("single1", new Integer(1)));//一个Record是一个对象

        List<Record> lw = new ArrayList<Record>();
        lw.add(new Record("a", new Integer(1)));
        lw.add(new Record("a", new Integer(2)));
        lw.add(new Record("a", new Integer(3)));
        lw.add(new Record("a", new Integer(4)));
        lw.add(new Record("a", new Integer(5)));
        lw.add(new Record("a", new Integer(6)));
        lw.add(new Record("a", new Integer(7)));
        lw.add(new Record("a", new Integer(8)));
        lw.add(new Record("a", new Integer(9)));
        lw.add(new Record("b", new Integer(1)));
        lw.add(new Record("b", new Integer(2)));
        lw.add(new Record("b", new Integer(3)));
        lw.add(new Record("b", new Integer(4)));
        lw.add(new Record("b", new Integer(5)));
        lw.add(new Record("b", new Integer(6)));
        lw.add(new Record("b", new Integer(7)));
        lw.add(new Record("b", new Integer(8)));
        lw.add(new Record("b", new Integer(9)));
        lw.add(new Record("c", new Integer(1)));
        lw.add(new Record("c", new Integer(2)));
        lw.add(new Record("c", new Integer(3)));
        lw.add(new Record("c", new Integer(4)));
        lw.add(new Record("c", new Integer(5)));
        lw.add(new Record("c", new Integer(6)));
        lw.add(new Record("c", new Integer(7)));
        lw.add(new Record("c", new Integer(8)));
        lw.add(new Record("c", new Integer(9)));
        oos.writeObject(lw);//整个List<Record>是一个对象

        oos.writeObject(new Record("single2", new Integer(2)));//一个Record是一个对象
        oos.writeObject(new Record("single3", new Integer(3)));//一个Record是一个对象

        oos.flush();
        oos.close();
        fos.close();


        //2.read（注意读取对象的顺序要和存入对象的顺序相同，否则不同类型的数据读出做类型转换时对不上号会出现类型转换错误）
        FileInputStream fis = new FileInputStream("/Users/liefb/Desktop/serialize.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);

        Record r1 = (Record) ois.readObject();
        System.out.println(r1.key + " " + r1.value);

        List<Record> lr = (List<Record>) ois.readObject();
        for (int i = 0; i < lr.size(); i++){
            System.out.println(lr.get(i).key + " " + lr.get(i).value);
        }

        Record r2 = (Record) ois.readObject();
        System.out.println(r2.key + " " + r2.value);

        Record r3 = (Record) ois.readObject();
        System.out.println(r3.key + " " + r3.value);

        fis.close();
        ois.close();

    }

}