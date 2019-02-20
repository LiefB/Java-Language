import java.io.*;

public class AppendWriteObject{

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //1.write
        File file = new File("/Users/liefb/Desktop/serialize.txt");
        boolean isexit = false;
        if (file.exists()) {
            isexit = true;// 序列化文件存在,追加内容
        }
        FileOutputStream fos = new FileOutputStream(file, true);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        long pos = 0;// 可以说是文件的长度
        if (isexit) {
            // getChannel()返回此通道的文件位置，这是一个非负整数，它计算从文件的开始到当前位置之间的字节数
            pos = fos.getChannel().position() - 4;// StreamHeader有4个字节所以减去
            // 将此通道的文件截取为给定大小
            fos.getChannel().truncate(pos);
        }

        oos.writeObject(new Record("single1", new Integer(1)));//一个Record是一个对象
        oos.writeObject(new Record("single2", new Integer(2)));//一个Record是一个对象
        oos.writeObject(new Record("single3", new Integer(3)));//一个Record是一个对象
        oos.writeObject(new Record("single4", new Integer(4)));//一个Record是一个对象

        oos.flush();
        oos.close();
        fos.close();





        File file2 = new File("/Users/liefb/Desktop/serialize.txt");
        boolean isexit2 = false;
        if (file2.exists()) {
            isexit2 = true;// 序列化文件存在,追加内容
        }
        FileOutputStream fos2 = new FileOutputStream(file,true);
        ObjectOutputStream oos2 = new ObjectOutputStream(fos2);

        long pos2 = 0;// 可以说是文件的长度
        if (isexit2) {
            // getChannel()返回此通道的文件位置，这是一个非负整数，它计算从文件的开始到当前位置之间的字节数
            pos2 = fos2.getChannel().position() - 4;// StreamHeader有4个字节所以减去
            // 将此通道的文件截取为给定大小
            fos2.getChannel().truncate(pos2);
        }

        oos2.writeObject(new Record("single1", new Integer(5)));//一个Record是一个对象
        oos2.writeObject(new Record("single2", new Integer(6)));//一个Record是一个对象
        oos2.writeObject(new Record("single3", new Integer(7)));//一个Record是一个对象
        oos2.writeObject(new Record("single4", new Integer(8)));//一个Record是一个对象

        oos2.flush();
        oos2.close();
        fos2.close();



        //2.read（注意读取对象的顺序要和存入对象的顺序相同，否则不同类型的数据读出做类型转换时对不上号会出现类型转换错误）
        FileInputStream fis = new FileInputStream("/Users/liefb/Desktop/serialize.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);

        while (fis.available() > 0){
            Record r = (Record) ois.readObject();
            System.out.println(r.key + " " + r.value);
        }

        fis.close();
        ois.close();

    }

}