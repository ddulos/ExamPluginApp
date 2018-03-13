package han.ica.asd.utility;

import han.ica.asd.domain.interfaces.IPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PluginHandler {
    private static final Logger logger = Logger.getLogger(PluginHandler.class.getName());
    private HashMap<String, IPlugin> loadedPlugins;

    public void loadPlugins() {
        File dir = new File(System.getProperty("user.dir") + File.separator + "plugins");
        loadedPlugins = new HashMap<>();

        String[] files = dir.list();
        for (int i = 0; i < files.length; i++) {
            try {
                // only consider files ending in ".jar"
                if (!files[i].endsWith(".jar"))
                    continue;
                loadPluginClass(files[i]);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Something went wrong while searching for files.");
            }
        }
    }

    private void loadPluginClass(String fileName) {
        String pathToJar = "plugins/" + fileName;
        JarFile pluginFile = null;
        try {
            pluginFile = new JarFile(pathToJar);
            Enumeration<JarEntry> entries = pluginFile.entries();

            URL[] urls = {new URL("jar:file:" + pathToJar + "!/")};
            try (URLClassLoader urlClassLoader = URLClassLoader.newInstance(urls)) {

                while (entries.hasMoreElements()) {
                    JarEntry jarEntry = entries.nextElement();
                    if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
                        continue;
                    }

                    loadPluginClass(urlClassLoader, jarEntry);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException e1) {
                logger.log(Level.SEVERE, "Something went wrong while loading classes.");
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Something went wrong while loading the plugin file.");
        } finally {
            try {
                if (pluginFile != null) {
                    pluginFile.close();
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Something went wrong while closing the plugin file.");
            }
        }

    }

    @SuppressWarnings("unchecked")
    private void loadPluginClass(URLClassLoader urlClassLoader, JarEntry jarEntry) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6);
        className = className.replace('/', '.');
        if (className.contains("han.ica.asd.plugin") && !className.contains("hello")) {
            Class loadedClass = urlClassLoader.loadClass(className);
            logger.log(Level.FINE, "Class: " + loadedClass.getName());

            if (!loadedClass.isInterface()) {
                String interfaceName = loadedClass.getInterfaces()[0].getSimpleName();
                logger.log(Level.FINE, "interfaceName " + interfaceName);

                if (interfaceName.equals(IPlugin.class.getSimpleName())) {
                    Constructor classConstructor = loadedClass.getDeclaredConstructor();
                    IPlugin plugin = (IPlugin) classConstructor.newInstance();
                    loadedPlugins.put(plugin.getPluginID(), plugin);
                    logger.log(Level.FINE, "plugin loaded with ID: " + plugin.getPluginID());
                }
            }
        }
    }

    public Map<String, IPlugin> getLoadedPlugins() {
        return loadedPlugins;
    }
}
