func has a parameter：Int[]  -->  Java pass this parameter to C++, let C++ process it.

Java:
  JNI_Func.java --> JNI_Func.class --> JNI_Func.h --> jni_calculate.cpp --> jni_calculate.dll/.so --> JNICost.java --> JNICost.class
  {
    int[] arr = ...
    ...
    Jni_Call(arr)
    ...
  }

C++:
  calculate.h calculate.cpp --> calculate.o --> main.cpp 
  {
    int* arr = ...
    ...
    fun_call(arr)
    ...
  }


Windows:
  Java Cost: 400ms(Get/ReleaseIntArrayElements)  160ms(Get/ReleasePrimitiveArrayCritical)  1ms(funcBody = void)
  C++  Cost: 196ms  0ms(funcBody = void)

Linux:
  Java Cost: 144ms(Get/ReleasePrimitiveArrayCritical)
  C++  Cost: 140ms
