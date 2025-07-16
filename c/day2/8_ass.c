#include<stdio.h>
int fact1(int a){
    if(a == 0 || a == 1){
        return 1;
    }
    else{
        return a * fact(a - 1);
    }
}
int main(){
    int a;
    printf("enter the number need get factorial: ");
    scanf("%d",&a);
    int fact = 1;
    for(int i = 1; i <= a; i++){
        fact = fact * i;
    }
    printf("factorial of %d is %d", a, fact);
    printf("\n");
    printf("factorial of %d is %d", a, fact1(a));
    return 0;
}