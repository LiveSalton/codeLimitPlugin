package com.salton123.plugin;

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/6/12 16:46
 * ModifyTime: 16:46
 * Description:
 */
public class TimeCostProp {
    public static final String NAME = "timeCostProp";
    public boolean isOpen = true;

    public void setThresholdTime(int thresholdTime) {
        this.thresholdTime = thresholdTime;
    }

    public int thresholdTime = 500;

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

}
