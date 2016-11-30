package per.itachi.test.util;

public class StringUtil {
	
	/**
	 * version 1
	 * */
	public static void getNextByKMP(CharSequence sub, int[] next) {
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
				++k;
			}
			next[curPos] = k;
		}
	}
	
	/**
	 * version 2
	 * */
	public static int[] getNextByKMP(CharSequence sub) {
		if (sub == null || sub.length() == 0) {
			return null;
		}
		int lengthSub = sub.length();
		int[] next = new int[lengthSub];
		int i = 0;
		int k = 0;
		next[0] = 0;
		while(i < lengthSub - 1) {//i < lengthSub - 1 ?
			if (k == 0 || sub.charAt(i) == sub.charAt(k)) {
				++i;
				++k;
				next[i] = k;
			} 
			else {//non-matching and try to find smaller matching
				k = next[k];
			}
		}
		return next;
	}
	
	/**
	 * return index of substring when it is matched at first time.
	 * @param version	1 and 2 currently 
	 * */
	public static int searchSubstrByKMP(CharSequence main, CharSequence sub, int version) {
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
		switch (version) {
		case 1:
			next = new int[lengthSub];
			getNextByKMP(sub, next);
			break;
		case 2:
			next = getNextByKMP(sub);
			break;
		default:
			next = getNextByKMP(sub);
			break;
		}
		if (next == null) {
			return -1;
		}
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
