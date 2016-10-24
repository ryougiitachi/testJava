package per.itachi.test.inherit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestInheritLvl3 extends TestInheritLvl2 {

	private static Log log = LogFactory.getLog(TestInheritLvl3.class);
	
	public TestInheritLvl3() {
		super();//It doesn't make difference in whether to add super for default constructor?
		log.debug(String.format("There is TestInheritLvl3 constructing."));
	}

	public int getProtectNumber() {
		return super.protectNumber;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		log.debug(String.format("There is TestInheritLvl3 finalising."));
	}

}
