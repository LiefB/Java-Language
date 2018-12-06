package JavaSerializable.PersistOnDisk;

import java.io.Serializable;

public class Person implements Serializable{

    private static final long serialVersionUID = -8122420157083146928L;

    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
