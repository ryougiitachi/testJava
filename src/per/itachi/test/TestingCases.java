package per.itachi.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import per.itachi.test.inherit.TestInheritLvl1;
import per.itachi.test.inherit.TestInheritLvl2;
import per.itachi.test.inherit.TestInheritLvl3;
import per.itachi.test.util.StringUtil;

public class TestingCases {
	
	private final static Log log = LogFactory.getLog(TestingCases.class);
//	private final static Logger logger = Logger.getLogger(TestingCases.class);
	
	private static final Formatter formatter = new Formatter();
	
	public static void manageTestingCases(String[] args) {
		if (args.length <= 0) {
			return;
		}
		
		String strRegex = "[-\\+]?\\d+";
		int iCode = 0;
		if (Pattern.matches(strRegex, args[0])) {
			try {
				iCode = Integer.parseInt(args[0]);
			} 
			catch (NumberFormatException e) {
				e.printStackTrace();
				return; 
			}
		}
		switch (iCode) {
		case 1:
			testBasicDataTypes();
			break;
		case 2:
			testByteOperation();
			break;
		case 3:
			testCharset();
			break;
		case 4:
			testArray();
			break;
		case 5:
			testInherit();
			break;
		case 6:
			testDate();
			break;
		case 7:
			testFinal();
			break;
		case 8:
			testOverflow();
			break;
		case 9:
			testSearchString();
			break;
		case 10:
			testBasicInfo();
			testDouble();
			break;
		case 11:
			testGenRandomPasswd();
			break;
		case 12:
			testRandomAccessFile();
		case 13:
			testStream();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 1
	 * */
	public static void testBasicDataTypes() {
		log.debug(String.format("long = %d", -123456789123l));
		log.debug(String.format("float = %03.5f", -1.0f));
		
		char c = 30000;
//		System.out.printf("char = %x\n", c);	// java.util.IllegalFormatConversionException  
		log.debug(c);
	}
	
	/**
	 * 2 
	 * */
	public static void testByteOperation() {
		DateFormat saf = new SimpleDateFormat("GG yyyy-MM-dd HH:mm:ss.SSS zzz");
		Calendar calendar;
		calendar = Calendar.getInstance();
		log.debug(String.format("Current year value is %d", calendar.get(Calendar.YEAR)));
		log.debug(String.format("Current month value is %d", calendar.get(Calendar.MONTH)));
		log.debug(String.format("Current day value is %d", calendar.get(Calendar.DAY_OF_MONTH)));
		calendar.set(Calendar.YEAR, 155);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		log.debug(String.format("Current locale is %s", Locale.getDefault()));
		log.debug(saf.format(calendar.getTime()));
		log.debug(String.format("Birth date is %d", calendar.getTimeInMillis()));
		log.debug(String.format("Birth date is %#X", calendar.getTimeInMillis()));
		
		long lTime;
		byte[] bytesTime;
		lTime = calendar.getTimeInMillis();
		bytesTime = new byte[Long.SIZE >> 3];
		for (int i = 0; i < bytesTime.length; i++) {
			bytesTime[i] = Long.valueOf((lTime >>> (i * 8)) & 0x000000ff).byteValue();
		}
		
		File file = new File("data/ByteOperation.data");
//		log.debug(String.format("whether to create directory is %b", file.mkdir()));
//		log.debug(String.format("whether to create directories is %b", file.mkdirs()));
		try {
			log.debug(String.format("whether to create file is %b", file.createNewFile()));
		} 
		catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		log.debug(String.format("The parent of file is %s", file.getParent()));
		log.debug(String.format("The parent of file is %b", file.getParentFile().exists()));
		log.debug(String.format("The parent of directory is %b", file.getParentFile().isDirectory()));
		log.debug(String.format("The parent of directory is %b", file.getParentFile().isFile()));
		
		OutputStream fos = null;
		if (!file.exists() || file.length() <= 0) {
			try {
				fos = new FileOutputStream(file);
				fos.write(bytesTime);
			} 
			catch (FileNotFoundException e) {
				log.error(e.getMessage(), e);
			} 
			catch (IOException e) {
				log.error(e.getMessage(), e);
			}
			finally {
				if (fos != null) {
					try {
						fos.close();
					} 
					catch (IOException e) {
						log.error(e.getMessage(), e);
					}
					fos = null;
				}
			}
		}
		// This attribute is used to browse the free space of partition. 
		log.debug(String.format("The free space is %d", file.getFreeSpace() >> 10 >> 10));
		Arrays.fill(bytesTime, (byte)0); 
		lTime = 0;
		
		InputStream fis = null;
		if (file.exists() && file.length() > 0) {
			try {
				fis = new FileInputStream(file);
				fis.read(bytesTime);
			} 
			catch (FileNotFoundException e) {
				log.error(e.getMessage(), e);
			} 
			catch (IOException e) {
				log.error(e.getMessage(), e);
			}
			finally {
				if (fis != null) {
					try {
						fis.close();
					} 
					catch (IOException e) {
						log.error(e.getMessage(), e);
					}
					fis = null;
				}
			}
		}
		for (int i = 0; i < bytesTime.length; i++) {
			lTime |= (bytesTime[i] & 0xffl) << (i * 8);	// this l is a key point. 
			log.debug(String.format("bytesTime %d from file is %d", i, bytesTime[i]));
		}
		log.debug(String.format("Birth date from file is %d", lTime));
		log.debug(String.format("Birth date from file is %#X", lTime));
	}
	
	/**
	 * 3
	 * */
	public static void testCharset() {
		Map<String, Charset> mapCharsets = Charset.availableCharsets();
		log.debug(String.format("Determine whether or not to support charset is %s", mapCharsets.getClass()));
		log.debug(String.format("Determine whether or not to support charset is %s", Charset.isSupported("utf8")));
		log.debug(String.format("Determine whether or not to support charset is %s", mapCharsets.get("utf8")));
		log.debug(String.format("Determine whether or not to support charset is %s", mapCharsets.get("utf-8")));
		log.debug(String.format("Determine whether or not to support charset is %s", mapCharsets.get("UTF-8")));
		log.debug(String.format("The default charset is %s", Charset.defaultCharset()));
	}
	
	/**
	 * 4
	 * */
	private static void testArray() {
		int[] src = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		int[] des = new int[]{0, 0, 0, 0, 0};
		System.arraycopy(src, 0, des, 0, src.length);	//ArrayIndexOutOfBoundsException if parameter length > des.length 
		log.debug(String.format("List is %s", des[4]));
	}
	
	/**
	 * 5
	 * */
	static void testInherit() {
		TestInheritLvl1 p1lvl1=null, p1lvl2=null, p1lvl3=null;
		TestInheritLvl2 p2lvl2;
		TestInheritLvl3 p3lvl3;
		
		log.debug(String.format("Initialising p1lvl1, p1lvl2, p1lvl3... "));
		p1lvl1=new TestInheritLvl1();
		p2lvl2=new TestInheritLvl2();
		p3lvl3=new TestInheritLvl3();
		
		p1lvl2=p2lvl2; 
		p1lvl3=p3lvl3;
		log.debug(String.format("Complete initialising p1lvl1, p1lvl2, p1lvl3."));
		
		log.debug(String.format("The protect result of TestInheritLvl1 is %d", p1lvl1.getProtectNumber()));
		log.debug(String.format("The protect result of TestInheritLvl2 is %d", p1lvl2.getProtectNumber()));
		log.debug(String.format("The protect result of TestInheritLvl3 is %d", p1lvl3.getProtectNumber()));
		log.debug(String.format("The public result of TestInheritLvl1 is %d", p1lvl1.publicNumber));
		log.debug(String.format("The public result of TestInheritLvl1 is %d", p1lvl2.publicNumber));
		log.debug(String.format("The public result of TestInheritLvl1 is %d", p1lvl3.publicNumber));
		log.debug(String.format("The public result of TestInheritLvl2 is %d", p2lvl2.publicNumber));
		log.debug(String.format("The public result of TestInheritLvl3 is %d", p3lvl3.publicNumber));
		
		log.debug(String.format("Complete testing p1lvl1, p1lvl2, p1lvl3."));
		p1lvl1=null;
		p1lvl2=null;
		p1lvl3=null;
//		System.gc();
	}
	
	/**
	 * 6
	 * */
	protected static void testDate() {
		DateFormat sdf = new SimpleDateFormat("GG yyyy-MM-dd HH:mm:ss", Locale.UK);
		long ltimeBC = 0;
		long ltimeAD = 0;
		Date dateBC = null;
		Date dateAD = null;
		Date dateTemp = new Date(-62135798400000l);
		TimeZone tz = TimeZone.getDefault();
		Calendar calendarAD = Calendar.getInstance();
		GregorianCalendar gre = (GregorianCalendar)calendarAD;
		log.debug(String.format("The default locale is %s", Locale.getDefault()));
		log.debug(String.format("The default time zone is %s", tz));
		log.debug(String.format("The default time zone class name is %s", tz.getClass()));
		log.debug(String.format("The class name of calendar is %s", calendarAD.getClass()));
		try {
			dateBC = sdf.parse("BC 0001-02-29 00:00:51");
			dateAD = sdf.parse("AD 1970-01-03 08:00:00");
			calendarAD.setTime(dateAD);
			calendarAD.set(Calendar.YEAR, 2000);
			calendarAD.set(Calendar.MONTH, Calendar.FEBRUARY);
			calendarAD.set(Calendar.DAY_OF_MONTH, 29);
			calendarAD.set(Calendar.HOUR_OF_DAY, 0);
			calendarAD.set(Calendar.MINUTE, 0);
			calendarAD.set(Calendar.SECOND, 0);
			calendarAD.set(Calendar.MILLISECOND, 0);
			ltimeBC = dateBC.getTime();
			ltimeAD = dateAD.getTime();
//			ltimeAD = sdf.parse("AD 0000-00-00 00:00:00").getTime();
			log.debug(String.format("The value of date bc is %d", ltimeBC));
//			log.debug(String.format("The value of week bc is %d", ltimeBC));
			log.debug(String.format("The value of date ad is %d", ltimeAD));
			log.debug(String.format("The value of ad is %s", sdf.format(calendarAD.getTime())));
			log.debug(String.format("The value of era ad is %d", calendarAD.get(Calendar.ERA)));
			log.debug(String.format("The value of week ad is %d", calendarAD.get(Calendar.DAY_OF_WEEK)));
			log.debug(String.format("The value of time zone offset ad is %d", calendarAD.get(Calendar.ZONE_OFFSET)));
			log.debug(String.format("The value of time DST offset ad is %d", calendarAD.get(Calendar.DST_OFFSET)));
			log.debug(String.format("The value of calendar date ad is %d", calendarAD.getTimeInMillis()));
			log.debug(String.format("The value of dateTemp is %s", sdf.format(dateTemp)));
			log.debug(String.format("The date of Gregorian Calendar change is %s", sdf.format(gre.getGregorianChange())));
			log.debug(String.format("The date of Gregorian Calendar change is %d", gre.getGregorianChange().getTime()));
		} 
		catch (ParseException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 7
	 * */
	static void testFinal() {
		final StringBuilder builder = new StringBuilder();
		builder.append("It seems that final won't forbid changing object member value, ");
		builder.append(true);
		log.debug(builder.toString());
	}
	
	/**
	 * 8
	 * */
	static void testOverflow() {
		int i = Integer.MAX_VALUE;
		long l = Long.MAX_VALUE;
		log.debug(String.format("The value of int is %d", i));
		log.debug(String.format("The overflow of int is %d", ++i));
		log.debug(String.format("The overflow of int is %d", Integer.MAX_VALUE + 1));
		log.debug(String.format("The overflow of int is %d", Integer.MAX_VALUE + 1l));
		log.debug(String.format("The value of long is %d", l));
		log.debug(String.format("The overflow of long is %d", ++l));
		log.debug(String.format("The overflow of long is %d", Long.MAX_VALUE + 1));
	}
	
	/**
	 * 9
	 * */
	static void testSearchString() {
		String strMain = "ababxbababcabfdsss";
		String strPattern = "abfdsss";
		int i, count;
		int versionKMP = 1;
		int[] next = new int[strPattern.length()];
		StringBuilder builder = new StringBuilder(128);
		long ltimeStart, ltimeEnd;
		count = 1000000;
		//In this case, BF is more efficient than KMP. 
		//version 1 is more efficient than version 2. version 2 is wrong ? 
		//KMP next array
		StringUtil.getNextByKMP("abcdabd", next);//version 1
		for(i=0; i < next.length; ++i){
			builder.append(next[i]).append(' ');
		}
		log.debug(builder);
		builder.setLength(0);
		next = StringUtil.getNextByKMP("abcdabd");//version 2
		for(i=0; i < next.length; ++i){
			builder.append(next[i]).append(' ');
		}
		log.debug(builder);
		builder.setLength(0);
		//KMP version 1
		versionKMP = 1;
		ltimeStart = System.currentTimeMillis();
		for (i=0; i < count; ++i) {
			StringUtil.searchSubstrByKMP(strMain, strPattern, versionKMP);
		}
		ltimeEnd = System.currentTimeMillis();
		log.debug(String.format("The duration is %d, start is %d, end is %d", 
				ltimeEnd - ltimeStart, ltimeStart, ltimeEnd));
		log.debug(String.format("The position is %d", StringUtil.searchSubstrByKMP(strMain, strPattern, versionKMP)));
		//KMP version 2
		versionKMP = 2;
		ltimeStart = System.currentTimeMillis();
		for (i=0; i < count; ++i) {
			StringUtil.searchSubstrByKMP(strMain, strPattern, versionKMP);
		}
		ltimeEnd = System.currentTimeMillis();
		log.debug(String.format("The duration is %d, start is %d, end is %d", 
				ltimeEnd - ltimeStart, ltimeStart, ltimeEnd));
		log.debug(String.format("The position is %d", StringUtil.searchSubstrByKMP(strMain, strPattern, versionKMP)));
		//BF
		ltimeStart = System.currentTimeMillis();
		for (i=0; i < count; ++i) {
			strMain.indexOf(strPattern);
		}
		ltimeEnd = System.currentTimeMillis();
		log.debug(String.format("The duration is %d, start is %d, end is %d", 
				ltimeEnd - ltimeStart, ltimeStart, ltimeEnd));
		log.debug(String.format("The position is %d", strMain.indexOf(strPattern)));
	}
	
	/**
	 * 10
	 * */
	static void testBasicInfo() {
		Runtime runtime = Runtime.getRuntime();
		log.debug(formatter.format("The number of available processors is %d", runtime.availableProcessors()));
	}
	
	/**
	 * 10
	 * */
	static void testDouble() {
		double d = 1.0;
		long ldouble = Double.doubleToLongBits(d);
		long rawldouble = Double.doubleToRawLongBits(d);
		log.debug(String.format("The value of doubleToLongBits is %d", ldouble));
		log.debug(String.format("The value of doubleToLongBits is %016X", ldouble));
		log.debug(String.format("The value of doubleToLongBits is %s", Double.toHexString(d)));
		log.debug(String.format("The value of doubleToRawLongBits is %d", rawldouble));
		log.debug(String.format("The value of doubleToRawLongBits is %016X", rawldouble));
		long l = 1l;
		double dlong = Double.longBitsToDouble(l);
		log.debug(String.format("The value of longBitsToDouble is %f", dlong));
		double d1 = -1.0;
		log.debug(String.format("The value of doubleToLongBits is %d", Double.doubleToLongBits(d1)));
		log.debug(String.format("The value of doubleToLongBits is %016X", Double.doubleToLongBits(d1)));
		log.debug(String.format("The value of doubleToRawLongBits is %d", Double.doubleToRawLongBits(d1)));
		log.debug(String.format("The value of doubleToRawLongBits is %016X", Double.doubleToRawLongBits(d1)));
		double d2 = 2.0;
		log.debug(String.format("The value of d2 doubleToLongBits is %d", Double.doubleToLongBits(d2)));
		log.debug(String.format("The value of d2 doubleToLongBits is %016X", Double.doubleToLongBits(d2)));
		log.debug(String.format("The value of d2 doubleToRawLongBits is %d", Double.doubleToRawLongBits(d2)));
		log.debug(String.format("The value of d2 doubleToRawLongBits is %016X", Double.doubleToRawLongBits(d2)));
		double d3 = 3.0;
		log.debug(String.format("The value of d3 doubleToLongBits is %d", Double.doubleToLongBits(d3)));
		log.debug(String.format("The value of d3 doubleToLongBits is %016X", Double.doubleToLongBits(d3)));
		log.debug(String.format("The value of d3 doubleToRawLongBits is %d", Double.doubleToRawLongBits(d3)));
		log.debug(String.format("The value of d3 doubleToRawLongBits is %016X", Double.doubleToRawLongBits(d3)));
		
		log.debug(String.format("The value of NEGATIVE_INFINITY is %f", Double.NEGATIVE_INFINITY + 1.0));
		log.debug(String.format("The value of POSITIVE_INFINITY is %s", Double.POSITIVE_INFINITY));
		
		try {
			int i =(int) (1.0/0.0);
			log.debug(String.format("The value of i is %d", i));
			log.debug(String.format("The value of 1.0/0.0 is %f", 1/0.0));
			log.debug(String.format("The value of 0.0/0.0 is %f", 0.0/0.0));
			log.debug(String.format("The value of 0.0/0.0 is %f", 0/0.0));
			log.debug(String.format("The value of 0.0/0.0 is %f", 0.0/0));
//			log.debug(String.format("The value of 0.0/0.0 is %d", 0.0/0));//java.util.IllegalFormatConversionException: d != java.lang.Double
			log.debug(String.format("The value of 0.0/0.0 is %f", 0/0));
			log.debug(String.format("The value of 1/0 is %f", 1/0));
		} 
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 11 Generate random passcode. 
	 * */
	static void testGenRandomPasswd() {
		int i, count;
		count = 6;
		for (i=0; i < count; ++i) {
			log.info(genRandomPasscode(8));
			
			try {
				Thread.sleep(10l);
			} 
			catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * Generate random passcode. 
	 * */
	private static String genRandomPasscode(int passwordLength) {
		if (passwordLength < 8) {
			return null;
		}
		
		String strPassword = null;
		StringBuilder strbuilderPassword;
		strbuilderPassword = new StringBuilder(passwordLength);
		
		int i, length, index;
		
		List<Character> listUpper;
		List<Character> listLower;
		List<Character> listNumeric;
		List<Character> listSpecchar;
		List<Character> listAll;
		Random random;
		boolean hasUpper	= false;
		boolean hasLower	= false;
		boolean hasNumeric	= false;
		boolean hasSpecchar	= false;
		
		listUpper		= new ArrayList<Character>(32);
		listLower		= new ArrayList<Character>(32);
		listNumeric		= new ArrayList<Character>(32);
		listSpecchar	= new ArrayList<Character>(32);
		listAll			= new ArrayList<Character>(80);
		random = new Random(System.currentTimeMillis());
		
		//initialise the sequences of elements. 
		for (i=0; i < TestingConst.ELEMENT_STR_UPPER.length(); ++i) {
			listUpper.add(TestingConst.ELEMENT_STR_UPPER.charAt(i));
			listAll.add(TestingConst.ELEMENT_STR_UPPER.charAt(i));
		}
		for (i=0; i < TestingConst.ELEMENT_STR_LOWER.length(); ++i) {
			listLower.add(TestingConst.ELEMENT_STR_LOWER.charAt(i));
			listAll.add(TestingConst.ELEMENT_STR_LOWER.charAt(i));
		}
		for (i=0; i < TestingConst.ELEMENT_STR_NUMERIC.length(); ++i) {
			listNumeric.add(TestingConst.ELEMENT_STR_NUMERIC.charAt(i));
			listAll.add(TestingConst.ELEMENT_STR_NUMERIC.charAt(i));
		}
		for (i=0; i < TestingConst.ELEMENT_STR_SPECCHAR.length(); ++i) {
			listSpecchar.add(TestingConst.ELEMENT_STR_SPECCHAR.charAt(i));
			listAll.add(TestingConst.ELEMENT_STR_SPECCHAR.charAt(i));
		}
		
		//
		if (!hasUpper) {
			index = random.nextInt() % listUpper.size();
//			strbuilderPassword.append(listUpper.get(random.nextInt(listUpper.size())));
			strbuilderPassword.append(listUpper.get(index >= 0 ? index : -index));
			hasUpper = true;
		}
		if (!hasLower) {
			index = random.nextInt() % listLower.size();
//			strbuilderPassword.append(listLower.get(random.nextInt(listLower.size())));
			strbuilderPassword.append(listLower.get(index >= 0 ? index : -index));
			hasLower = true;
		}
		if (!hasNumeric) {
			index = random.nextInt() % listNumeric.size();
//			strbuilderPassword.append(listNumeric.get(random.nextInt(listNumeric.size())));
			strbuilderPassword.append(listNumeric.get(index >= 0 ? index : -index));
			hasNumeric = true;
		}
		if (!hasSpecchar) {
			index = random.nextInt() % listSpecchar.size();
//			strbuilderPassword.append(listSpecchar.get(random.nextInt(listSpecchar.size())));
			strbuilderPassword.append(listSpecchar.get(index >= 0 ? index : -index));
			hasSpecchar = true;
		}
		
		//
		for (i=4; i < passwordLength; ++i) {
			index = random.nextInt() % listAll.size();
//			strbuilderPassword.append(listAll.get(random.nextInt(listAll.size())));
			strbuilderPassword.append(listAll.get(index >= 0 ? index : -index));
		}
		
		return strbuilderPassword.toString();
	}
	
	/**
	 * 12
	 * */
	static void testRandomAccessFile(){
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile("data/TestRandomAccessFile.data", "rw");
			raf.writeLong(1l);//00 00 00 00 00 00 00 01 stored in file
			raf.writeFloat(1.0f);//3F 80 00 00 stored in file
			raf.writeDouble(1.0);// 3F F0 00 00 00 00 00 00 stored in file
		} 
		catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		} 
		catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		finally {
			if (raf != null) {
				try {
					raf.close();
				}
				catch (IOException e) {
					log.error(e.getMessage(), e);
				}
				finally {
					raf = null;
				}
			}
		}
	}
	
	/**
	 * 13
	 * */
	static void testStream() {
		InputStream fis = null;
		InputStream bis = null;
		Scanner scanner = null;
		try {
			fis = new FileInputStream("etc/log4j.xml");
			bis = new BufferedInputStream(fis);
			scanner = new Scanner(bis);
		} 
		catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		}
		finally {
			if (scanner != null) {
				scanner.close();//all the streams will be closed at this step. 
				scanner = null;
			}
			if (bis != null) {
				try {
					bis.close();
				} 
				catch (IOException e) {
					log.error(e.getMessage(), e);
				}
				bis = null;
			}
			if (fis != null) {
				try {
					fis.close();
				} 
				catch (IOException e) {
					log.error(e.getMessage(), e);
				}
				fis = null;
			}
		}
	}
}
