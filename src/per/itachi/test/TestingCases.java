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
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
}