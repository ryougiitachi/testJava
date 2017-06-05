package per.itachi.test.map;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import per.itachi.test.TestingConst;

public class TestingTreeMap {
	
	private final static Log log = LogFactory.getLog(TestingTreeMap.class);
	
	public static void test(String[] params) {
		Map<TestTreeMapKey, String> treemap = new TreeMap<TestTreeMapKey, String>();
		//Comparable is different from Comparator!!!!!
		//In TreeMap, program must one of 2 conditions, 
		//which key must implements Comparable<T>, 
		//or a Comparator<T> implement class is set. 
		//Otherwise, a ClassCastException will be thrown at run-time. 
		//Exception in thread "main" java.lang.ClassCastException: 
		//per.itachi.test.map.TestingTreeMap$TestTreeMapKey cannot be cast to java.lang.Comparable
		//Comparable<T> is internal comparator, while Comparator<T> is external comparator. 
		treemap.put(new TestTreeMapKey(1), TestingConst.ELEMENT_STR_SPECCHAR);
		treemap.put(new TestTreeMapKey(2), TestingConst.ELEMENT_STR_SPECCHAR);
		log.debug("There is no error.");
	}

	/**
	 * Without static, it will prompt, "No enclosing instance of type TestingTreeMap is accessible. 
	 * Must qualify the allocation with an enclosing instance of type TestingTreeMap 
	 * (e.g. x.new A() where x is an instance of TestingTreeMap)." 
	 * At least as for static method. 
	 * It is not sure for member method currently. 
	 * */
	static class TestTreeMapKey implements Comparable<TestTreeMapKey>{
		
		public TestTreeMapKey(int id) {
			this.id = id;
		}
		
		private int id;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		@Override
		public boolean equals(Object obj) {
			return super.equals(obj);
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}

		@Override
		public int compareTo(TestTreeMapKey key) {
			return id - key.id;
		}
	}
}
