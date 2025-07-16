#include<stdio.h>

int fib(int n)
{
    if(n == 0)
        return 0;
    else if(n == 1)
        return 1;
    else
        return fib(n - 1) + fib(n - 2);
}

int main()
{
    int n, i;
    printf("Enter the number of terms: ");
    scanf("%d", &n);

    // Recursive version
    printf("Fibonacci with recursion:\n");
    for(i = 0; i < n; i++)
    {
        printf("%d ", fib(i));
    }
    printf("\n");

    // Iterative version
    printf("Fibonacci without recursion:\n");
    int a = 0, b = 1, c, count = 2;

    if(n >= 1)
        printf("%d ", a);
    if(n >= 2)
        printf("%d ", b);

    while(count < n)
    {
        c = a + b;
        printf("%d ", c);
        a = b;
        b = c;
        count++;
    }
    printf("\n");

    return 0;
}