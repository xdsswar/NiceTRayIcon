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



}
