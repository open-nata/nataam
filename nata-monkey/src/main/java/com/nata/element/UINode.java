package com.nata.element;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-26 20:51
 */
public class UINode {
    private String text;
    private String resourceId;
    private String className;
    private String contentDesc;
    private String checkable;
    private String checked;
    private String clickable;
    private String bounds;
    private String packageName;
    private String enabled;
    private String focusable;
    private String focused;
    private String scrollable;
    private String long_clickable;
    private String password;
    private String selected;

    public String getFocused() {
        return focused;
    }

    public void setFocused(String focused) {
        this.focused = focused;
    }

    public String getScrollable() {
        return scrollable;
    }

    public void setScrollable(String scrollable) {
        this.scrollable = scrollable;
    }

    public String getLong_clickable() {
        return long_clickable;
    }

    public void setLong_clickable(String long_clickable) {
        this.long_clickable = long_clickable;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public UINode() {
    }

    public void setFocusable(String focusable) {
        this.focusable = focusable;
    }

    public String getFocusable() {
        return focusable;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBounds() {
        return bounds;
    }

    public void setBounds(String bounds) {
        this.bounds = bounds;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }

    public String getCheckable() {
        return checkable;
    }

    public void setCheckable(String checkable) {
        this.checkable = checkable;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getClickable() {
        return clickable;
    }

    public void setClickable(String clickable) {
        this.clickable = clickable;
    }

    @Override
    public String toString() {
        return "UINode{" +
                "text='" + text + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", className='" + className + '\'' +
                ", contentDesc='" + contentDesc + '\'' +
                ", checkable='" + checkable + '\'' +
                ", checked='" + checked + '\'' +
                ", clickable='" + clickable + '\'' +
                ", bounds='" + bounds + '\'' +
                ", packageName='" + packageName + '\'' +
                ", enabled='" + enabled + '\'' +
                ", focusable='" + focusable + '\'' +
                ", focused='" + focused + '\'' +
                ", scrollable='" + scrollable + '\'' +
                ", long_clickable='" + long_clickable + '\'' +
                ", password='" + password + '\'' +
                ", selected='" + selected + '\'' +
                '}';
    }
}
