#include <stdio.h>
#include <ctime>
#include "calculate.h"

int main(char* args, int argv) {
	int n = 100000000;
	int* arr = new int[100000000];
	for (int i = 0; i < n; i++) {
		arr[i] = i;
	}

	clock_t startTime = clock();
	calculate(arr, n);
	clock_t endTime = clock();
	printf("%lf", (double)(endTime - startTime)/CLOCKS_PER_SEC);

	return 0;
}
