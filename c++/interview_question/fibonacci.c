#include<stdio.h>
int fibbo(int n){
    if(n == 0){
       return 0;
    }
    else if(n == 1){
        return 1;
    }
    else{
       return fibbo(n-1) + fibbo(n-2);
    }
}
int main(){
    int n;
    printf("Enter the fibonacci series want to print: \n");
    scanf("%d",&n);
    for(int i=0;i<n;i++){
        printf("%d\n",fibbo(i));
    }
    printf("\n");
    return 0;
}
