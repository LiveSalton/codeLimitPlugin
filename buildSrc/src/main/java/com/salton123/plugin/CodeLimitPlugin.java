package com.salton123.plugin;

import com.android.build.gradle.AppExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

public class CodeLimitPlugin implements Plugin<Project> {

    private String TAG = "CodeLimitPlugin";

    @Override
    public void apply(Project project) {
        System.out.println("========================");
        System.out.println("start code limit plugin1:" + System.currentTimeMillis());
        project.getExtensions().create("codelimit", CodeLimitExtension.class);
        System.out.println("register code limit extension");
        AppExtension android = project.getExtensions().findByType(AppExtension.class);
        android.registerTransform(new CodeLimitTransform(project));
        System.out.println("register code limit transform");

        // project.task("codelimit").doLast(task -> System.out.println("Hi this is micky's plugin"));
        // project.task("helloPlugin", new Action<Task>() {
        //     @Override
        //     public void execute(Task task) {
        //         new HelloTask();
        //     }
        // });
        System.out.println("========================");


    }
}
