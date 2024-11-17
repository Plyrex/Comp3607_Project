package s_jamz.AutoGrader;

import java.net.URL;
import java.net.URLClassLoader;

public class IsolatedClassLoader extends URLClassLoader {
    public IsolatedClassLoader(URL[] urls) {
        super(urls, null); // Use null to avoid parent class loading
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // Check if the class is already loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                // Load the class locally
                try {
                    c = findClass(name);
                } catch (ClassNotFoundException e) {
                    // Fallback to default behavior
                    c = super.loadClass(name, resolve);
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }
}