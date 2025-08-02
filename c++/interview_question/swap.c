#include<stdio.h>
int main(){
    int a = 10, b =5;
    printf("Before swapping: a is: %d and b is: %d\n",a,b);
    a = a +b;
    b = a - b;
    a = a-b;
    printf("after swapping: a is: %d and b is: %d\n",a, b);
}