
/**
 * functions in c:
 * ===============
 * 1. function prototype/decleration:
 * ==================================
 * --> it is used by the compiler to know some where else ther is function in the code
 *     return_type function_name(parameters);
 *     int  add(int a,int b);
 * 2. function definition:
 * =======================
 *      return_type function_name(parameters){
 *          //code
 *      }
 *      void add(int a, int b){
 *          ptintf("the sum is %d",a+b);
 *      }
 *
 * 3. function call:
 * =================
 *     function_name(arguments);
 *     add(2,3);
 */
#include <stdio.h>
int add(int num1, int num2);

int main()
{
    int a, b;
    printf("Enter two numbers: \n");
    scanf("%d %d", &a, &b);
    int res = add(a, b);
    printf("The sum of %d and %d is: %d", a, b, res);

    return 0;
}

int add(int num1, int num2)
{
    return num1 + num2;
}