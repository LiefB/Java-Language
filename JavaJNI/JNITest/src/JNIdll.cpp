//JNIdll.cpp文件

#include<stdio.h>
#include<jni.h>
#include "JNI.h"

JNIEXPORT jint JNICALL Java_JNI_call
  (JNIEnv *, jobject){
      //实现代码
      int i = 777;
      return i;
}