public class TestMe {

	@Test(priority = 0)
	public static void method1() {
		System.out.println("m1");
	}

	@Test(priority = 1)
	public static void method2() {
		System.out.println("m2");
	}

	@Test(priority = 10)
	public static void method3() {
		System.out.println("m3");
	}

	@Test(priority = 2)
	public static void method4() {
		System.out.println("m4");
	}

	@Test(priority = 2)
	public static void method5() {
		System.out.println("m5");
	}

	@Test
	public static void method6() {
		System.out.println("m6");
	}

	@Test(priority = 3)
	public static void method7() {
		System.out.println("m7");
	}

	@Test(priority = 4)
	public static void method8() {
		System.out.println("m8");
	}

	//@AfterSuite
	//@BeforeSuite
	@Test
	public static void method9() {
		System.out.println("m9");
	}

	@Test(priority = 6)
	public static void method10() {
		System.out.println("m10");
	}

	@AfterSuite
	public static void method11() {
		System.out.println("m11");
	}

	@BeforeSuite
	public static void method12() {
		System.out.println("m12");
	}
}
