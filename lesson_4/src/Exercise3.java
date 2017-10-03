import java.util.Scanner;

public class Exercise3 {
	static Object scanMonitor = new Object();
	static Object printMonitor = new Object();

	public static void main(String[] args) {
		//String test = "scan 25" + System.getProperty("line.separator") + "print 50" + System.getProperty("line.separator") + "print 5" + System.getProperty("line.separator") + "scan 5";
		//Scanner sc = new Scanner(test);
		Scanner sc = new Scanner(System.in);
		String command;
		int numberOfPages;
		while(true) {
			command = sc.nextLine();
			if (command.startsWith("print ")) {
				numberOfPages = Integer.parseInt(command.substring(6));
				final int n = numberOfPages;
				new Thread(() -> {
					printPages(n);
				}).start();
			}
			if (command.startsWith("scan ")) {
				numberOfPages = Integer.parseInt(command.substring(5));
				final int n = numberOfPages;
				new Thread(() -> {
					scanPages(n);
				}).start();
			}
		}
	}

	private static void scanPages(int numberOfPages) {
		synchronized (scanMonitor) {
			System.out.print("Scanned ");
			for (int i = 0; i < numberOfPages; i++) {
				System.out.print((i+1) + " ");
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("pages");
		}
	}

	private static void printPages(int numberOfPages) {
		synchronized (printMonitor) {
			System.out.print("Printed ");
			for (int i = 0; i < numberOfPages; i++) {
				System.out.print((i+1) + " ");
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("pages");
		}
	}
}
