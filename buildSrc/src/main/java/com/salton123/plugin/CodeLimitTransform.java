package com.salton123.plugin;


import com.android.build.api.transform.Context;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;

import org.gradle.api.Project;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;


/**
 * User: newSalton@outlook.com
 * Date: 2019/6/19 15:49
 * ModifyTime: 15:49
 * Description:
 */
public class CodeLimitTransform extends Transform {

    private static final String TAG = "CodeLimitTransform";
    private Project mProject;

    private static ClassPool pool = ClassPool.getDefault();

    public CodeLimitTransform(Project project) {
        this.mProject = project;
    }

    @Override
    public String getName() {
        return TAG;
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_JARS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        Context context = transformInvocation.getContext();
        //依赖
        Collection<TransformInput> inputs = transformInvocation.getInputs();
        //
        Collection<TransformInput> referencedInputs = transformInvocation.getReferencedInputs();
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();
        boolean isIncremental = transformInvocation.isIncremental();
        System.out.println(TAG + ",transform" + inputs
                + ",referencedInputs:" + referencedInputs
                + ",context:" + context
                + ",outputProvider:" + outputProvider
                + ",isIncremental:" + isIncremental
        );


        inputs.forEach(transformInput -> {

            transformInput.getDirectoryInputs().forEach(directoryInput -> {
                System.out.println(TAG + ",transformInput dir" + directoryInput.getFile().getAbsolutePath());
                FileUtils.eachFileRecurse(directoryInput.getFile(), file -> {
                    String filePath = file.getAbsolutePath();

                    if (filePath.endsWith(".class")
                            && !filePath.contains("R$")
                            && !filePath.contains("R.class")
                            && !filePath.contains("BuildConfig.class")) {
                        System.out.println(TAG + "," + file.getName());
                        if (file.getName().contains("Main")) {
                            String classPath = filePath.split("\\\\debug\\\\")[1];
                            String className = classPath.substring(0, classPath.length() - 6).replace('\\', '.').replace('/', '.');
                            mProject.getLogger().info("className:"+className);
                            // CtClass c = null;
                            // try {
                            //     c = pool.getCtClass(className);
                            // } catch (NotFoundException e) {
                            //     e.printStackTrace();
                            // }
                            // if (c.isFrozen()) {
                            //     c.defrost();
                            // }
                            // CtMethod[] methods = c.getDeclaredMethods();
                            // for (CtMethod method : methods) {
                            //     if (method.isEmpty() || Modifier.isNative(method.getModifiers())) {
                            //         return;
                            //     }
                            // }
                        }
                    }
                });
            });

            transformInput.getJarInputs().forEach(jarInput -> {

            });
        });

    }

    public void eachDirRecurse(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                System.out.println("no file found!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("dir:" + file2.getAbsolutePath());
                        eachDirRecurse(file2.getAbsolutePath());
                    } else {
                        System.out.println("file:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("no file found!");
        }
    }


}
