// amstrong of digit 153 = 1^3 + 5^3 + 3^3 = 153
#include<stdio.h>
int main()
{
    int n,r,sum=0,temp;
    printf("Enter the number : ");
    scanf("%d",&n);
    temp=n;
    while(n>0)
    {
        r=n%10;
        sum=sum+(r*r*r);
        n=n/10;
    }
    if(temp==sum)
    {
        printf("Amstrong number");
    }
    else
    {
        printf("Not Amstrong number");
    }
    return 0;
}