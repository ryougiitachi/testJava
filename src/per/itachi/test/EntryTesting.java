package per.itachi.test;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EntryTesting {

	private static final Log log = LogFactory.getLog(EntryTesting.class);
	
	/**
	 * VM arguments: 
	 * 		-Dcommons.logging.configuration=etc/commons-logging.properties 
	 * 		-Dlog4j.configuration=file:etc/log4j.properties
	 * 		-Djava.util.logging.config.file=etc/jdk-logging.properties
	 * And actually, commons.logging.configuration is an invalid argument. <br></br>
	 * 
	 * If both slf4j and commons-logging exist, slf4j seems to have higher priority?
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Thread.sleep(0);//run immediately, instead of unlimited waiting.
		} 
		catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
		if (args.length >= 1) {
			TestingCases.manageTestingCases(args);
		}
		else {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} 
			catch (ClassNotFoundException e) {
				log.error(e.getMessage(), e);
				System.exit(-1);
			} 
			catch (InstantiationException e) {
				log.error(e.getMessage(), e);
				System.exit(-2);
			} 
			catch (IllegalAccessException e) {
				log.error(e.getMessage(), e);
				System.exit(-3);
			} 
			catch (UnsupportedLookAndFeelException e) {
				log.error(e.getMessage(), e);
				System.exit(-4);
			}
		}
//		TestingCases.testInherit();//correct
//		TestingCases.testDate();//correct
	}
}
