package com.nata.rules;


import com.alibaba.fastjson.JSON;

import java.io.*;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-30 21:00
 */
public class RuleParser {

    public Rule parse(String jsonString) {
        return  JSON.parseObject(jsonString,Rule.class);
    }

    public static Rules parse(File jsonFile){
        Rules rules = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(jsonFile));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            rules = JSON.parseObject(sb.toString(),Rules.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rules;
    }

    public static void main(String[] args) {
        File file = new File("rules/zhihu.json");
        RuleParser parser = new RuleParser();
        Rules rules = parser.parse(file);
    }
}
