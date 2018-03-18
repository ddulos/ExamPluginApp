package han.ica.asd.utility;

import han.ica.asd.domain.interfaces.IPlugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for handling plugins.
 * <p>
 * Here the application loads plugins that will be used while for the questions in an exam.
 * <p>
 * The application will search for available plugins in the form of .jar files.
 * Then the classes within the .jar file will be searched through until a class will be found that implements the IPlugin interface.
 * From this class a new instance will be made and put in the HashMap, which the controller can use to pick the correct plugin to create the questions present in the exam.
 */
public class PluginHandler {
    private static final Logger logger = Logger.getLogger(PluginHandler.class.getName());

    private static final String PLUGINS_SHORT_PATH = "plugins/";
    private static final String PLUGINS_PATH = System.getProperty("user.dir") + File.separator + "plugins";
    private static final String PLUGIN_PACKAGE_PATH = "han.ica.asd.plugin";

    private HashMap<String, IPlugin> loadedPlugins;

    /**
     * Load plugin jar files located in given path.
     */
    public void loadPlugins() {
        File pluginDirectory = new File(PLUGINS_PATH);
        loadedPlugins = new HashMap<>();

        for (String file : pluginDirectory.list()) {
            try {
                // only consider files ending in ".jar"
                if (file.endsWith(".jar"))
                    getClassesFromFile(PLUGINS_SHORT_PATH + file);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Something went wrong while searching for files.");
            }
        }
    }

    /**
     * Get classes located in jar file.
     *
     * @param pathToJar Path to jar file to load the classes.
     */
    private void getClassesFromFile(String pathToJar) {
        try {
            Enumeration<JarEntry> entries = new JarFile(pathToJar).entries();

            URL[] urls = {new URL("jar:file:" + pathToJar + "!/")};
            URLClassLoader urlClassLoader = URLClassLoader.newInstance(urls);

            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                if (!jarEntry.getName().endsWith(".class"))
                    continue;

                loadPluginClass(urlClassLoader, getClassName(jarEntry));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong while loading classes.", e);
        }
    }

    /**
     * Retrieve IPlugin implemented class from plugin file and save instance to HashMap.
     *
     * @param urlClassLoader UrlClassLoader to load classes by URL of file.
     * @param className      Name of the class to be loaded.
     */
    @SuppressWarnings("unchecked")
    private void loadPluginClass(URLClassLoader urlClassLoader, String className) throws Exception {
        if (className.contains(PLUGIN_PACKAGE_PATH)) {
            Class loadedClass = urlClassLoader.loadClass(className);

            if (IPlugin.class.isAssignableFrom(loadedClass)) {
                IPlugin plugin = (IPlugin) loadedClass.newInstance();
                loadedPlugins.put(plugin.getPluginID(), plugin);
            }
        }
    }

    /**
     * Get name of the class by altering the string from given jar entry.
     *
     * @param jarEntry The jar entry that contains the unaltered string of the class name.
     * @return Altered string containing the class name with package path.
     */
    private String getClassName(JarEntry jarEntry) {
        String className = jarEntry.getName();
        className = className.substring(0, jarEntry.getName().length() - 6);
        className = className.replace('/', '.');
        return className;
    }

    /**
     * Getter for property 'loadedPlugins'.
     *
     * @return HashMap that contains loaded plugins.
     */
    public Map<String, IPlugin> getLoadedPlugins() {
        return loadedPlugins;
    }
}
