package com.salton123.plugin;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/6/12 17:38
 * ModifyTime: 17:38
 * Description:
 */
class TimeCostAdapter extends AdviceAdapter {
    private String className;
    private int threadholdTime;
    private boolean isStaticMethod;
    private String methodName;

    /**
     * Constructs a new {@link AdviceAdapter}.
     *
     * @param methodVisitor the method visitor to which this adapter delegates calls.
     * @param access        the method's access flags (see {@link Opcodes}).
     * @param methodName    the method's name.
     * @param descriptor    the method's descriptor (see {@link Type Type}).
     */
    protected TimeCostAdapter(
            MethodVisitor methodVisitor,
            String className,
            int thresholdTime,
            int access,
            String methodName,
            String descriptor) {
        super(Opcodes.ASM7, methodVisitor, access, methodName, descriptor);
        this.className = className;
        this.threadholdTime = thresholdTime;
        this.methodName = methodName;
        //access值得计算方式为 Opcodes.ACC_PUBLIC & Opcodes.ACC_STATIC
        this.isStaticMethod = (access & Opcodes.ACC_STATIC) != 0;
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        System.out.println("onMethodEnter:" + className + methodName);
        try {
            try {
                mv.visitLdcInsn(methodName);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                mv.visitMethodInsn(INVOKESTATIC, "com/salton123/asm/TimeCache", "setStartTime", "(Ljava/lang" +
                        "/String;J)V", false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        System.out.println("onMethodExit:" + opcode);
        try {
            try {
                mv.visitLdcInsn(methodName);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                mv.visitMethodInsn(INVOKESTATIC, "com/salton123/asm/TimeCache", "setEndTime", "(Ljava/lang/String;" +
                        "J)V", false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
