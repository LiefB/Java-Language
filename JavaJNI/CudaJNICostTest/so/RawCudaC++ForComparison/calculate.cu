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

