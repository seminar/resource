#include <unistd.h>; 
#include <sys/types.h>; 

main () 
{ 
pid_t pid; 
printf("fork!"); // printf("fork!n"); 
pid=fork(); 

if (pid < 0) 
printf("error in fork!"); 
else if (pid == 0) 
printf("i am the child process, my process id is %dn",getpid()); 
else 
printf("i am the parent process, my process id is %dn",getpid()); 
} 