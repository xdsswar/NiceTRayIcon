package com.jfx.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author XDSSWAR
 */
public final class PlatformUtil {

    /**
     * Disallow Instances
     */
    private PlatformUtil(){}

    /**
     * Check if the Current Platform is Windows
     * @return boolean
     */
    public static boolean isWindow(){
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    /**
     * Check if the Current Platform is MacOS
     * @return boolean
     */
    public static boolean isMac(){
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }

    /**
     * Check if the Current Platform is Linux
     * @return boolean
     */
    public static boolean isLinux(){
        String os=System.getProperty("os.name").toLowerCase();
        return os.contains("nix") || os.contains("nux") || os.indexOf("aix") > 0;
    }

    /**
     * Get the Current Platform Directory Separator
     * @return String
     */
    public static String dirSeparator(){
        if (isWindow()){
            return "\\";
        }
        return "/";
    }

    /**
     * Get the Path to the user's Documents folder path
     * @return String
     */
    public static String getDocumentsPath(){
        javax.swing.JFileChooser ch=new javax.swing.JFileChooser();
        ch.setVisible(false);
        return ch.getFileSystemView().getDefaultDirectory().toString()+dirSeparator();
    }

    /**
     * Get the Current user's folder path
     * @return String
     */
    public static String getUserPath(){
        return System.getProperty("user.home")+dirSeparator();
    }

    /**
     * Create Directory or Directories
     * This method will Check if the Directory exist, if not the dir will
     * be created. This will happen is a recursive mode.
     * For example: We want to create this directory app/props/
     * NOTE: The method assumes that you add the full path to the dir dynamically like p={C:\Users\} for Windows
     * or p={C:/Users/} for MacOS and Linux. At the end the result looks like this p+/app/props/ where every "/"
     * will be replaced for the current OS dir separator.
     * 1.The app will check if the app dir exist and create it if not.
     * 2.Then the props dir will be created.
     * @param baseDir String Base Directory where we start
     * @param dirs String Dir or Dirs to be created one by one
     */
    public static void createDirectory(String baseDir, String dirs){
        if (!baseDir.endsWith(dirSeparator())){
            baseDir=baseDir+dirSeparator();
        }
        if (dirs.startsWith("/")){
            dirs=dirs.substring(1);
        }
        String[] strings = dirs.split("/");
        for (String string : strings) {
            if (!baseDir.endsWith(dirSeparator())){
                baseDir=baseDir+dirSeparator();
            }
            baseDir=baseDir+string;
            if (!Files.exists(Path.of(baseDir))){
                try {
                    Files.createDirectory(Path.of(baseDir));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
