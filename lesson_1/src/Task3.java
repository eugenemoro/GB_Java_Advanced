import java.util.ArrayList;
import java.util.Iterator;

abstract class Fruit {
	float weight;
	public float getWeight() {
		return weight;
	}
}

class Apple extends Fruit {
	public Apple() {
		weight = 1f;
	}
}

class Orange extends Fruit {
	public Orange() {
		weight = 1.5f;
	}
}

class Box<T extends Fruit>{
	ArrayList<T> box = new ArrayList<>();

	public Box() {
		this.box = box;
	}

	public void addFruit(T fruit){
		box.add(fruit);
	}

	public float getWeight() {
		float sum = 0;
		for (T el : box){
			sum += el.getWeight();
		}
		return sum;
	}

	public boolean compare(Box otherBox){
		return Math.abs(this.getWeight() - otherBox.getWeight()) < 0.001;
	}

	public void moveFruits(Box<T> otherBox){
		Iterator<T> it = box.iterator();
		while (it.hasNext()) {
				T temp = it.next();
				otherBox.addFruit(temp);
				it.remove();
			}
		}
	}


public class Task3 {
	public static void main(String[] args) {
		Box<Orange> boxOfOranges1 = new Box<>();
		for (int i = 0; i < 10; i++) {
			boxOfOranges1.addFruit(new Orange());
		}
		System.out.println(boxOfOranges1.getWeight());

		Box<Apple> boxOfApples1 = new Box<>();
		for (int i = 0; i < 15; i++) {
			boxOfApples1.addFruit(new Apple());
		}
		System.out.println(boxOfApples1.getWeight());
		System.out.println(boxOfOranges1.compare(boxOfApples1));

		Box<Orange> boxOfOranges2 = new Box<>();
		boxOfOranges1.moveFruits(boxOfOranges2);
		System.out.println(boxOfOranges1.getWeight());
		System.out.println(boxOfOranges2.getWeight());

		//Box<Apple> boxOfApples2 = new Box<>();
		//boxOfOranges2.moveFruits(boxOfApples2); //проверка невозможности работы с коробками разных типов
	}
}
