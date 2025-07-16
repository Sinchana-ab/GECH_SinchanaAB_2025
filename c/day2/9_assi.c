//Palindrome question
#include<stdio.h>
int main(){
    int a;
    printf("Enter the number: ");
    scanf("%d",&a);
    int n = a;
    int reversed = 0,reminder = 0;
    while (n>0)
    {
        reminder = n % 10;
        reversed = reversed * 10 + reminder;
        n /= 10;
    }
    if(reversed == a){
        printf("its palindrome");
    }
    else{
        printf("its not a palindrome");
    }
    return 0;
}