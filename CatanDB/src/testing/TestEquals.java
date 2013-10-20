package testing;

import java.util.Arrays;

public class TestEquals {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[] array1 = new Integer[2];
		Integer[] array2 = new Integer[2];
		Integer a = new Integer(2);
		Integer b = new Integer(2);
		array1[0] = a;
		array2[0] = b;
		System.out.println(Arrays.equals(array1,array2));
		System.out.println(a.equals(b));
	}

}
