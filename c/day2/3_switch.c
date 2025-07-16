#include<stdio.h>
int main(){
    int a;
    printf("enter a number: ");
    scanf("%d",&a);
    switch (a)
    {
    case 1: printf("the value of a is 1");
        break;
    case 2: printf("the value of a is 2");
    break;
     case 3: printf("the value of a is 2");
     break;
    default: printf("the value is greater than 3");
        break;
    }
}