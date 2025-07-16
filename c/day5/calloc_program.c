#include<stdio.h>
#include<stdlib.h>
int main(){
    float* fptr;
    int n;
    printf("enyter the number of elements to be stored:\n");
    scanf("%d",&n);
    fptr = (float*) calloc(n, sizeof(float));
    printf("enter the %d elements:\n",n);
    for(int i =0; i<n;i++){
        scanf("%f",&fptr[i]);
    }
    printf("the elements are:\n");
    for(int i=0;i<n;i++){
        printf("%.2f\n", fptr[i]);
    }
}