package per.itachi.test.algorithm;

import java.io.Serializable;

public class TestingSerializable implements Serializable {
	
	/**
	 * The serialVersionUID is related to member variable, variable name, variable type, 
	 * member method, method name, method return type, method access modifier, static method, 
	 * final modifier. <br/>
	 * And It is not related to order of members, content of member method, comments. <br/>
	 */
	private static final long serialVersionUID = -4338522539893327966L;
	
	private int id;
	private String name;
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	protected final long test() {
		long l = 1;
		return l;
	}
	
	public static final void testStatic() {
		
	}
	
	public static void main(String[] args) {
		
	}
}
