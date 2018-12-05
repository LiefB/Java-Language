package com.imooc.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class 集合泛型 {
    public static void main(String[] args){
        ArrayList list1 = new ArrayList();
        ArrayList<String> list2 = new ArrayList<String>();

        list2.add("hello");
        //list2.add(2); 编译错误的

        Class c1 = list1.getClass();
        Class c2 = list2.getClass();
        System.out.println(c1 == c2);
        //反射的操作都是编译之后的操作

        /*
         * c1 == c2结果返回true说明编译之后集合的泛型是去泛型化的
         * Java中集合的泛型，是防止错误输入的，只在编译阶段有效，
         * 绕过编译就无效了
         * 验证：我们可以通过方法的反射操作，绕过编译
         */
        try {
            Method m = c1.getMethod("add", Object.class);
            try {
                m.invoke(list2, 20);
                System.out.println(list2.size());
                System.out.println(list2);
                /*for (String string : list2){
                    System.out.println(string);
                }*///现在不能这样遍历
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
