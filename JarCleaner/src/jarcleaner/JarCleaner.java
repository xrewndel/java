package jarcleaner;

import java.io.File;

/**
 *
 * @author Andrew
 * 
 * Walk on specified dir recursively and remove all jar files in 'dist' dir.
 * By default it only show files to remove.
 * java - jar JarCleaner.jar path - show
 * java - jar JarCleaner.jar path 1 - remove
 */
public class JarCleaner {
    static String path;
    static int i = 0;
    static boolean show = true;
    
    public static void main(String[] args) {
        if (args.length == 0) { System.out.println("Specify dir"); System.exit(0); }
        else if (args.length == 1) path = args[0]; 
        else { path = args[0]; show = false; } 
        
        proc(path);
        System.out.println("Total: " + i);
    }
    
    // обход каталога на реальной файловой системе
    public static void proc(String path) {
        File root = new File(path);
        File[] list = root.listFiles();

        for (File obj : list) {
            if (obj.isDirectory() && !obj.getParent().endsWith("dist")) 
                proc(obj.getAbsolutePath());
            else if (obj.getParent().endsWith("dist") && obj.getName().endsWith(".jar")) {
                System.out.println("Process " + obj.getAbsolutePath());
                if (!show) obj.delete();
                i++;
            }
        }
    }
}
