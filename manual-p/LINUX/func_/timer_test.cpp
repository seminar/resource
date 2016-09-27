#include <sys/time.h>
#include "stdio.h"
#include<time.h>



typedef  unsigned long   U32;
#if 1

void MSrv_SendDvdKeyCode_Delay(U32 timer)
{
	struct timespec begin;  
	unsigned long interval = timer;//nS  
	unsigned long long ns;  
	clock_gettime(CLOCK_MONOTONIC, &begin);  
	while(1)  
	{  
	ns = begin.tv_nsec;  
	ns += interval;  
	begin.tv_sec += ns/(1000*1000*1000);  
	begin.tv_nsec = ns%(1000*1000*1000);  
	clock_nanosleep(CLOCK_MONOTONIC, TIMER_ABSTIME, &begin, NULL);  
	return;
	}  
}

int main()
{
    printf("\033[1;32;43m ffs test  %s %s %d   \033[0m\n",__FILE__,__FUNCTION__,__LINE__);
    MSrv_SendDvdKeyCode_Delay(1000000000);
    printf("\033[1;32;43m ffs test  %s %s %d   \033[0m\n",__FILE__,__FUNCTION__,__LINE__);
    
    printf("\033[1;32;43m ffs test  %s %s %d   \033[0m\n",__FILE__,__FUNCTION__,__LINE__);
    printf("\033[1;32;43m ffs test  %s %s %d   \033[0m\n",__FILE__,__FUNCTION__,__LINE__);
    printf("\033[1;32;43m ffs test  %s %s %d   \033[0m\n",__FILE__,__FUNCTION__,__LINE__);
    return 0;
}
#endif

#if 0 
ffs@ubuntu-desktop:~/ffs_test$ g++ timer_test.cpp  -lrt
ffs@ubuntu-desktop:~/ffs_test$ ./a.out
 ffs test  timer_test.cpp main 29   
 ffs test  timer_test.cpp main 31   
 ffs test  timer_test.cpp main 33   
 ffs test  timer_test.cpp main 34   
 ffs test  timer_test.cpp main 35   
ffs@ubuntu-desktop:~/ffs_test$ 


#endif
