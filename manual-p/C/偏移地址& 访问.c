#include <stdio.h>
#include "stdbool.h"
int main();

void start(void)
{
printf("-----\n");

printf("-----\n");
printf("-----\n");

main();

}

int main()
{
    
   volatile unsigned char a=9;
   volatile unsigned char a1=8;
   volatile unsigned char a2=7;
    
    #define UART_BASE       &a 
    #define UART_REG8(x)   ((volatile unsigned char *)UART_BASE)[x]
    UART_REG8(2)=20;
    printf("\nffs--testfor: OX%x , %x,%x\n",UART_BASE,&a1,&a2);
   printf("\nffs--testfor : OX%x \n", UART_REG8(-2));
   
   UART_REG8(-2)=0x32;
   printf("\nffs--testfor : OX%x \n", UART_REG8(-2));
   printf("\nffs--testfor : OX%x \n", UART_REG8(0));
   printf("\nffs--testfor : OX%x \n", & ( UART_REG8(2)));
   start();
   return 0;
}


// printf MSG

//ffs--testfor: OXbfc43df3 , bfc43df2,bfc43df1

//ffs--testfor : OX7 

//ffs--testfor : OX32 

//ffs--testfor : OX9 

//ffs--testfor : OXbfc43df5 

