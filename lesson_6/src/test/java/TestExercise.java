import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestExercise {
	Exercise exercise;

	@Before
	public void init(){
		exercise = new Exercise();
	}

	@Test(expected = RuntimeException.class)
	public void whatsAfterFourExceptionTest(){
		exercise.whatsAfterFour(new int[]{1, 2, 3});
	}

	@Test
	public void whatsAfterFourRealWorkTest1(){
		Assert.assertArrayEquals(new int[]{5,6}, exercise.whatsAfterFour(new int[]{1,2,3,4,5,6}));
	}

	@Test
	public void whatsAfterFourRealWorkTest2(){
		Assert.assertArrayEquals(exercise.whatsAfterFour(new int[]{1,4,5,6,4,7}), new int[]{7});
	}

	@Test
	public void whatsAfterFourNullTest1(){
		Assert.assertArrayEquals(exercise.whatsAfterFour(new int[]{1,5,6,4}), new int[]{});
	}

	@Test
	public void whatsAfterFourNullTest2(){
		Assert.assertArrayEquals(exercise.whatsAfterFour(new int[]{4}), new int[]{});
	}

	@Test
	public void containsOnesAndFoursTest1(){
		Assert.assertTrue(exercise.containsOnesAndFours(new int[]{1,4,1}));
	}

	@Test
	public void containsOnesAndFoursTest2(){
		Assert.assertFalse(exercise.containsOnesAndFours(new int[]{1}));
	}

	@Test
	public void containsOnesAndFoursTest3(){
		Assert.assertFalse(exercise.containsOnesAndFours(new int[]{4}));
	}

	@Test
	public void containsOnesAndFoursTest4(){
		Assert.assertFalse(exercise.containsOnesAndFours(new int[]{1,4,5,6}));
	}

	@Test
	public void containsOnesAndFoursTest5(){
		Assert.assertFalse(exercise.containsOnesAndFours(new int[]{5,6}));
	}

	@Test
	public void containsOnesAndFoursTest6(){
		Assert.assertFalse(exercise.containsOnesAndFours(new int[]{}));
	}
}
