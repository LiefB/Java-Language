package com.imooc.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodReflect {
    public static void main(String[] args){
        //要获取print(int, int)方法
        //1.要获取一个方法就是获取类的信息，获取类的信息首先要获取类的类类型
        //A a1 = new A();
        //Class c = a1.getClass();
        Class c = null;
        try {
            c = Class.forName("com.imooc.reflect.A");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        A a1 = null;
        try {
            a1 = (A)c.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        /*
         * 2.获取方法，由名称和参数列表来决定
         * getMethod获取的是public方法
         * getDeclaredMethod获取的是自己声明的方法
         */
        Method m;
        {
            try {
                //m = c.getMethod("print", new Class[]{int.class, int.class});
                m = c.getMethod("print", int.class, int.class);

                //方法的反射操作
                //方法的反射操作是用m对象来进行方法调用，和a1.print(10, 20)调用效果完全相同
                //方法如果没有返回值返回null，有返回值返回具体的返回值
                try {
                    //Object o = m.invoke(a1, new Object[]{10, 20});
                    Object o = m.invoke(a1, 10, 20);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        try {
            //获取方法pring(String, String)
            Method m1 = c.getMethod("print", String.class, String.class);
            //用方法进行反射
            try {
                Object o = m1.invoke(a1, "hello", "world");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
}


class A {

    public void print(int a, int b) {
        System.out.println(a + b);
    }

    public void print(String a, String b) {
        System.out.println(a.toUpperCase() + "," + b.toUpperCase());
    }
}
