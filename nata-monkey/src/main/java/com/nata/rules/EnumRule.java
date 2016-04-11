package com.nata.rules;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-03 15:17
 */
public class EnumRule {
    private String resourceId;
    private List<String> enums = new ArrayList<>();

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public List<String> getEnums() {
        return enums;
    }

    public void setEnums(List<String> enums) {
        this.enums = enums;
    }

    public void addEnum(String enumValue){
        enums.add(enumValue);
    }

    @Override
    public String toString() {
        return "EnumRule{" +
                "resourceId='" + resourceId + '\'' +
                ", enums=" + enums +
                '}';
    }
}
