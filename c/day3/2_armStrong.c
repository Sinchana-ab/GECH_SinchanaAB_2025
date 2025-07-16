#include<stdio.h>
#include<math.h>
int main(){
    int a, i = 0, r=0,result =0;
    printf("Enter a number: ");
    scanf("%d",&a);
    int n = a;
    while(n>0){
        n = n/10;
        i++;
    }
     n =a;
    while(n>0){
        r = n % 10;
        result += pow(r,i);
        n=n/10;
    }
    if(a == result){
        printf("Armstrong number");
    }
    else{
        printf("Not a armstrong number"); 
    }

    return 0;
}