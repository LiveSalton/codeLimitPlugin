package com.salton123.plugin;

import com.android.build.gradle.AppExtension;
import com.quinn.hunter.transform.HunterTransform;

import org.gradle.api.Project;

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/6/12 16:55
 * ModifyTime: 16:55
 * Description:
 */
public class TimeCostTransform extends HunterTransform {
    private TimeCostProp mTimeCostProp;
    private AppExtension mAppExtension;

    public TimeCostTransform(Project project) {
        super(project);
        this.mAppExtension = (AppExtension) project.getProperties().get("android");
        this.mTimeCostProp = (TimeCostProp) project.getExtensions().getByName(TimeCostProp.NAME);
        this.bytecodeWeaver = new TimeCostWeaver(this.mAppExtension);
        this.bytecodeWeaver.setExtension(mTimeCostProp);
    }
}
