//sort in ascending order
//bubble sort technique
#include<stdio.h>
int main(){
    int arr[10],n,temp;
    printf("Enter the size of array: ");
    scanf("%d",&n);
    printf("Enter the elements of array: \n");
    for(int i = 0; i < n; i++){
        scanf("%d", &arr[i]);
    }
    printf("the sorted array elements:\n");
    for(int  i = 0;i< n;i++){
        for(int j=0; j < n;j++){
            if(arr[j]>arr[j+1]){
                temp = arr[j];
                arr[j]=arr[j+1];
                arr[j+1] = temp;
            }
        }
    }
    for(int i = 0; i < n; i++){
        printf("%d ", arr[i]);
    }
    return 0;
}