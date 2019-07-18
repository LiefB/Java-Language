#ifndef CALCULATE_CUH
#define CALCULATE_CUH

#include "cuda_runtime.h"
#include "device_launch_parameters.h"

__global__ void gpuCalculate(int* d_arr);

void callCudaFunc(int* carr, int n);


#endif
