package com.salton123.asm;

import android.util.Log;

import java.util.HashMap;

public class TimeCache {
    public static HashMap<String, Long> sStartTime = new HashMap<>();

    public static void setStartTime(String methodName, long time) {
        sStartTime.put(methodName, time);
    }

    public static void setEndTime(String methodName, long end) {
        if (sStartTime != null) {
            Object object = sStartTime.get(methodName);
            if (object != null) {
                long start = (long) object;
                long dex = end - start;
                String text = "method: " + methodName  + " cost " + dex + " ns";
                System.out.println(text);
                Log.e("TimeCache", text);
                sStartTime.remove(methodName);
            }
        }
    }
}
