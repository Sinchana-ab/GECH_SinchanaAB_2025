#include <stdio.h>

int main() {
    // Variable declarations for different data types
    int i;
    float f;
    double d;
    char c;
    char str[100];
    long int li;
    short int si;
    unsigned int ui;
    long long int lli;

    // Input from user
    printf("Enter an integer: ");
    scanf("%d", &i);

    printf("Enter a float: ");
    scanf("%f", &f);

    printf("Enter a double: ");
    scanf("%lf", &d);

    printf("Enter a character: ");
    scanf(" %c", &c); 

    printf("Enter a string: ");
    scanf("%s", str); 

    printf("Enter a long integer: ");
    scanf("%ld", &li);

    printf("Enter a short integer: ");
    scanf("%hd", &si);

    printf("Enter an unsigned integer: ");
    scanf("%u", &ui);

    printf("Enter a long long integer: ");
    scanf("%lld", &lli);

    
    printf("\nYou entered:\n");
    printf("Integer: %d\n", i);
    printf("Float: %f\n", f);
    printf("Double: %lf\n", d);
    printf("Character: %c\n", c);
    printf("String: %s\n", str);
    printf("Long Integer: %ld\n", li);
    printf("Short Integer: %hd\n", si);
    printf("Unsigned Integer: %u\n", ui);
    printf("Long Long Integer: %lld\n", lli);

    return 0;
}