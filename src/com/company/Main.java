package com.company;

import java.io.IOException;
import java.net.URL;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.prefs.Preferences;
import static java.lang.System.setErr;
import static java.util.prefs.Preferences.systemRoot;

public class Main {

    /*Start the program*/
    public static void startProgram(String[] args) {
        if(!checkCompatibility()) {
            System.out.format("You need to be using a version of Windows after Win7");
            System.exit(0);
        }
        if(!checkPermission()) {
            System.out.format("You must run the program as an administrator");
            System.exit(0);
        }
        System.out.format("Success");
        return;
    }

    /*Checks for administrator privilege. Much thanks to stackoverflow's BullyWiiPlaza*/
    public static boolean checkPermission() {
        Preferences preferences = systemRoot();

        synchronized (System.err) {
            setErr(new PrintStream(new OutputStream() {
                @Override
                public void write(int b) {
                }
            }));

            try {
                preferences.put("foo", "bar"); // SecurityException on Windows
                preferences.remove("foo");
                preferences.flush(); // BackingStoreException on Linux
                return true;
            } catch (Exception exception) {
                return false;
            } finally {
                setErr(System.err);
            }
        }
    }

    /*Checks for windows version*/
    public static boolean checkCompatibility() {
        float winVer = Float.parseFloat(System.getProperty("os.version").trim());
        if (winVer > 7) {
            return true;
        } else {
            return false;
        }
    }

    /*Driver*/
    public static void main(String[] args) {
        startProgram(args);
    }
}
