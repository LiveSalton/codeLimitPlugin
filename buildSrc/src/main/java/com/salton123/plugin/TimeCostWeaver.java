package com.salton123.plugin;

import com.android.build.gradle.AppExtension;
import com.quinn.hunter.transform.asm.BaseWeaver;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/6/12 17:22
 * ModifyTime: 17:22
 * Description:
 */
class TimeCostWeaver extends BaseWeaver {
    private TimeCostProp mTimeCostProp;

    public TimeCostWeaver(AppExtension appExtension) {
    }

    @Override
    public void setExtension(Object extension) {
        super.setExtension(extension);
        mTimeCostProp = (TimeCostProp) extension;
    }

    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        if (mTimeCostProp != null && mTimeCostProp.isOpen) {
            return new TimeCostMethodClassAdapter(classWriter,mTimeCostProp);
        }
        return super.wrapClassWriter(classWriter);
    }
}
