package per.itachi.test;

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
		
	}
}
