import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class JavaProperties {

    public static void main(String[] args) {
        Properties prop = new Properties();
        try{
            //读取属性文件a.properties
            InputStream in = new BufferedInputStream (new FileInputStream("/Users/liefb/Desktop/a.properties"));
            prop.load(in);     ///加载属性列表
            Iterator<String> it=prop.stringPropertyNames().iterator();
            while(it.hasNext()) {
                String key=it.next();
                System.out.println(key + ":" + prop.getProperty(key));
            }
            in.close();

            ///保存属性到b.properties文件
            FileOutputStream oFile = new FileOutputStream("/Users/liefb/Desktop/b.properties", true);//true表示追加打开
            prop.setProperty("phone", "10086");
            prop.setProperty("name", "modify");
            prop.store(oFile, "The New properties file");
            oFile.close();
        }catch(Exception e) {
            System.out.println(e);
        }
    }
}