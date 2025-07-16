#include<stdio.h>
int main(){
    int n;
    int sum = 0;
    printf("enter number\n");
    scanf("%d",&n);
    
    for(int i=1;i<=n;i++){
        sum = sum + i;
    }
    printf("Sum of %d natural number is %d",n,sum);
    printf("\n");
    printf("Through formula\n");
    printf("%d", n*(n+1)/2);

    printf("\n");
    printf("using while loop");
    int i=0, sum1 =0;
    while(i<=n){
        sum1 = sum1 + i;
        i++;
    }
    printf("%d",sum1);
    return 0;
}