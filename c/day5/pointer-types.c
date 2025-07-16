#include<stdio.h>
int main(){
    float f = 2034;
    float* fptr = &f; /// normal pointer refering the some variable 
    int* iptr;
    int* ptr2 = NULL;// null pointer

    printf("the float pointer contains: %p\n",fptr);
    printf("the int pointer contains: %p\n",iptr);
    printf("the int NULL pointer contains: %p\n",ptr2);
    void* ptr3 = NULL;//VOID POINTER
    printf("the void pointer contains: %p\n", ptr3);
    ptr3 = &f; // works as float pointer
    printf("the integer pointer contains: %p address and value &f\n", ptr3, *(float*)(ptr3));
    ptr3 = &iptr; // it works integer

    printf("the void pointer address contains: %p\n", ptr3);
    

}