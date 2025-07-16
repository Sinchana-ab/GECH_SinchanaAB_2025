#include<stdio.h>
int main(){
    int a;
    printf("Enter a number: ");
    scanf("%d",&a);
    if(a%2==0){
        printf("The number is devided by 2");
    }
    else if(a%3==0){
        printf("The number is devided by 3");
    }
    else{
        printf("The number is not devided by 2 and 3");
    }
}