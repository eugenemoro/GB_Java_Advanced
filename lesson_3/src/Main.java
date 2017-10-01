import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

public class Main {
	static int count = 0;
	public static void main(String[] args) {

		//task 1
		readFile(new File("txt/1.txt"));

		//task 2
		combineFiles();

		//task 3
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			System.out.println("Enter the page number:");
			boolean end = false;
			while (!end) {
				String s = br.readLine();
				if (s.equals("/end")) {
					end = true;
				} else {
					try {
						printOutPage(Integer.parseInt(s));
					} catch (NumberFormatException e) {
						System.out.println("Numeric value needed");
					}
				}
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	private static void printOutPage(int page) {
		Long t = System.currentTimeMillis();
		File file = new File("txt/bigFile.txt");
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file), 1048576)) {
			byte[] arrPage = new byte[1800];
			if (Math.round(file.length() / 1800) >= page) {
				bis.skip((page-1) * 1800);
				bis.read(arrPage);
				StringBuilder s = new StringBuilder();
				for (int i = 0; i < arrPage.length; i++) {
					s.append((char) arrPage[i]);
				}
				System.out.println(s);
			} else {
				System.out.println("The page number is too big");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readFile(File file) {
		try (FileInputStream inputStream = new FileInputStream(file)) {
			byte[] byteArray = new byte[25];
			int x = -1;
			StringBuilder s = new StringBuilder();
			while ((x = inputStream.read(byteArray)) > 0) {
				for (int i = 0; i < x; i++) {
					s.append((char) byteArray[i]);
				}
			}
			System.out.println(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void combineFiles() {
		try 	(FileInputStream in1 = new FileInputStream("txt/1.txt");
					FileInputStream in2 = new FileInputStream("txt/2.txt");
					FileInputStream in3 = new FileInputStream("txt/3.txt");
					FileInputStream in4 = new FileInputStream("txt/4.txt");
					FileInputStream in5 = new FileInputStream("txt/5.txt");
					FileOutputStream out = new FileOutputStream("txt/result.txt")){
			ArrayList<InputStream> arrayListOfStreams = new ArrayList<>();
			arrayListOfStreams.add(in1);
			arrayListOfStreams.add(in2);
			arrayListOfStreams.add(in3);
			arrayListOfStreams.add(in4);
			arrayListOfStreams.add(in5);
			Enumeration<InputStream> en = Collections.enumeration(arrayListOfStreams);
			SequenceInputStream seqInput = new SequenceInputStream(en);
			int x = seqInput.read();
			while (x != -1) {
				out.write(x);
				x = seqInput.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File createFile(int size) {
		count++;
		byte[] arr = new byte[size];
		Arrays.fill(arr, (byte) (Math.random() * 127));
		File file = new File(count + ".txt");
		try (OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
			out.write(arr);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
}
