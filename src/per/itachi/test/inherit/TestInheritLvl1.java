package per.itachi.test.inherit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestInheritLvl1 {
	
	private static Log log = LogFactory.getLog(TestInheritLvl1.class);
	
	protected int protectNumber;
	
	public int publicNumber;
	
	public TestInheritLvl1() {
		protectNumber = -1;
		publicNumber = -1;
		log.debug(String.format("There is TestInheritLvl1 constructing."));
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
		log.debug(String.format("There is TestInheritLvl1 finalising."));
	}

}
