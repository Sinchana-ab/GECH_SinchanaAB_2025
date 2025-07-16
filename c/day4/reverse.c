#include<stdio.h>
int main(){
    int n,arr[10];
    printf("enter the size of array: \n");
    scanf("%d",&n);
    printf("enter the array elements:\n");
    for(int i =0;i< n;i++){
        scanf("%d",&arr[i]);
    }
    printf("the reversed array elements\n");
    for (int i = n-1;i>=0;i--){
        printf("%d\n",arr[i]);
    } 
}