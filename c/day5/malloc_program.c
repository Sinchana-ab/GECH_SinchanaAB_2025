#include<stdio.h>
#include<stdlib.h> //used for DMA functions
// ex: malloc(), calloc(), realloc(),free()
int main() // malloc is allocate memory as per reired like user input 
{
    int *p;
    int n;
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
    return 0;
}