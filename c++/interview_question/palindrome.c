#include<stdio.h>
int main(){
    int n, rem = 0, rev = 0;
    printf("enter the number\n");
    scanf("%d",&n);
    int temp = n;
    while(n>0){
        rem = n % 10;
        rev = rev * 10 + rem;
        n = n/10;
    }
    if(temp == rev){
        printf("its palindrome\n");
    }
    else{
        printf("its not a palindrome");
    }
    return 0;
}