import java.util.ArrayList;

public class Exercise {

	public int[] whatsAfterFour(int[] arr) throws RuntimeException{
		ArrayList<Integer> list = new ArrayList<>();
		for (int el : arr) {
			list.add(el);
		}
		if (list.contains(4)){
			int n = list.lastIndexOf(4) + 1;
			list = new ArrayList<>(list.subList(n, list.size()));
			int[] output = new int[list.size()];
			if (list.size() != 0){
				for (int i = 0; i < list.size(); i++) {
					output[i] = list.get(i);
				}
			}
			return output;
		} else {
			throw new RuntimeException();
		}
	}

	public boolean containsOnesAndFours(int[] arr){
		if (arr.length == 0) return false;
		for (int el : arr) {
			if ((el != 1) && (el != 4)) return false;
		}
		return true;
	}
}
