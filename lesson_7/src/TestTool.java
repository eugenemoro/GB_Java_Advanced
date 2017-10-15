import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestTool {
	public static void start(Object object){
		Class c = null;
		int iBeforeSuite = -1;
		int iAfterSuite = -1;
		try {
			if (object.getClass().equals(String.class)) {
				c = Class.forName((String) object);
			} else {
				c = (Class) object;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Method[] methods = c.getDeclaredMethods();
		//finding @BeforeSuite and @AfterSuite and making sure they're used once per class
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].isAnnotationPresent(BeforeSuite.class)){
				if (iBeforeSuite == -1) iBeforeSuite = i;
				else throw new RuntimeException("There must be one @BeforeSuite annotation");
			}
			if (methods[i].isAnnotationPresent(AfterSuite.class)){
				if (iAfterSuite == -1) iAfterSuite = i;
				else throw new RuntimeException("There must be one @AfterSuite annotation");
			}
		}

		try {
			//run method @BeforeSuite if present
			if (iBeforeSuite != -1) {
				methods[iBeforeSuite].invoke(c);
			}
			//iterating thru priorities and methods invoking the methods of each priority
			for (int i = 0; i < 11; i++) {
				for (Method m : methods) {
					if (m.isAnnotationPresent(Test.class) && m.getAnnotation(Test.class).priority() == i){
						m.invoke(c);
					}
				}
			}
			//run method @AfterSuite if present
			if (iAfterSuite != -1) {
				methods[iAfterSuite].invoke(c);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
