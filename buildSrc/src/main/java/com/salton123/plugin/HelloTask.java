package com.salton123.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

/**
 * User: newSalton@outlook.com
 * Date: 2019/6/19 15:31
 * ModifyTime: 15:31
 * Description:
 */
public class HelloTask extends DefaultTask {
    @TaskAction
    void output(){
        System.out.println("helloTask");
    }
}
