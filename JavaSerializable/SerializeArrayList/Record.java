import java.io.Serializable;

public class Record implements Serializable {
    public Object key;
    public Object value;

    public Record(){
        this.key = null;
        this.value = null;
    }

    public Record(Object key, Object value){
        this.key = key;
        this.value = value;
    }
}
