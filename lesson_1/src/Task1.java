import java.util.Arrays;

public class Task1 {
	public static  <T> void swapElements(T[] array, int n1, int n2){
		if ((array.length - 1 >= n1) && (array.length - 1 >= n2)) {
			T temp = array[n1];
			array[n1] = array[n2];
			array[n2] = temp;
		}
	}

	public static void main(String args[]) {
		Integer[] nums = {1, 2, 3, 4};
		String[] strs = {"a", "b", "c", "d"};
		Float[] flts = {1.1f, 2.2f, 3.3f, 4.4f};
		swapElements(nums,1, 2);
		swapElements(strs,3, 0);
		swapElements(flts,0, 3);
		System.out.println(Arrays.toString(nums));
		System.out.println(Arrays.toString(strs));
		System.out.println(Arrays.toString(flts));
	}
}
