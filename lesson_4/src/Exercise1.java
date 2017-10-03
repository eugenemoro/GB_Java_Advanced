public class Exercise1 {
	static Object monitor = new Object();
	static char nextSymb = 'A';
	public static void main(String[] args) {
		new Thread(() -> {
			try {
				for (int i = 0; i < 5; i++) {
					synchronized (monitor) {
						while (nextSymb != 'A'){
							monitor.wait();
						}
						System.out.print(nextSymb);
						nextSymb = 'B';
						monitor.notifyAll();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		new Thread(() -> {
			try {
				for (int i = 0; i < 5; i++) {
					synchronized (monitor) {
						while (nextSymb != 'B'){
							monitor.wait();
						}
						System.out.print(nextSymb);
						nextSymb = 'C';
						monitor.notifyAll();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		new Thread(() -> {
			try {
				for (int i = 0; i < 5; i++) {
					synchronized (monitor) {
						while (nextSymb != 'C'){
							monitor.wait();
						}
						System.out.print(nextSymb);
						nextSymb = 'A';
						monitor.notifyAll();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
