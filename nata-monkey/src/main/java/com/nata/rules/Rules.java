package com.nata.rules;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-30 21:01
 */
public class Rules {
    private List<KeyValueRule> keyvalues  = new ArrayList<>();
    private List<EnumRule> enums = new ArrayList<>();

    public List<KeyValueRule> getKeyvalues() {
        return keyvalues;
    }

    public void setKeyvalues(List<KeyValueRule> keyvalues) {
        this.keyvalues = keyvalues;
    }

    public void addKeyValue(KeyValueRule rule){
        keyvalues.add(rule);
    }

    public List<EnumRule> getEnums() {
        return enums;
    }

    public void setEnums(List<EnumRule> enums) {
        this.enums = enums;
    }

    public void addEnum(EnumRule rule){
        enums.add(rule);
    }
}
