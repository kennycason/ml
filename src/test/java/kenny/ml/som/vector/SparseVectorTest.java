package kenny.ml.som.vector;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SparseVectorTest {
	
	private static double DELTA = 0.005;

	@Test
	public void test() {
		SparseVector v = new SparseVector();
		assertEquals(0,  v.size());
		assertEquals(0, v.get(0), DELTA);
		assertEquals(0, v.get(1), DELTA);
		assertEquals(0, v.get(2), DELTA);
		assertEquals(0, v.get(3), DELTA);
		assertEquals(0, v.get(4), DELTA);
		
		v.set(3, 2);
		assertEquals(4, v.size());
		assertEquals(2, v.get(3), DELTA);
		
		v.set(3, 2);
		assertEquals(4, v.size());
		assertEquals(2, v.get(3), DELTA);
		
		v.set(0, 3);
		assertEquals(4, v.size());
		assertEquals(3, v.get(0), DELTA);
		assertEquals(2, v.get(3), DELTA);	
		
		v.set(1, 4);
		v.set(2, 8);
		v.set(4, 1);
		assertEquals(4, v.get(1), DELTA);
		assertEquals(8, v.get(2), DELTA);
		assertEquals(1, v.get(4), DELTA);
		

		v = new SparseVector();
		v.set(0, 3);
		v.set(2, 2);

		VectorND v2 = new VectorND(new double[]{3, 0, 2, 0, 0});
		assertEquals(v2.magnitude(), v.magnitude(), DELTA);
		assertEquals(0, v2.distance(v), DELTA);
		
		
		v2.setAll(new double[]{2, 0, 2, 0, 2});
		v.set(0, 2);
		v.set(2, 2);
		v.set(4, 2);
		System.out.println(v2);
		System.out.println(v);
		
		
	}

}
