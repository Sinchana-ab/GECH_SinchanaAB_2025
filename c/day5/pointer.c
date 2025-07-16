#include<stdio.h>
int main(){
    int num = 5;
    int* ptr = &num;
    printf(" the value of num %d\n",num);
    printf(" the addres of num %p\n", &num);
    printf(" the pointer contains %p\n", ptr);
    printf(" the address of value %d\n", &ptr);
    printf(" the value of num %d\n", *ptr);

}