package com.imooc.reflect;

public class ClassReflect_Method_Field_Constructor {
    public static void main(String[] args){
        String s = "hello";
        ClassUtil.printClassMethodMessage(s);
        System.out.println("=========================================================");
        ClassUtil.printClassFieldMessage(s);
        System.out.println("=========================================================");
        ClassUtil.printClassConstructorMessage(s);
    }
}
