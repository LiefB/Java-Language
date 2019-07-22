#include <stdio.h>

// 判断Big-Endian还是Little-Endian
int main(int argc, char** argv) {
    int x = 0x01020304;
    char* p = (char*)&x;
    if (p[0] == 1) {
         printf("Big-Endian\n");
    }
    else{
          printf("Little-Endian\n");
    }
    return 0;
}