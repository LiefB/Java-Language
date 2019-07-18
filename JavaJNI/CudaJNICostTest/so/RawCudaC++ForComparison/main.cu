#include <stdio.h>
#include <ctime>
#include "calculate.cuh"

/*
__global__ void gpuCalculate(int* d_arr) {
    int idx = threadIdx.x + blockIdx.x * blockDim.x;
    d_arr[idx]++;
}

void callCudaFunc(int* carr, int n) {
    int* d_arr;
    cudaMalloc((void**)& d_arr, n * sizeof(int));
    cudaMemcpy(d_arr, carr, n*sizeof(int), cudaMemcpyHostToDevice);

    gpuCalculate<<<100000, 1000>>>(d_arr);

    cudaMemcpy(carr, d_arr, n*sizeof(int), cudaMemcpyHostToDevice);
}
*/

int main(int argc, char** argv) {
    int n = 100000000;
    int* arr = new int[100000000];
    for (int i = 0; i < n; i++) {
        arr[i] = i;
    }

    clock_t startTime = clock();
    callCudaFunc(arr, n);
    clock_t endTime = clock();
    printf("%lf\n", (double)(endTime - startTime)/CLOCKS_PER_SEC);

    for (int i = 0; i < 10; i++) {
        printf("%d\n", arr[i]);
    }    

    for (int i = 0; i < n; i++) {
        if (arr[i] != i + 1) {
	    printf("Something Wrong......\n");
            break;
        }
    }
    return 0;
}
