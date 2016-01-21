package com.nata.cmd;

import com.nata.ShellKit;
import java.io.File;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 14:15
 */
public class AdbDevice {

    private static final String DUMP_FILE_LOCATION = "/storage/sdcard0/window_dump.xml";

    public AdbDevice() {
        ShellKit.adb("wait-for-adb");
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Run uiautomator dump and pull the uidump.xml to temp file
     */
    public File dumpUI() {
        String tempDir = System.getProperty("java.io.tmpdir");
        String tempDumpfile = tempDir + "dumpfile/dumpfile.xml";

        String output = ShellKit.adbShell("uiautomator", "dump");

        if (!output.equals("")) {
            ShellKit.adb("pull",DUMP_FILE_LOCATION ,tempDumpfile);
        }

        ShellKit.adbShell("rm" , DUMP_FILE_LOCATION);
        return new File(tempDumpfile);
    }

    /**
     * Start a Activity ,can be used to start an app
     *
     * @param component packageName/Activity
     */
    public void startActivity(String component) {
        ShellKit.adbShell("am start -n " + component);
    }

    public static void main(String[] args) {
        AdbDevice device = new AdbDevice();

//        device.dumpUI();
    }




//    /**
//     * 获取设备上当前界面的package和activity
//     *
//     * @return 返回package/activity
//     */
//    public String getFocusedPackageAndActivity() {
//        Pattern pattern = Pattern.compile("([a-zA-Z0-9.]+/.[a-zA-Z0-9.]+)");
//        Process ps = ShellKit.adbShell("dumpsys input | grep FocusedApplication");
//
//        ArrayList<String> component = ReUtil.matchString(pattern, ShellKit.getShellOut(ps));
//
//        // 会有FocusedApplication: <null>情况发生
//        if (component.isEmpty()) {
//            return ReUtil
//                    .matchString(pattern,
//                            ShellKit.getShellOut(ShellKit.adbShell("dumpsys window w | grep \\/ | grep name=")))
//                    .get(0);
//        }
//
//        return component.get(0);
//    }
//
//
//    /**
//     * 获取设备上当前界面的包名
//     *
//     * @return 返回包名
//     */
//    public String getCurrentPackageName() {
//        return getFocusedPackageAndActivity().split("/")[0];
//    }
//
//    /**
//     * 获取设备上当前界面的activity
//     *
//     * @return 返回activity名
//     */
//    public String getCurrentActivity() {
//        return getFocusedPackageAndActivity().split("/")[1];
//    }
//
//    /**
//     * 获取设备屏幕的分辨率
//     *
//     * @return 返回分辨率数组
//     */
//    public int[] getScreenResolution() {
//        Pattern pattern = Pattern.compile("([0-9]+)");
//        Process ps = ShellKit.adbShell("dumpsys display | grep PhysicalDisplayInfo");
//        ArrayList<Integer> out = ReUtil.matchInteger(pattern, ShellKit.getShellOut(ps));
//
//        int[] resolution = new int[]{out.get(0), out.get(1)};
//
//        return resolution;
//    }
//
//
//
//
//    /**
//     * 退出当前应用
//     */
//    public void quitCurrentApp() {
//        ShellKit.adbShell("am force-stop " + getCurrentPackageName());
//    }
//
//    /**
//     * 重置当前应用，清除当前应用的数据且重启该应用
//     */
//    public void resetApp() {
//        String component = getFocusedPackageAndActivity();
//        clearAppDate(getCurrentPackageName());
//        startActivity(component);
//
//    }



//    /**
//     * 使用拨号器拨打号码
//     *
//     * @param number 电话号码
//     */
//    public void callPhone(int number) {
//        ShellKit.adbShell("am start -a android.intent.action.CALL -d tel:" + number);
//    }
//
//    /**
//     * 发送一个按键事件
//     *
//     * @param keycode 键值
//     */
//    public void sendKeyEvent(int keycode) {
//        ShellKit.adbShell("input keyevent " + keycode);
//        sleep(500);
//    }
//
//    /**
//     * 清除文本
//     *
//     * @param text 清除文本框中的text
//     */
//    public void clearText(String text) {
//        int length = text.length();
//        for (int i = length; i > 0; i--) {
//            sendKeyEvent(AndroidKeyCode.BACKSPACE);
//        }
//    }
//

//
//    /**
//     * 发送一个点击事件
//     *
//     * @param x x坐标
//     * @param y y坐标
//     */
//    public void tap(int x, int y) {
//        ShellKit.adbShell("input tap " + x + " " + y);
//        sleep(500);
//    }
//
//    /**
//     * 发送一个点击事件
//     *
//     * @param x x小于1，自动乘以分辨率转换为实际坐标，大于1，当做实际坐标处理
//     * @param y y小于1，自动乘以分辨率转换为实际坐标，大于1，当做实际坐标处理
//     */
//    public void tap(double x, double y) {
//        double[] coords = ratio(x, y);
//        ShellKit.adbShell("input tap " + coords[0] + " " + coords[1]);
//        sleep(500);
//    }
//
//    private double[] ratio(double x, double y) {
//        int[] display = getScreenResolution();
//        double[] coords = new double[2];
//
//        if (x < 1) {
//            coords[0] = display[0] * x;
//        } else {
//            coords[0] = x;
//        }
//
//        if (y < 1) {
//            coords[1] = display[1] * y;
//        } else {
//            coords[1] = y;
//        }
//
//        return coords;
//    }
//
//    private double[] ratio(double startX, double startY, double endX, double endY) {
//        int[] display = getScreenResolution();
//        double[] coords = new double[4];
//
//        if (startX < 1) {
//            coords[0] = display[0] * startX;
//        } else {
//            coords[0] = startX;
//        }
//
//        if (startY < 1) {
//            coords[1] = display[1] * startY;
//        } else {
//            coords[1] = startY;
//        }
//
//        if (endX < 1) {
//            coords[2] = display[0] * endX;
//        } else {
//            coords[2] = endX;
//        }
//
//        if (endY < 1) {
//            coords[3] = display[1] * endY;
//        } else {
//            coords[3] = endY;
//        }
//
//        return coords;
//    }
//
//    /**
//     * 获取设备中SDK的版本号
//     *
//     * @return 返回SDK版本号
//     */
//    public int getSdkVersion() {
//        Process ps = ShellKit.adbShell("getprop ro.build.version.sdk");
//        String sdkVersion = ShellKit.getShellOut(ps);
//        return new Integer(sdkVersion);
//    }
//
//    /**
//     * 发送一个滑动事件
//     *
//     * @param startX 起始x坐标
//     * @param startY 起始y坐标
//     * @param endX   结束x坐标
//     * @param endY   结束y坐标
//     * @param ms     持续时间
//     */
//    public void swipe(double startX, double startY, double endX, double endY, long ms) {
//
//        double[] coords = ratio(startX, startY, endX, endY);
//        if (getSdkVersion() < 19) {
//            ShellKit.adbShell("input swipe " + coords[0] + " " + coords[1] + " " + coords[2] + " " + coords[3]);
//        } else {
//            ShellKit.adbShell("input swipe " + coords[0] + " " + coords[1] + " " + coords[2] + " " + coords[3] + " " + ms);
//        }
//
//        sleep(500);
//    }
//
//    /**
//     * 左滑屏幕
//     */
//    public void swipeToLeft() {
//        swipe(0.8, 0.5, 0.2, 0.5, 500);
//    }
//
//    /**
//     * 右滑屏幕
//     */
//    public void swipeToRight() {
//        swipe(0.2, 0.5, 0.8, 0.5, 500);
//    }
//
//    /**
//     * 上滑屏幕
//     */
//    public void swipeToUp() {
//        swipe(0.5, 0.7, 0.5, 0.3, 500);
//    }
//
//    /**
//     * 下滑屏幕
//     */
//    public void swipeToDown() {
//        swipe(0.5, 0.3, 0.5, 0.7, 500);
//    }
//
//    /**
//     * 发送一个长按事件
//     *
//     * @param x x坐标
//     * @param y y坐标
//     */
//    public void longPress(double x, double y) {
//        swipe(x, y, x, y, 1500);
//    }
//
//    /**
//     * 发送一段文本，只支持英文，多个空格视为一个空格
//     *
//     * @param text 英文文本
//     */
//    public void sendText(String text) {
//        String[] str = text.split(" ");
//        ArrayList<String> out = new ArrayList<String>();
//        for (String string : str) {
//            if (!string.equals("")) {
//                out.add(string);
//            }
//        }
//
//        int length = out.size();
//        for (int i = 0; i < length; i++) {
//            ShellKit.adbShell("input text " + out.get(i));
//            sleep(100);
//            if (i != length - 1) {
//                sendKeyEvent(AndroidKeyCode.SPACE);
//            }
//        }
//    }
//
//    /**
//     * 清除应用的用户数据
//     *
//     * @param packageName 应用的包名
//     * @return 清楚成功返回true, 否则返回false
//     */
//    public boolean clearAppDate(String packageName) {
//        Process ps = ShellKit.adbShell("pm clear " + packageName);
//        if (ShellKit.getShellOut(ps).equals("Success")) {
//            return true;
//        }
//        return false;
//    }




}
