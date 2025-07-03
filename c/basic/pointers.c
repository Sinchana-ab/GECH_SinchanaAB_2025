#include <stdio.h>
/**
 * Pointer:
 * ========
 * pointer is a varibale that stores the address ofanother variable.
 *
 * decleration:
 * ============
 * datatype *pointer_name;
 *
 * int *ptr;
 *
 * initialization:
 * ===============
 * pointer_name = &var_name;
 * ptr = &a;
 *
 * & ---> address operator
 * * ---> dereference operator / value operator.
 */
int main()
{
    // variable
    int a = 20;
    // pointer
    int *ptr;
    ptr = &a;

    printf("The value of a: %d \n", a);
    printf("The Address of a: %p \n", &a);
    printf("The Value of ptr is: %p\n", ptr);
    printf("The Address of ptr is: %p\n", &ptr);

    printf("The value of a using pointer: %d \n", *ptr); // 20
    printf("The value of a using pointer: %d \n", *(&a));
    printf("The value of a using pointer: %d \n", *(*(&ptr)));
    *ptr = 50;
    printf("The value of a using pointer: %d \n", *ptr); // 20

    /**
     * int a = 10;
     * int b = 20;
     * int temp;
     *
     * temp = a;
     * a = b;
     * b = temp;
     */
}