//JNIdll.cpp文件

#include<stdio.h>
#include<malloc.h>
#include<jni.h>
#include "JNI.h"

JNIEXPORT void JNICALL Java_JNI_call
  (JNIEnv* env, jobject obj, jobject buffer, jint len){
	  int* buf = (int*) env->GetDirectBufferAddress(buffer);
      for (int i = 0; i < len; i++) {
		  buf[i] = i;
	  }
}