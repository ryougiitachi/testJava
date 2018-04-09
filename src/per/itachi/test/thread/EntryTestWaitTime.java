package per.itachi.test.thread;

public class EntryTestWaitTime {

	public static void main(String[] args) {

	}
	
	static class Bank {
		
		private double []accounts;
		
		public Bank() {
			accounts = new double[2];
			for (int i = 0, length = accounts.length; i < length; i++) {
				accounts[i] = 10000.0;
			}
		}
		
		public void transfer() {
			
		}
		
	}
	
	static class WaitUnlimitedRunnable implements Runnable {
		
		private Bank bank;

		public WaitUnlimitedRunnable(Bank bank) {
			this.bank = bank;
		}
		
		@Override
		public void run() {
			bank.transfer();
		}
	}
	
	static class WaitlimitedRunnable implements Runnable {
		
		private Bank bank;

		public WaitlimitedRunnable(Bank bank) {
			this.bank = bank;
		}
		
		@Override
		public void run() {
			bank.transfer();
		}
	}

}
