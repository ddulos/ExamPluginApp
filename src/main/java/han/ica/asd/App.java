package han.ica.asd;

import han.ica.asd.domain.Question;
import han.ica.asd.domain.interfaces.IPlugin;
import han.ica.asd.domain.interfaces.IQuestionView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
        Parent root = loader.load();

        window.setTitle("Exam Plugin App");
        window.setScene(new Scene(root, 600, 400));
        window.show();

//        TestPlugin();
//        loadPluginClass();

    }

    private void loadPluginClass() {
        String pathToJar = "plugins/MultipleChoicePlugin.jar";
        URLClassLoader classLoader = null;
        JarFile pluginFile = null;
        try {
            pluginFile = new JarFile(pathToJar);
            Enumeration<JarEntry> entries = pluginFile.entries();

            URL[] urls = {new URL("jar:file:" + pathToJar + "!/")};
            try (URLClassLoader urlClassLoader = URLClassLoader.newInstance(urls)) {
//            System.out.println("PluginFile: " + pluginFile.getName());
//            classLoader = new URLClassLoader(new URL[]{pluginFile.toURI().toURL()});

                while (entries.hasMoreElements()) {
                    JarEntry jarEntry = entries.nextElement();
                    if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
                        continue;
                    }
                    // -6 because of .class
                    String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6);
                    className = className.replace('/', '.');
                    if (className.contains("han.ica.asd.plugin") && !className.contains("hello")) {
                                            Class loadedClass = urlClassLoader.loadClass(className);
//                        Class loadedClass = Class.forName(className, true, urlClassLoader);
                        System.out.println("Class: " + loadedClass.getName());

                        if (!loadedClass.isInterface()) {
                            String interfaceName = loadedClass.getInterfaces()[0].getSimpleName();
                            System.out.println("interfaceName " + interfaceName);

                            if (interfaceName.equals(IPlugin.class.getSimpleName())) {
                                Constructor classConstructor = loadedClass.getDeclaredConstructor(String.class, int.class, JSONObject.class, String.class);
                                IPlugin plugin = (IPlugin) classConstructor.newInstance("Test questionphrasing...", 10, null, "freeInput");
                                System.out.println(plugin.getPluginID());

//                                Constructor classConstructor = loadedClass.getDeclaredConstructor(String.class, int.class, JSONObject.class, String.class);
//                                IPlugin plugin = (IPlugin) classConstructor.newInstance("Test questionphrasing...", 10, null, "freeInput");
//                                System.out.println(plugin.getPluginID());
//
//                                Question question = (Question) plugin.getQuestion();
//                                System.out.println("Phrasing: " + question.getQuestionPhrasing());
//                                System.out.println("Type: " + question.getQuestionType());
//                                System.out.println("Points: " + question.getPoints());
//
//                                IQuestionView questionView = plugin.getQuestionView();
//                                System.out.println(questionView);
//                                questionView.getView();
//
//                                System.out.println(plugin.getQuestionView());
                            }
                        }
                    }
                }
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e1) {
                e1.printStackTrace();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (classLoader != null) {
                    classLoader.close();
                }
                if (pluginFile != null) {
                    pluginFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
