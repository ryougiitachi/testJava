package per.itachi.test.map;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestingHashMap {
	
	private final static Log log = LogFactory.getLog(TestingHashMap.class);
	
	public static void test(String[] params) {
		Map<TestHashMapKey, String> map = new HashMap<TestHashMapKey, String>();
		TestHashMapKey keyA = new TestHashMapKey(1);
		TestHashMapKey keyB = new TestHashMapKey(1);
		//If hashCode and equals haven't been override, keyA and keyB will be different keys. 
		//Both hashCode and equals are needed to modify in pairs. 
		map.put(keyA, "keyA");
		map.put(keyB, "keyB");
		log.debug(String.format("The value of %s is %s", keyA, map.get(keyA)));
		log.debug(String.format("The value of %s is %s", keyB, map.get(keyB)));
	}

	static class TestHashMapKey {
		
		private int id;
		
		public TestHashMapKey(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		@Override
		public int hashCode() {
			return id;
//			return super.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return this.id == ((TestHashMapKey)obj).id;
//			return super.equals(obj);
		}

		@Override
		public String toString() {
			return TestHashMapKey.class.getName() + String.valueOf(id);
		}
	}
}
