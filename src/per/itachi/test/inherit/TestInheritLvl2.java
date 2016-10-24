package per.itachi.test.inherit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestInheritLvl2 extends TestInheritLvl1 {
	
	private static Log log = LogFactory.getLog(TestInheritLvl2.class);
	
	protected int protectNumber;//no error, return value according to class
	
	public int publicNumber;//no error, return value according to class

	public TestInheritLvl2() {
		protectNumber = -2;
		publicNumber = -2;
		log.debug(String.format("There is TestInheritLvl2 constructing."));
	}

	public int getProtectNumber() {
		return protectNumber;
	}

	public void setProtectNumber(int protectNumber) {
		this.protectNumber = protectNumber;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		log.debug(String.format("There is TestInheritLvl2 finalising."));
	}

}
