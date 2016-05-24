package com.nata.server;


import blade.kit.json.JSONObject;
import com.blade.Blade;
import com.nata.AdbDevice;
import com.nata.Config;
import com.nata.action.Action;
import com.nata.element.DumpService;
import com.nata.element.Widget;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-22 19:24
 */
public class App {

    public static void main(String[] args) {
        Blade blade = Blade.me();
        RunnerFactory runnerFactory = new RunnerFactory();

        blade.before("/.*", (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        });

        blade.get("/device/online", (request, response) -> {
            String device_id = request.query("device_id").trim();
            AdbDevice adbDevice = new AdbDevice();
            String deviceId = adbDevice.getDeviceId().trim();

            if (device_id.equals(deviceId)) {
                response.status(200).text("online");
            } else {
                response.status(404).text("not online");
            }
        });


        blade.get("/device", (request, response) -> {
            AdbDevice adbDevice = new AdbDevice();
            String deviceName = adbDevice.getModel();
            String deviceId = adbDevice.getDeviceId();
            String androidVersion = adbDevice.getAndroidVersion();
            int sdkVersion = adbDevice.getSdkVersion();
            int[] resolution = adbDevice.getScreenResolution();
            String cpuAbi = adbDevice.getCpuABI();
            String manufacturer = adbDevice.getManufacturer();

            JSONObject res = new JSONObject();
            res.put("name", deviceName.trim());
            res.put("id", deviceId.trim());
            res.put("version", androidVersion.trim());
            res.put("sdk", sdkVersion);
            res.put("resolution", resolution[0] + "x" + resolution[1]);
            res.put("cpu", cpuAbi.trim());
            res.put("manufacturer", manufacturer.trim());

            response.status(200).json(res.toString());

        });

        /**
         * app_name=%E5%87%A4%E5%9F%8E%E5%8D%AB%E5%A3%AB&package_name=com.cvicse.zhnt&activity_name=.LoadingActivity&action_count=1000&algorithm=dfs&device_id=DU2SSE1478031311
         create.js:49 success
         */
        blade.post("/start", (request, response) -> {
            if (!runnerFactory.isValid()) {
                response.status(406).text("error");
            }

            Config config = new Config();
            config.setRemote(true);
            config.setRecord_id(request.query("record_id"));
            config.setApp_name(request.query("app_name"));
            config.setPackage_name(request.query("package_name"));
            config.setActivity_name(request.query("activity_name"));
            config.setAction_count(request.queryAsInt("action_count"));
            config.setAlgorithm(request.query("algorithm"));
            config.setDevice_id(request.query("device_id"));
            config.setSetup(request.query("setup"));
            config.setBlacklist(request.query("blacklist"));

            runnerFactory.createRunnerAndRun(config);
            response.status(200).text("success");
        });


        // not thread safe
        blade.post("/stop", (request, response) -> {
            runnerFactory.stopTask();
            response.status(200).text("success");
        });

        blade.post("/action",((request, response) -> {
            String action = request.query("action").trim();
            if(Replayer.playAction(action)){
                response.status(200).text("success");
            }else{
                response.status(406).text("error");
            }

        }));

        blade.post("/actions",((request, response) -> {
            String actions = request.query("actions").trim();
            System.out.println(actions);
            if(Replayer.playActions(actions)){
                response.status(200).text("success");
            }else{
                response.status(406).text("error");
            }

        }));


        blade.get("/actions",(request, response) -> {
            String actionString =  ActionMaker.getActions();
            response.status(200).text(actionString);
        });


        blade.listen(9001).start();
    }
}

