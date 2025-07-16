#include<stdio.h>
int main(){
    int n, count =0;
    printf("enter a number");
    scanf("%d",&n);
    if(n<=0){
        printf("it not a prime");
    }
    else{
        for(int i = 2;i<n;i++){
            if(n%i==0){
                count++;
            }
            if(count>2){
                printf("it is a prime number");
            }
            else{
                printf("its not prime");
            }
        }
    }
    return 0;
}