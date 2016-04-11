package com.nata.rules;

import com.alibaba.fastjson.JSON;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-11 15:24
 */
public class RuleEncoder {

    public static void main(String[] args) {
        Rules rules = new Rules();

        KeyValueRule passwordRule = new KeyValueRule();
        passwordRule.setResouceId("com.zhihu.android:id/password");
        passwordRule.setValue("mcl896345253");
        passwordRule.setType("keyvalue");

        rules.addKeyValue(passwordRule);

        KeyValueRule userNameRule = new KeyValueRule();
        userNameRule.setResouceId("com.zhihu.android:id/username");
        userNameRule.setValue("15996270647");
        userNameRule.setType("keyvalue");

        rules.addKeyValue(userNameRule);

        EnumRule fullnameRule = new EnumRule();
        fullnameRule.setResourceId("com.zhihu.android:id/fullname");
        fullnameRule.addEnum("测试一");
        fullnameRule.addEnum("测试二");
        fullnameRule.addEnum("测试三");

        rules.addEnum(fullnameRule);

        String jsonString = JSON.toJSONString(rules);

        System.out.println(jsonString);
    }
}
