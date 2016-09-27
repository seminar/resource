#include <pthread.h> 
#include <stdio.h> 
#include <stdlib.h> 

extern "C" unsigned sleep (unsigned __seconds) ;

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;/*初始化互斥锁*/ 
pthread_cond_t cond = PTHREAD_COND_INITIALIZER;/*初始化条件变量*/ 
void *thread1(void *); 
void *thread2(void *); 
int i=1; 
int main(void) 
{ 
pthread_t t_a; 
pthread_t t_b; 
pthread_create(&t_a,NULL,thread1,(void *)NULL);/*创建进程t_a*/ 
pthread_create(&t_b,NULL,thread2,(void *)NULL); /*创建进程t_b*/ 
pthread_join(t_a, NULL);/*等待进程t_a结束*/ 
pthread_join(t_b, NULL);/*等待进程t_b结束*/ 
pthread_mutex_destroy(&mutex); 
pthread_cond_destroy(&cond); 
exit(0); 
} 
void *thread1(void *junk) 
{ 
for(i=1;i<=6;i++) 
{ 
pthread_mutex_lock(&mutex);/*锁住互斥量*/ 
printf("thread1: lock %d/n", __LINE__); 
if(i%3==0){ 
printf("thread1:signal 1 %d/n", __LINE__); 
pthread_cond_signal(&cond);/*条件改变，发送信号，通知t_b进程*/ 
printf("thread1:signal 2 %d/n", __LINE__); 
sleep(1);

} 
pthread_mutex_unlock(&mutex);/*解锁互斥量*/ 
printf("thread1: unlock %d/n/n", __LINE__); 
sleep(1); 
} 
} 
void *thread2(void *junk) 
{ 
while(i<6) 
{ 
pthread_mutex_lock(&mutex); 
printf("thread2: lock %d/n", __LINE__); 
if(i%3!=0){ 
printf("thread2: wait 1 %d/n", __LINE__); 
pthread_cond_wait(&cond,&mutex);/*解锁mutex，并等待cond改变*/ 
printf("thread2: wait 2 %d/n", __LINE__); 
} 
pthread_mutex_unlock(&mutex); 
printf("thread2: unlock %d/n/n", __LINE__); 
sleep(1); 
} 
} 

/*

ffs@ubuntu-desktop:~/ffs_test$ g++ test2.cpp -o thread -lpthread
ffs@ubuntu-desktop:~/ffs_test$ ./thread
thread2: lock 47/nthread2: wait 1 49/nthread1: lock 29/nthread1: unlock 38/n/nthread1: lock 29/nthread1: unlock 38/n/nthread1: lock 29/nthread1:signal 1 31/nthread1:signal 2 33/nthread1: unlock 38/n/nthread2: wait 2 51/nthread2: unlock 54/n/nthread1: lock 29/nthread1: unlock 38/n/nthread2: lock 47/nthread2: wait 1 49/nthread1: lock 29/nthread1: unlock 38/n/nthread1: lock 29/nthread1:signal 1 31/nthread1:signal 2 33/nthread1: unlock 38/n/nthread2: wait 2 51/nthread2: unlock 54/n/nffs@ubuntu-deskto

*/
