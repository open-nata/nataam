package com.nata.monkeys;

import com.nata.AndroidKeyCode;
import com.nata.cmd.AdbDevice;
import com.nata.dictionary.TextValueDictionary;
import com.nata.utils.LogUtil;
import java.util.Random;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-06 18:08
 */
public class PureRandomMonkey extends AbstractMonkey{

    private Random random = new Random();
    private String[] actions= {"TAP","SWIPE","BACK","HOME","MENU","LONG_CLICK","TEXT","RESTART"};

    private int screenX;
    private int screenY;
    private String lastAction;

    public PureRandomMonkey(int actionCount,String pkg, String act, AdbDevice device) {
        super("PureRandomMonkey",actionCount,pkg, act, device);
        int []resolutions = device.getScreenResolution();
        screenX = resolutions[0];
        screenY = resolutions[1];
    }

    public PureRandomMonkey(String pkg, String act, AdbDevice device){
        this(1000,pkg,act,device);
    }

    public void tap(){
        int randomX = random.nextInt(screenX);
        int randomY = random.nextInt(screenY);
        getDevice().tap(randomX,randomY);
        lastAction = "TAP";
    }

    public void swipe(){
        int index = random.nextInt(4);
        switch (index){
            case 0 : getDevice().swipeToUp();break;
            case 1 : getDevice().swipeToRight();break;
            case 2 : getDevice().swipeToDown();break;
            case 3 : getDevice().swipeToLeft();break;
        }
        lastAction = "SWIPE";
    }

    public void back(){
        getDevice().sendKeyEvent(AndroidKeyCode.BACK);
        lastAction = "BACK";
    }

    public void home(){
        getDevice().sendKeyEvent(AndroidKeyCode.HOME);
        lastAction = "HOME";
    }

    public void menu(){
        getDevice().sendKeyEvent(AndroidKeyCode.MENU);
        lastAction = "MENU";
    }

    public void longclick(){
        int randomX = random.nextInt(screenX);
        int randomY = random.nextInt(screenY);
        getDevice().longPress(randomX,randomY);
        lastAction = "LONG_CLICK";
    }

    public void text(){
        String text = TextValueDictionary.getInstance().getRandomValidValue();
        getDevice().sendText(text);
        getDevice().hideSoftKeyBoard();
        lastAction = "TEXT";
    }

    public void restart(){
        getDevice().startActivity(getPkgAct());
        lastAction = "RESTART";
    }

    @Override
    public void play() {
        startApp();
        int cnt = 0;
        while((++cnt) <= ACTION_COUNTS){
            if (!isInCurrentPkg()) {
                getBackToApp();
            }

            int index = random.nextInt(actions.length);
            switch(actions[index]){
                case "TAP" : tap();break;
                case "SWIPE" : swipe();break;
                case "BACK" : back();break;
                case "HOME" : home();break;
                case "MENU" : menu();break;
                case "LONG_CLICK" : longclick();break;
                case "TEXT" : text();break;
                case "RESTART": restart();break;
            }
            LogUtil.debug("LastAction : " + lastAction);
            getCurrentState();
            if(cnt % Frequency== 0){
                summary();
            }
        }
    }
}
