package per.itachi.test.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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
		
		ControlableRunnable runnable = new TestThreadRunnable();
		
		//initialise a new thread pool with fixed number of thread, 
		//using java.util.concurrent.ThreadPoolExecutor
		log.debug("Initialising Executors.newFixedThreadPool");
		ExecutorService poolFixed = Executors.newFixedThreadPool(iNumOfThreads);
		poolFixed.execute(runnable);
		poolFixed.shutdown();
		log.debug("Shut down Executors.newFixedThreadPool");
		
		//initialise a new thread pool with cached recyclable threads, 
		//using java.util.concurrent.ThreadPoolExecutor
		log.debug("Initialising Executors.newCachedThreadPool");
		ExecutorService poolCached = Executors.newCachedThreadPool();
		poolCached.shutdown();
		log.debug("Shut down Executors.newCachedThreadPool");
		
		//initialise a new thread pool with the fixed number of scheduled thread, implementing ... interface (?)
		//using java.util.concurrent.ScheduledThreadPoolExecutor
		log.debug("Initialising Executors.newScheduledThreadPool");
		ScheduledExecutorService poolScheduled = Executors.newScheduledThreadPool(iNumOfThreads);
		poolScheduled.shutdown();
		log.debug("Shut down Executors.newScheduledThreadPool");
		
		//initialise a new thread pool with , 
		//using new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(5 params))
		log.debug("Initialising Executors.newSingleThreadExecutor");
		ExecutorService poolSingle = Executors.newSingleThreadExecutor();
		poolSingle.shutdown();
		log.debug("Shut down Executors.newSingleThreadExecutor");
		
		//initialise a new thread pool with just , 
		//using new DelegatedScheduledExecutorService(new ScheduledThreadPoolExecutor(1))
		log.debug("Initialising Executors.newSingleThreadScheduledExecutor");
		ScheduledExecutorService poolSingleScheduled = Executors.newSingleThreadScheduledExecutor();
		poolSingleScheduled.shutdown();
		log.debug("Shut down Executors.newSingleThreadScheduledExecutor");
		
//		try {
//			Thread.sleep(5000l);
//		} 
//		catch (InterruptedException e) {
//			log.error("The current thread has been interrupted. ", e);
//		}
	}
}

class TestThreadRunnable implements ControlableRunnable {
	
	private final static Log log = LogFactory.getLog(TestThreadRunnable.class);
	
	public TestThreadRunnable() {}

	@Override
	public void run() {
		try {
			Thread.sleep(1000l);
		} 
		catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void terminate() {
		
	}
	
}
