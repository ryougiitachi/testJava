package per.itachi.test;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class EntryTesting {

	/**
	 * VM arguments: 
	 * 		-Dcommons.logging.configuration=etc/commons-logging.properties 
	 * 		-Dlog4j.configuration=file:etc/log4j.properties
	 * 		-Djava.util.logging.config.file=etc/jdk-logging.properties
	 * And actually, commons.logging.configuration is an invalid argument. 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length >= 1) {
			TestingCases.manageTestingCases(args);
		}
		else {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.exit(-1);
			} 
			catch (InstantiationException e) {
				e.printStackTrace();
				System.exit(-2);
			} 
			catch (IllegalAccessException e) {
				e.printStackTrace();
				System.exit(-3);
			} 
			catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
				System.exit(-4);
			}
		}
//		TestingCases.testInherit();//correct
//		TestingCases.testDate();//correct
	}
}
