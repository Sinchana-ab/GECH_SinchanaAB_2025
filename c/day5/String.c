#include<stdio.h>
#include<string.h>
int main(){
    // for single char
    char ch;
    printf("Enter a char: ");
    scanf("%c",&ch);
    printf("the value charcter of %c\n", ch);
    // for string
    char str[100], copy[100];
    printf("Enter a string: \n");
   // scanf("%s", str);
    //gets(str);  // it will take input until enter key is pressed and print and without checking the input variable size
    fgets(str, sizeof(str), stdin);// it will check the variable size the it take will take input as per that
    printf("the value of string is %s\n", str);
    printf("the given string contain %d character: ",strlen(str));
    printf("the copying the str is %s",strcpy(copy,str));
    printf("the reverse of string is %s", strrev(str));
    printf("the string comparision is %d",strcmp(copy,str));
    printf("the string concatenation is %s", strcat(copy, str));
    return 0;
}