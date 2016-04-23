package com.nata.server;


import blade.kit.json.JSONObject;
import com.blade.Blade;
import com.nata.AdbDevice;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-22 19:24
 */
public class App {
    public static void main(String[] args) {
        Blade blade = Blade.me();

        blade.before("/.*", (request, response) -> {
            response.header("Access-Control-Allow-Origin","*");
            response.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        });


        blade.get("/device", (request, response) -> {
            AdbDevice adbDevice = new AdbDevice();
            String deviceName = adbDevice.getModel();
            String deviceId = adbDevice.getDeviceId();
            String androidVersion = adbDevice.getAndroidVersion();
            int sdkVersion = adbDevice.getSdkVersion();
            int[] resolution = adbDevice.getScreenResolution();
            String cpuAbi = adbDevice.getCpuABI();
            String manufacturer =adbDevice.getManufacturer();

            JSONObject res = new JSONObject();
            res.put("name",deviceName.trim());
            res.put("id",deviceId.trim());
            res.put("version",androidVersion.trim());
            res.put("sdk",sdkVersion);
            res.put("resolution",resolution[0] + "x" + resolution[1]);
            res.put("cpu",cpuAbi.trim());
            res.put("manufacturer",manufacturer.trim());
            res.put("status", 200);

            response.json(res.toString());

        });

        blade.post("/start", (request, response) -> {

        });


        blade.listen(9001).start();
    }
}

