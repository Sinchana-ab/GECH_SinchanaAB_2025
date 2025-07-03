#include <stdio.h>
int main()
{
    // single line comment
    /**
     * multi
     * line
     * comment
     */
    /**
     * primitive datatypes in c
     * ========================
     * 1. int  --> %d or %i
     * 2. char --> %c
     * 3. float -->  %f
     * 4. double --> %lf
     *
     * derived datatype:
     * =================
     * 1. array
     * 2. string(char array)
     *
     * user defined datatypes:
     * =======================
     * 1. structure
     * 2. union
     *
     */

    int age;
    char gender;
    float marks;
    double percentage;
    printf("Enter age: \n");
    scanf("%d", &age);
    getchar();
    printf("Enter Gender: \n");
    scanf("%c", &gender);
    printf("Enter Marks: \n");
    scanf("%f", &marks);
    printf("Enter Percentage: \n");
    scanf("%lf", &percentage);
    printf("Age is: %d \n", age);
    printf("Gender is: %c \n", gender);
    printf("Marks is: %f \n", marks);
    printf("Percentage is: %lf \n", percentage);
}