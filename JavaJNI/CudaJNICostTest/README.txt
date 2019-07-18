func has a parameter：Int[]  -->  Java pass this parameter to C++, let C++ process it.

#include "calculate.cuh"

__global__ void gpuCalculate(int* d_arr) {
    int idx = threadIdx.x + blockIdx.x * blockDim.x;
    d_arr[idx]++;
}

void callCudaFunc(int* carr, int n) {
    int* d_arr;
    cudaMalloc((void**)& d_arr, n * sizeof(int));
    cudaMemcpy(d_arr, carr, n*sizeof(int), cudaMemcpyHostToDevice);

    gpuCalculate<<<100000, 1000>>>(d_arr);

    cudaMemcpy(carr, d_arr, n*sizeof(int), cudaMemcpyDeviceToHost);
}


Java:
  JNI_Func.java --> JNI_Func.class --> JNI_Func.h --> jni_calculate.cu --> libJniCalculate.so --> CudaJNICostTest.java --> CudaJNICostTest.class
  {
    int[] arr = ...
    ...
    callCudaFunc(arr)
    ...
  }

C++:
  calculate.cuh calculate.cu main.cu--> main.out
  {
    int* arr = ...
    ...
    callCudaFunc(arr)
    ...
  }

Linux:
  Java Cost: 390+ms(Get/ReleasePrimitiveArrayCritical)
  C++  Cost: 390+ms
