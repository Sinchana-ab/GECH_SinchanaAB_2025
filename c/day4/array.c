#include<stdio.h>
int main(){
    //syntax 
    int num[10],n;
    int  num1 = {10,12,13,14};
   // int a[10] ={10, 12,13,14};
    printf("enter the size of elements\n");
    scanf("%d",&n);
    for(int i =0;i<n;i++){
        scanf("%d",&num[i]);
    }
    printf("the elements are\n");
    for(int i =0;i<n;i++){
        printf("%d\n", num[i]);
    }

}