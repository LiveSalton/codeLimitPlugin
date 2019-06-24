package com.salton123.plugin;

import java.io.File;

/**
 * User: newSalton@outlook.com
 * Date: 2019/6/19 17:26
 * ModifyTime: 17:26
 * Description:
 */
public class FileUtils {
    public static void eachFileRecurse(File fromFile, FileAction action) {
        if (fromFile == null) {
            return;
        }
        File file = fromFile;
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                // System.out.println("no file found!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        // System.out.println("dir:" + file2.getAbsolutePath());
                        eachFileRecurse(file2, action);
                    } else {
                        action.onFileFound(file2);
                        // System.out.println("file:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            // System.out.println("no file found!");
        }
    }

    interface FileAction {
        void onFileFound(File file);
    }

}
