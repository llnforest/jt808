package org.yzh.framework.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class ClassUtils {

    private static final Logger log = LoggerFactory.getLogger(ClassUtils.class.getSimpleName());

    public static List<Class<?>> getClassList(String packageName, Class<? extends Annotation> annotationClass) {
        List<Class<?>> classList = getClassList(packageName);
        Iterator<Class<?>> iterator = classList.iterator();
        while (iterator.hasNext()) {
            Class<?> next = iterator.next();
            if (!next.isAnnotationPresent(annotationClass))
                iterator.remove();
        }
        return classList;
    }

    public static List<Class<?>> getClassList(String packageName) {
        List<Class<?>> classList = new LinkedList();
        String path = packageName.replace(".", "/");
        try {
            Enumeration<URL> urls = ClassUtils.getClassLoader().getResources(path);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();

                if (url != null) {
                    String protocol = url.getProtocol();

                    if (protocol.equals("file")) {
                        addClass(classList, url.toURI().getPath(), packageName);

                    } else if (protocol.equals("jar")) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        JarFile jarFile = jarURLConnection.getJarFile();

                        Enumeration<JarEntry> jarEntries = jarFile.entries();
                        while (jarEntries.hasMoreElements()) {

                            JarEntry jarEntry = jarEntries.nextElement();
                            String entryName = jarEntry.getName();

                            if (entryName.startsWith(path) && entryName.endsWith(".class")) {
                                String className = entryName.substring(0, entryName.lastIndexOf(".")).replaceAll("/", ".");
                                addClass(classList, className);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取类出错！", e);
        }
        return classList;
    }

    private static void addClass(List<Class<?>> classList, String packagePath, String packageName) {
        try {
            File[] files = new File(packagePath).listFiles(file -> (file.isDirectory() || file.getName().endsWith(".class")));
            if (files != null)
                for (File file : files) {
                    String fileName = file.getName();
                    if (file.isFile()) {
                        String className = fileName.substring(0, fileName.lastIndexOf("."));
                        if (packageName != null) {
                            className = packageName + "." + className;
                        }
                        addClass(classList, className);
                    } else {
                        String subPackagePath = fileName;
                        if (packageName != null) {
                            subPackagePath = packagePath + "/" + subPackagePath;
                        }
                        String subPackageName = fileName;
                        if (packageName != null) {
                            subPackageName = packageName + "." + subPackageName;
                        }
                        addClass(classList, subPackagePath, subPackageName);
                    }
                }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void addClass(List<Class<?>> classList, String className) {
        classList.add(loadClass(className, false));
    }

    public static Class<?> loadClass(String className, boolean isInitialized) {
        try {
            return Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}