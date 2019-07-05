#include "cuda_runtime.h"
#include "device_launch_parameters.h"
#include <malloc.h> 
#include <stdio.h>
#include "CudaJNI.h"
static int k = 0;
__global__ void helloFromGPU() {
	printf("Hello World from GPU! this is block (%d,%d,%d) thread (%d,%d,%d)\n",
		blockIdx.x, blockIdx.y, blockIdx.z, threadIdx.x, threadIdx.y, threadIdx.z);
}

__global__ void printMem(int* p) {
	printf("Hello World from GPU!");
	for (int i = 0; i < 10; i++) {
		printf("this is %d\n", p[i]);
	}
}
__global__ void printID() {
	printf("Hello World from GPU! this is thread %d ");
}
int printHelloWorld(void) {

	int* d_data = 0;
	int h_data[] = { 1,2,3,4,5,6,7,8,9,10 };
	cudaError_t cudaStatus = cudaMalloc((void**)& d_data, 10 * sizeof(int));
	cudaStatus = cudaMemset(&d_data, 3, 10 * sizeof(int));
	helloFromGPU << <2, 5 >> > ();
	cudaDeviceReset();
	char z;
	scanf("%c", &z);
Error:
	return 0;
	return 0;
}
JNIEXPORT jint JNICALL Java_CudaJNI_printHelloWorldByGPU
(JNIEnv*, jobject) {
	printHelloWorld();
	return 1;
}