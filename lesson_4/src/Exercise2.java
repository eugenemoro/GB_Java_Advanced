import java.io.*;

public class Exercise2 {
	static File file = new File("123.txt");
	static int turn = 1;
	static DataOutputStream dos;

	public static void main(String[] args) {
		try {
			dos = new DataOutputStream(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		new Thread(() -> {
			writeIntoFile("Thread1 \n");
		}).start();
		new Thread(() -> {
			writeIntoFile("Thread2 \n");
		}).start();
		new Thread(() -> {
			writeIntoFile("Thread3 \n");
		}).start();
	}

	private synchronized static void writeIntoFile(String str){
		for (int i = 0; i < 10; i++) {
			try {
				dos.writeBytes(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
