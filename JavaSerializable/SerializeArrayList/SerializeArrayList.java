import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializeArrayList{

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //1.write
        FileOutputStream fos = new FileOutputStream("/Users/liefb/Desktop/serialize.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

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

        oos.writeObject(lw);
        oos.flush();
        oos.close();
        fos.close();


        //2.read
        FileInputStream fis = new FileInputStream("/Users/liefb/Desktop/serialize.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<Record> lr = (List<Record>) ois.readObject();
        ois.close();
        fis.close();


        //3.print to verify
        for (int i = 0; i < lr.size(); i++){
            System.out.println(lr.get(i).key + " " + lr.get(i).value);
        }

    }

}