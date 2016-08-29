 
#include <stdio.h>

#define test(fmt,arg...)      printf((char *)fmt, ##arg)

int main(void )
{
     test("\033[1;33;44m good morning,sir!!!! %d %s %s \033[0m\n",__LINE__,__FILE__,__FUNCTION__);
     printf("%s",__FILE__);
    return 0;
}

/*
printf_test.cffs@ubuntu-desktop:~/ffs_test$ gcc printf_test.c 
ffs@ubuntu-desktop:~/ffs_test$ ./a.out 
 good morning,sir!!!! 8 printf_test.c main 
printf_test.cffs@ubuntu-desktop:~/ffs_test$ 
*/
// 执行结果