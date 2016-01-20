package com.nata.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 14:36
 */
public class ShellUtil {
    /**
     * Run shell command starting with 'adb'
     * @param cmd command to execute
     * @return running process
     */
    public static Process adb(String cmd) {
        return process("adb " + cmd);
    }

    /**
     *  Run shell command starting with 'adb shell'
     * @param cmd command to execute
     * @return running process
     */
    public static Process adbshell(String cmd) {
        return process("adb shell " + cmd);
    }

    /**
     * Return Output of the previous executed shell command process
     * @param ps previous executed shell command process
     * @return Output of process
     */
    public static String getShellOut(Process ps) {
        BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line + System.lineSeparator()) ;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString().trim();
    }

    /**
     * Execute a shell command
     * @param command command to execute
     * @return process executed
     */
    private static Process process(String command) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return process;
    }


    //mininal unit test
    public static void main(String[] args) {
        Process process = ShellUtil.adb("devices");
        System.out.println(ShellUtil.getShellOut(process));

        Process adbShellProcess = ShellUtil.adbshell("getprop ro.boot.serialno");
        System.out.println(ShellUtil.getShellOut(adbShellProcess));
    }

}
