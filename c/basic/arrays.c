#include <stdio.h>
/**
 * Array:
 * ======
 * --> array is a collection of same datatype elements
 * datatype_type array_name[size];
 * int arr[10];
 *
 * accessing:
 * ==========
 * arr[index];
 * arr[0];
 */
int main()
{
    int arr[10]; // 1D

    int arr[3][3]; // 9-elemets 3*3
    for (int i = 0; i < 3; i++)
    {
        for (int j = 0; j < 3; j++)
        {
            printf("%d ",arr[i][j]);
        }
        printf("\n");
    }
}