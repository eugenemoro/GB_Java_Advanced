import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
	public static final int CARS_COUNT = 4;
	public static AtomicBoolean finishCrossed = new AtomicBoolean(false);
	private static final Semaphore smr = new Semaphore(CARS_COUNT / 2);
	private static final CyclicBarrier cbStart = new CyclicBarrier(CARS_COUNT + 1);
	private static final CyclicBarrier cbFinish = new CyclicBarrier(CARS_COUNT + 1);
	private static ReentrantLock lock = new ReentrantLock();

	public static void main(String[] args) {
		System.out.println("ATTENTION! >>> GETTING READY!!!");
		Race race = new Race(new Road(60), new Tunnel(), new Road(40));
		Car[] cars = new Car[CARS_COUNT];
		for (int i = 0; i < cars.length; i++) {
			cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
		}
		for (int i = 0; i < cars.length; i++) {
			new Thread(cars[i]).start();
		}
		try {
			cbStart.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println("ATTENTION! >>> RACE STARTED!!!");
		try {
			cbFinish.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println("ATTENTION! >>> RACE FINISHED!!!");
	}

	public static Semaphore getSmr() {
		return smr;
	}

	public static CyclicBarrier getCbStart() {
		return cbStart;
	}

	public static CyclicBarrier getCbFinish() {
		return cbFinish;
	}
}

class Car implements Runnable {
	private static int CARS_COUNT;
	static {
		CARS_COUNT = 0;
	}
	private Race race;
	private int speed;
	private String name;
	public String getName() {
		return name;
	}
	public int getSpeed() {
		return speed;
	}
	public Car(Race race, int speed) {
		this.race = race;
		this.speed = speed;
		CARS_COUNT++;
		this.name = "Car #" + CARS_COUNT;
	}
	@Override
	public void run() {
		try {
			System.out.println(this.name + " is preparing");
			Thread.sleep(500 + (int)(Math.random() * 800));
			System.out.println(this.name + " is ready");
			Main.getCbStart().await();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < race.getStages().size(); i++) {
			race.getStages().get(i).go(this);
		}

		if (!Main.finishCrossed.get()) {
			System.out.println(this.name + " WON!");
			Main.finishCrossed.set(true);
		}
		try {
			Main.getCbFinish().await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
}

abstract class Stage {
	protected int length;
	protected String description;
	public String getDescription() {
		return description;
	}
	public abstract void go(Car c);
}

class Road extends Stage {
	public Road(int length) {
		this.length = length;
		this.description = "Road " + length + " meters";
	}
	@Override
	public void go(Car c) {
		try {
			System.out.println(c.getName() + " started the stage: " + description);
			Thread.sleep(length / c.getSpeed() * 1000);
			System.out.println(c.getName() + " finished the stage: " + description);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Tunnel extends Stage {
	public Tunnel() {
		this.length = 80;
		this.description = "Tunnel " + length + " meters";
	}
	@Override
	public void go(Car c) {
		try {
			try {
				System.out.println(c.getName() + " is preparing for the stage(waiting): " + description);
				Main.getSmr().acquire();
				System.out.println(c.getName() + " started the stage: " + description);
				Thread.sleep(length / c.getSpeed() * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println(c.getName() + " finished the stage: " + description);
			}
			Main.getSmr().release();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class Race {
	private ArrayList<Stage> stages;
	public ArrayList<Stage> getStages() { return stages; }
	public Race(Stage... stages) {
		this.stages = new ArrayList<>(Arrays.asList(stages));
	}
}