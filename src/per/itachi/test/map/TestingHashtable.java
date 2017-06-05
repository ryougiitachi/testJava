package per.itachi.test.map;

import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import per.itachi.test.thread.ControlableRunnable;

public class TestingHashtable {
	
	private static final Log log = LogFactory.getLog(TestingHashtable.class);
	
	/**
	 * Validate whether or not ConcurrentModificationException will be thrown 
	 * in case that a key is deleted when iterating keys of hashtable. <br/>
	 * Result is no. <br/>
	 * But this exception will be thrown if it is hashmap. <br/>
	 * Hashtable for thread-safety(thread-safe Hashtable) is not false. <br/>
	 * */
	public static void test(String[] params) {
		Map<String, String> map;
		map = new Hashtable<>();
		map.put("1", "1");
		map.put("2", "2");
		map.put("3", "3");
		map.put("4", "4");
		map.put("5", "5");
		Set<String> setKeys = map.keySet();
		Iterator<String> iterator = setKeys.iterator();
		ControlableRunnable runnable = new TestIteratorRunnable(map);
		Thread thread = new Thread(runnable);//wait and notify must be invoked in synchronized block
		thread.start();
//		synchronized (map) {
//			try {
//				map.wait();//maybe a possible deadlock? java.lang.IllegalMonitorStateException ?
//			} 
//			catch (InterruptedException e) {
//				log.error(e.getMessage(), e);
//			}
//		}
		
//		for (String strKey : setKeys) {
//			log.debug(MessageFormat.format("{0} still remains. ", strKey));
//		}
		while (iterator.hasNext()) {
			String strKey = iterator.next();
			log.debug(MessageFormat.format("{0}-{1} still remains. ", strKey, map.get(strKey)));

			try {
				Thread.sleep(1000l);
			} 
			catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
		}
		log.debug("Finished TestingHashtable.test");
	}
	
	public static class TestIteratorRunnable implements ControlableRunnable {
		
		private Map<String, String> map;
		
		public TestIteratorRunnable(Map<String, String> map) {
			this.map = map;
		}

		@Override
		public void run() {
			synchronized (map) {
				try {
					Thread.sleep(2000l);
				} 
				catch (InterruptedException e) {
					log.error(e.getMessage(), e);
				}
				map.clear();
				map.notify();
			}
		}

		@Override
		public void terminate() {
			
		}		
	}
}
