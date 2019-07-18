#include "cuda_runtime.h"
#include "device_launch_parameters.h"
#include <malloc.h>
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


__global__ void gpuCalculate(int* d_arr) {
	int idx = threadIdx.x + blockIdx.x * blockDim.x;
	//if (idx < 100000000)
	d_arr[idx]++;
}

void callCudaFunc(int* carr, int n) {
	int* d_arr;
	cudaMalloc((void**)& d_arr, n * sizeof(int));
	cudaMemcpy(d_arr, carr, n*sizeof(int), cudaMemcpyHostToDevice);

	gpuCalculate <<<100000, 1000 >>> (d_arr);

	cudaMemcpy(carr, d_arr, n*sizeof(int), cudaMemcpyDeviceToHost);
}

// 创建一个阻止GC的临界区，将数组的真实数据直接暴露给用户
JNIEXPORT void JNICALL Java_JNI_1Func_calculate
  (JNIEnv * env, jobject obj, jintArray arr, jint n) {
        int* carr = static_cast<int*>(env->GetPrimitiveArrayCritical(arr, 0));

        callCudaFunc(carr, n);

        env->ReleasePrimitiveArrayCritical(arr, carr, 0);
}
