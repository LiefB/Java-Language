package JavaSerializable.PersistOnDisk;

import java.io.*;
import java.text.MessageFormat;

public class SerializeOnDisk {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Person person = new Person();
        person.setName("liefb");
        person.setAge(22);

        // ObjectOutputStream对象输出流，将Person对象存储到D盘的Person.txt文件中，完成对Person对象的序列化操作
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
                new File("D:/Person.txt")));
        oos.writeObject(person);
        System.out.println("Person Serialize Success...");
        oos.close();

        // ObjectInputStream对象输入流，读入D盘的Person.txt文件中的对象，完成对Person对象的反序列化操作
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
                new File("D:/Person.txt")));
        Person p_deSerialize = (Person) ois.readObject();
        System.out.println("Person deSerialize Success...");

        System.out.println(MessageFormat.format("name={0},age={1}", p_deSerialize.getName(), p_deSerialize.getAge()));
    }
}
