package per.itachi.test.compile;

import java.util.List;

public class Entry {

}

class Testing {
	
	/**
	 * Eclipse won't regard this class as main class, 
	 * and it will prompt, "Error: Could not find or load main class per.itachi.test.compile.Entry", 
	 * if running it manually.
	 * */
	public static void main(String[] args) {
		System.out.println("t");
		List<String> list1 = null;
		List<Integer> list2 = null;
//		list1 = list2;//Type mismatch: cannot convert from List<Integer> to List<String>
	}
}

class Parent {
	public void run(List<Integer> list) {
		
	}
}

class Child extends Parent {
//	public void run(List<String> list) {
//		
//	}
}
