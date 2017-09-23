import java.util.ArrayList;

public class Task2 {
	public static <T> ArrayList<T> arrayToList(T[] array) {
		ArrayList<T> list = new ArrayList<>();
		for (T el : array) {
			list.add(el);
		}
		return list;
	}

	public static void main(String[] args) {
		Integer[] nums = {1, 2, 3, 4};
		String[] strs = {"a", "b", "c", "d"};
		Float[] flts = {1.1f, 2.2f, 3.3f, 4.4f};
		ArrayList<Integer> al1 = arrayToList(nums);
		ArrayList<String> al2 = arrayToList(strs);
		ArrayList<Float> al3 = arrayToList(flts);

		StringBuilder sb = new StringBuilder();
		for (Integer s : al1)
		{
			sb.append(s + " ");
		}
		sb.append("\n");
		for (String s : al2)
		{
			sb.append(s + " ");
		}
		sb.append("\n");
		for (Float s : al3)
		{
			sb.append(s + " ");
		}
		System.out.println(sb);
	}
}
