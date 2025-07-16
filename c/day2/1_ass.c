#include<stdio.h>
int main(){
    int age;
    printf("Enter age of the person: ");
    scanf("%d",&age);
    if (age > 0 && age<=12)
    {
        printf("The person is a child");
    }
    else if(age >= 13 && age<=19){
        printf("The person is a teenager");
    }
    else if(age >= 20 && age<=59){
        printf("The person is an adult");
    }
    else if(age >= 60){
        printf("The person is a senior citizen");
    }
    else{
        printf("invalid");
    }
    
}