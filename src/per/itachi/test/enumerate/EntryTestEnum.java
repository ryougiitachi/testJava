package per.itachi.test.enumerate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EntryTestEnum {

	private static final Log log = LogFactory.getLog(EntryTestEnum.class);
	
	public static void main(String[] args) {
//		TestEnumeration enum1 = new TestEnumeration(1);//Cannot instantiate the type TestEnumeration
		log.debug(TestEnumeration.EAGER.hashCode());
		log.debug(TestEnumeration.LAZY.hashCode());
	}

}

enum TestEnumeration {
	EAGER(1),
	LAZY(2);
	
	private int type;
	
	/**
	 * If defined as public, it prompts, "Illegal modifier for the enum constructor; only private is permitted".
	 * */
	TestEnumeration(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
