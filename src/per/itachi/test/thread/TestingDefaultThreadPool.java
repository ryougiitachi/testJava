package per.itachi.test.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import per.itachi.test.TestingConst;

public class TestingDefaultThreadPool {
	
	private final static Log log = LogFactory.getLog(TestingDefaultThreadPool.class);
	
	public static void test(String[] params) {
		int iNumOfThreads = 0;
		if (params.length <= 1) {
			iNumOfThreads = TestingConst.DEFAULT_THREAD_POOL_NUM;
		}
		else {
			try {
				iNumOfThreads = Integer.parseInt(params[1]);
			} 
			catch (NumberFormatException e) {
				log.error("The number of thread from parameter is invalid, using default value.", e);
				iNumOfThreads = TestingConst.DEFAULT_THREAD_POOL_NUM;
			}
			finally {
				if (iNumOfThreads <= 0) {
					log.error("The number of thread from parameter is non-positive, using default value.");
					iNumOfThreads = TestingConst.DEFAULT_THREAD_POOL_NUM;
				}
			}
		}
		
		//initialise a new thread pool with fixed number of thread, 
		//using java.util.concurrent.ThreadPoolExecutor
		ExecutorService poolFixed = Executors.newFixedThreadPool(iNumOfThreads);
//		poolFixed.execute(Runnable);
		poolFixed.shutdown();
		
		//initialise a new thread pool with , 
		//using java.util.concurrent.ThreadPoolExecutor
		ExecutorService poolCached = Executors.newCachedThreadPool();
		poolCached.shutdown();
		
		//initialise a new thread pool with , 
		//using java.util.concurrent.ScheduledThreadPoolExecutor
		ExecutorService poolScheduled = Executors.newScheduledThreadPool(iNumOfThreads);
		poolScheduled.shutdown();
		
		//initialise a new thread pool with , 
		//using new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(5 params))
		ExecutorService poolSingle = Executors.newSingleThreadExecutor();
		poolSingle.shutdown();
		
		//initialise a new thread pool with , 
		//using new DelegatedScheduledExecutorService(new ScheduledThreadPoolExecutor(1))
		ExecutorService poolSingleScheduled = Executors.newSingleThreadScheduledExecutor();
		poolSingleScheduled.shutdown();
		
		try {
			Thread.sleep(5000l);
		} 
		catch (InterruptedException e) {
			log.error("The current thread has been interrupted. ", e);
		}
	}
}
