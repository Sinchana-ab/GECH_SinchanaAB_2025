#include <stdio.h>
int main()
{
    int a, b;
    printf("Enter two numbers: \n");
    // scanf(format_specifier,address_operator followed by var_name)
    scanf("%d %d", &a, &b);

    printf("The sum is: %d", (a + b));
    return 0;
}