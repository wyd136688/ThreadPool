package com.android.pool;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {
    /*
 1、newCachedThreadPool

-缓存型池子，先查看池中有没有以前建立的线程，如果有，就reuse.如果没有，就建一个新的线程加入池中
-缓存型池子通常用于执行一些生存期很短的异步型任务
 因此在一些面向连接的daemon型SERVER中用得不多。
-能reuse的线程，必须是timeout IDLE内的池中线程，缺省timeout是60s,超过这个IDLE时长，线程实例将被终止及移出池。

注意，放入CachedThreadPool的线程不必担心其结束，超过TIMEOUT不活动，其会自动被终止。

newFixedThreadPool

-newFixedThreadPool与cacheThreadPool差不多，也是能reuse就用，但不能随时建新的线程
-其独特之处:任意时间点，最多只能有固定数目的活动线程存在，此时如果有新的线程要建立，只能放在另外的队列中等待，直到当前的线程中某个线程终止直接被移出池子
-和cacheThreadPool不同，FixedThreadPool没有IDLE机制（可能也有，但既然文档没提，肯定非常长，类似依赖上层的TCP或UDP IDLE机制之类的），所以FixedThreadPool多数针对一些很稳定很固定的正规并发线程，多用于服务器
-从方法的源代码看，cache池和fixed 池调用的是同一个底层池，只不过参数不同:
fixed池线程数固定，并且是0秒IDLE（无IDLE）
cache池线程数支持0-Integer.MAX_VALUE(显然完全没考虑主机的资源承受能力），60秒IDLE

ScheduledThreadPool

-调度型线程池
-这个池子里的线程可以按schedule依次delay执行，或周期执行

SingleThreadExecutor

-单例线程，任意时间池中只能有一个线程
如果当前线程意外终止，会创建一个新线程继续执行任务，这和我们直接创建线程不同，也和newFixedThreadPool(1)不同。
-用的是和cache池和fixed池相同的底层池，但线程数目是1-1,0秒IDLE（无IDLE）

     */

   // final ExecutorService threadPool = Executors.newCachedThreadPool();
   final ExecutorService threadPool = Executors.newFixedThreadPool(3);
   // final ExecutorService threadPool = Executors.newSingleThreadExecutor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int i = 1;i < 5;i++) {
            final int taskId = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i = 1;i < 5;i++) {
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.d("test",">>weiyandong>>> 第"+taskId+"个任务，第"+i+"次执行");
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
