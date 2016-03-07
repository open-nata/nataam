package com.nata;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-20 14:23
 */
public class ShellKit {
    /**
     * Run shell command starting with 'adb'
     *
     * @param cmd command list to execute
     * @return  Any Output
     */
    public static String adb(List<String> cmd) {
        cmd.add(0, "adb");
        return command(cmd);
    }

    /**
     * Run shell command starting with 'adb'
     *
     * @param cmd command to execute ,tokenize with whitespace
     * @return  Any Output
     */
    public static String adb(String cmd) {
        String[] splits = cmd.split(" ");
        return adb(splits);
    }

    /**
     * Run shell command starting with 'adb'
     *
     * @param cmd command string array to execute
     * @return  Any Output
     */
    public static String adb(String... cmd) {
        ArrayList<String> cmds = new ArrayList<>();
        cmds.add("adb");
        for (String part:cmd
                ) {
            cmds.add(part) ;
        }
        return command(cmds);
    }

    /**
     * Run shell command starting with 'adb shell'
     *
     * @param cmd command list to execute
     * @return running Any Output
     */
    public static String adbShell(List<String> cmd) {
        cmd.add(0, "shell");
        cmd.add(0, "adb");
        return command(cmd);
    }

    /**
     * Run shell command starting with 'adb shell'
     *
     * @param cmd command to execute ,tokenize with whitespace
     * @return  Any Output
     */

    public static String adbShell(String cmd) {
        String[] splits = cmd.split(" ");
        return adbShell(splits);
    }

    /**
     * Run shell command starting with 'adb shell'
     *
     * @param cmd command string array to execute
     * @return  Any Output
     */
    public static String adbShell(String... cmd) {
        ArrayList<String> cmds = new ArrayList<>();
        cmds.add("adb");
        cmds.add("shell");
        for (String part:cmd
                ) {
            cmds.add(part) ;
        }
        return command(cmds);
    }

    /**
     * Execute a shell command and return its output
     *
     * @param command command to execute
     * @return process executed
     */
    public static String command(List<String> command) {
        //set redirectErrorStream to be true to cross output streams
        ProcessBuilder pb = new ProcessBuilder(command).redirectErrorStream(true);
        String output = "";

        try {
            Process process = pb.start();

            IOThreadHandler outputHandler = new IOThreadHandler(
                    process.getInputStream());

            outputHandler.start();

            //wait for the process to stop
            process.waitFor();

            //in case the process stopped before the thread
            outputHandler.join();

            output = outputHandler.getOutput();

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return output;

    }

    /**
     * Thread to drain the output of cmd running
     */
    private static class IOThreadHandler extends Thread {
        private InputStream inputStream;
        private StringBuilder output = new StringBuilder();

        IOThreadHandler(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        public void run() {
            try (Scanner br = new Scanner(new InputStreamReader(inputStream))) {
                String line = null;
                while (br.hasNextLine()) {
                    line = br.nextLine();
                    output.append(line).append(System.getProperty("line.separator"));
                }
            }
        }

        public String getOutput() {
            return output.toString();
        }
    }


    //mininal unit test
    public static void main(String[] args) {
        ArrayList<String> cmds = new ArrayList<>();
        cmds.add("devices");
        System.out.println(ShellKit.adb(cmds));

        cmds.clear();
        cmds.add("getprop");
        cmds.add("ro.boot.serialno");

        System.out.println(ShellKit.adbShell(cmds));
    }
}
