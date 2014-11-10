/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.controller;

/**
 *
 * @author grender
 */
public class OSController {

    private static final String osName = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return (osName.contains("win"));
    }

    public static boolean isMac() {
        return (osName.contains("mac"));
    }

    public static boolean isUnix() {
        return (osName.contains("nix")
                || osName.contains("nux")
                || osName.contains("aix"));
    }

    public static boolean isSolaris() {
        return (osName.contains("sunos"));
    }

    public static String getOS() {
        if (isWindows()) {
            return "Windows";
        } else if (isMac()) {
            return "OSx";
        } else if (isUnix()) {
            return "Linux";
        } else if (isSolaris()) {
            return "Solaris";
        } else {
            return "Unknown";
        }
    }
}
