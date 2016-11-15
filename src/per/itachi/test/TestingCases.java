package per.itachi.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
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
		case 8:
			testOverflow();
			break;
		case 9:
			testSearchString();
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
		log.debug(String.format("The default locale is %s", Locale.getDefault()));
		log.debug(String.format("The default time zone is %s", tz));
		log.debug(String.format("The default time zone class name is %s", tz.getClass()));
		log.debug(String.format("The class name of calendar is %s", calendarAD.getClass()));
		try {
			dateBC = sdf.parse("BC 0001-02-29 00:00:51");
			dateAD = sdf.parse("AD 0001-01-01 08:00:00");
			calendarAD.setTime(dateAD);
			calendarAD.set(Calendar.YEAR, 4);
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
		} 
		catch (ParseException e) {
			log.error(e.getMessage(), e);
		}
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
		long ltimeStart, ltimeEnd;
		count = 1000000;
		//In this case, BF is more efficient than KMP. 
		//KMP
		ltimeStart = System.currentTimeMillis();
		for (i=0; i < count; ++i) {
			StringUtil.searchSubstrByKMP(strMain, strPattern);
		}
		ltimeEnd = System.currentTimeMillis();
		log.debug(String.format("The duration is %d, start is %d, end is %d", 
				ltimeEnd - ltimeStart, ltimeStart, ltimeEnd));
		log.debug(String.format("The position is %d", StringUtil.searchSubstrByKMP(strMain, strPattern)));
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
}
