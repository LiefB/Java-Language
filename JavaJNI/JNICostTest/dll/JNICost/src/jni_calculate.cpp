#include <stdio.h>
#include <jni.h>
#include "JNI_Func.h"

// 考虑安全性，不直接暴露Java堆上内存地址，而是返回一个数组副本，再将副本复制回Java堆中真实数组中
/*
JNIEXPORT void JNICALL Java_JNI_1Func_calculate
  (JNIEnv * env, jobject obj, jintArray arr, jint n) {
        jint* carr;
        carr = env->GetIntArrayElements(arr, 0);
        if (carr == NULL)
                return;
        for (int i = 0; i < n; i++) {
                carr[i]++;
        }
        env->ReleaseIntArrayElements(arr, carr, 0);
}
*/

// 创建一个阻止GC的临界区，将数组的真实数据直接暴露给用户
JNIEXPORT void JNICALL Java_JNI_1Func_calculate
  (JNIEnv * env, jobject obj, jintArray arr, jint n) {
        int* carr = static_cast<int*>(env->GetPrimitiveArrayCritical(arr, 0));
        for (int i = 0; i < n; i++) {
                carr[i]++;
        }
        env->ReleasePrimitiveArrayCritical(arr, carr, 0);
}