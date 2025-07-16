#include<stdio.h>
int main(){
    // structure syntax
    struct Student{
        int id;
        char name[20];
        char branch[20];
        int sem;
    };
    //syntax for structure variable
    struct Student s1, s2;
    s1.id = 1;
    //s1.name = "Raj";
    printf("Enter name of student 1: ");
   // scanf("%s", s1.name);
   //fgets(s1.name, 20, stdin);
    //gets(s1.name);
    scanf("%[^\n]", s1.name);
    printf("Enter branch of student 1: ");
    scanf("%s", s1.branch);
    printf("Enter semester of student 1: ");
    scanf("%d", &s1.sem);
    printf("Enter id of student 1: ");
    scanf("%d", &s1.id);
    printf("the student details\n");
    printf("id: %d\n", s1.id);
    printf("name: %s\n", s1.name);
    printf("branch: %s\n", s1.branch);
    printf("semester: %d\n", s1.sem);
    //printf("ID: %d\t name: ")
}