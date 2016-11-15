package per.itachi.test.util;

public class StringUtil {
	
	/**
	 * version 1
	 * */
	private static void getNextByKMP(CharSequence sub, int[] next) {
		if (sub == null || sub.length() == 0) {
			return;
		}
		if (next == null || next.length == 0) {
			return;
		}
		if (sub.length() > next.length) {
			return;
		}
		int curPos;
		int k;
		int lengthSub = sub.length();
		//start to calculate next array(function)
		next[0] = 0;
		for(curPos=1, k=0; curPos < lengthSub; ++curPos) {
			//non-matching and try to find another matching
			while (k > 0 && sub.charAt(curPos) != sub.charAt(k)) {
				k = next[k - 1];
			}
			//matching
			if (sub.charAt(curPos) == sub.charAt(k)) {
				++curPos;
			}
			next[curPos] = k;
		}
	}
	
	/**
	 * version 2
	 * */
	private static int[] getNextByKMP(CharSequence sub) {
		if (sub == null || sub.length() == 0) {
			return null;
		}
		int lengthSub = sub.length();
		int[] next = new int[lengthSub];
		return next;
	}
	
	/**
	 * return index of substring when it is matched at first time. 
	 * */
	public static int searchSubstrByKMP(CharSequence main, CharSequence sub) {
		if (main == null || main.length() == 0) {
			return -1;
		}
		if (sub == null || sub.length() == 0) {
			return -1;
		}
		if (main.length() < sub.length()) {
			return -1;
		}
		int i, q;
		int index;
		int lengthMain = main.length();
		int lengthSub = sub.length();
		int[] next = null;
		//prepare next array. 
		next = new int[lengthSub];
		getNextByKMP(sub, next);
//		if (next == null) {
//			return -1;
//		}
		//start to search
		for(i = 0, q = 0; i < lengthMain; ++i) {
			//move pattern if not equal
			while(q > 0 && main.charAt(i) != sub.charAt(q)) {
				q = next[q - 1];
			}
			//continue searching if equal
			if (main.charAt(i) == sub.charAt(q)) {
				++q;
			}
			if (q == lengthSub) {
				break;
			}
		}
		if (q == lengthSub) {
			index = i + 1 - lengthSub;
		} 
		else {
			index = -1;
		}
		return index;
	}
	
	public static int searchSubstrByKMP(char[] main, char[] sub) {
		if (main == null || main.length == 0) {
			return -1;
		}
		if (sub == null || sub.length == 0) {
			return -1;
		}
		if (main.length < sub.length) {
			return -1;
		}
		return 0;
	}
}
