package com.salton123.plugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/6/12 17:25
 * ModifyTime: 17:25
 * Description:
 */
class TimeCostMethodClassAdapter extends ClassVisitor {
    private String className;
    private TimeCostProp mTimeCostProp;

    public TimeCostMethodClassAdapter(ClassVisitor classVisitor, TimeCostProp timeCostProp) {
        super(Opcodes.ASM7, classVisitor);
        mTimeCostProp = timeCostProp;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        boolean isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
        if (isInterface) {
            return;
        }
        this.className = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String methodName, String descriptor, String signature,
                                     String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, methodName, descriptor, signature, exceptions);
        try {
            if (ignorePackageNames(className)) {
                return methodVisitor;
            }
        } catch (Exception e) {
        }
        return new TimeCostAdapter(methodVisitor, className, mTimeCostProp.thresholdTime, access, methodName,
                descriptor);
    }

    public boolean ignorePackageNames(String className) {
        boolean isMatched = false;
        for (String packageName : ignoreArr) {
            if (className.contains(packageName)) {
                isMatched = true;
                break;
            }
        }
        return isMatched;
    }

    private String[] ignoreArr = new String[]{
            "com/salton123/asm"
    };
}
