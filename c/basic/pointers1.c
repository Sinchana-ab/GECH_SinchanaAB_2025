/**
 * Pointers to array.
 * int *ptr;
 * int arr[10];
 * ptr = arr or &arr[0]
 */

#include <stdio.h>
int main()
{
    int arr[5] = {1, 2, 3, 4, 5};
    int *ptr;
    ptr = arr;

    printf("The arr value: %p \n", arr); // array is also a pointer in c and c++.
    printf("The address of 0th ele: %p \n", &arr[0]);
    printf("The value of ptr: %p \n", ptr);

    printf("The value of 1st element using arr: %d \n", arr[0]);
    printf("The value of 1st element using arr: %d \n", 0 [arr]);
    printf("The value of 1st element using arr: %d \n", *arr);

    printf("The value of 1st element using ptr: %d \n", ptr[0]);
    printf("The value of 1st element using ptr: %d \n", 0 [ptr]);
    printf("The value of 1st element using ptr: %d \n", *ptr);

    ptr++; // prt = ptr+1; ---> 1004
    printf("The value  using ptr: %d \n", *ptr);
    ptr = ptr + 2;
    printf("The value  using ptr: %d \n", *ptr);
    *ptr = 100;

    for (int i = 0; i < 5; i++)
    {
        printf("%d ", i[arr]);
    }
    return 0;
}