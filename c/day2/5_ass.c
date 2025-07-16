#include<stdio.h>
int main(){
    int a;
    printf("enter a value upto: \n");
    scanf("%d",&a);
    printf("the even number upto %d\n",a);
    for (int i = 0; i < a; i++)
    {
        if(i % 2 == 0){
            printf("%d\n",i);
        }
    }
    return 0;
    
}