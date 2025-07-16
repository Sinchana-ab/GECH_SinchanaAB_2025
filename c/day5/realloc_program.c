#include<stdio.h>
#include<stdlib.h>
int main(){
    int *p;
    int n, n1;
    printf("Enter the size you want to allocate: \n");
    scanf("%d",&n);
    p = (int*)malloc(n*sizeof(int));
    printf("Enter the %d elements: \n",n);
    for(int i=0; i<n; i++)
    {
        scanf("%d", &p[i]);
    }
    printf("The elements are as follows \n");
    for(int i =0;i<n;i++){
        printf("%d\n", p[i]);
    }
    printf("Enter the new size you want to allocate: \n");
    scanf("%d", &n1);
    p = (int*)realloc(p,n1*sizeof(int));
    printf("Enter the %d elements: \n",n);
    for(int i=0; i<n1; i++)
    {
        scanf("%d", &p[i]);
    }
    //int n2 = n + n1; 
    printf("The elements are as follows \n");
    for(int i =0;i<n1;i++){
        printf("%d\n", p[i]);
    }

    //
}