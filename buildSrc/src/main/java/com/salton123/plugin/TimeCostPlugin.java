package com.salton123.plugin;

import com.android.build.gradle.AppExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.Collections;

class TimeCostPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        AppExtension appExtension = (AppExtension) project.getProperties().get("android");
        //扩展参数
        project.getExtensions().create(TimeCostProp.NAME, TimeCostProp.class);
        //慢函数
        appExtension.registerTransform(new TimeCostTransform(project), Collections.EMPTY_LIST);
    }
}