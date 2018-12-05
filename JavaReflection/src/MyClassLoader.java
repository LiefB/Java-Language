import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class MyClassLoader extends URLClassLoader {

    public MyClassLoader(final String path, final ClassLoader parent) throws MalformedURLException {
        super(new URL[]{ new URL(path) }, parent);
    }
}