package per.itachi.test.util;

public class StringUtil {
	
	/**
	 * get KMP next sequence.
	 * */
	private static int[] getNextArrayByKMP(CharSequence sub) {
		if (sub == null || sub.length() == 0) {
			return null;
		}
		int[] next = new int[sub.length()];
		int i = 0;
		int k = -1;
		next[0] = -1;
		while(i < sub.length() - 1) {
			if (k == -1 || sub.charAt(i) == sub.charAt(k)) {
				++i;
				++k;
				next[i] = k;
			} 
			else {
				k = next[k];
			}
		}
		return next;
	}
	
	/**
	 * search substring using KMP algorithm. 
	 * */
	public static int searchSubByKMP(CharSequence main, CharSequence sub) {
		if (main == null || main.length() == 0) {
			return -1;
		}
		if (sub == null || sub.length() == 0) {
			return -1;
		}
		int i = 0;
		int j = 0;
		int[] next = getNextArrayByKMP(sub);
		
		return 0;
	}

}
