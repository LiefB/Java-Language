package com.imooc.reflect;

public class Class_getName {
    public static void main(String[] args){

        Class c1 = int.class;//int的类类型
        Class c2 = String.class;//String类的类类型
        Class c3 = double.class;
        Class c4 = Double.class;
        Class c5 = void.class;

        System.out.println(c1.getName());
        System.out.println(c2.getName());
        System.out.println(c2.getSimpleName());//不包含报名的类的名称

    }
}
