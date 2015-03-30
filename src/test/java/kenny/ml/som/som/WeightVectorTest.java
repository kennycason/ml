package kenny.ml.som.som;


import kenny.ml.som.features.WeightVector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WeightVectorTest {

	private static final double DELTA = 0.01;

	
	@Test
	public void Test1D() {
		WeightVector vector = new WeightVector(0.5);
		
		assertEquals(0.5, vector.get(0), DELTA);
		
		WeightVector temp = vector.add(2.0);
		
		assertEquals(2.5, temp.get(0), DELTA);
		
	}
	
	@Test
	public void Test2D() {
		WeightVector vector = new WeightVector(0.5, 0.6);
		
		assertEquals(0.5, vector.get(0), DELTA);
		assertEquals(0.6, vector.get(1), DELTA);
		
		WeightVector temp = vector.add(2.0);
		
		assertEquals(2.5, temp.get(0), DELTA);
		assertEquals(2.6, temp.get(1), DELTA);
		
	}

	@Test
	public void Test3D() {
		WeightVector vector = new WeightVector(0.5, 0.6, 0.7);
		
		assertEquals(0.5, vector.get(0), DELTA);
		assertEquals(0.6, vector.get(1), DELTA);
		assertEquals(0.7, vector.get(2), DELTA);
		
		WeightVector temp = vector.add(2.0);
		
		assertEquals(2.5, temp.get(0), DELTA);
		assertEquals(2.6, temp.get(1), DELTA);
		assertEquals(2.7, temp.get(2), DELTA);
		
	}
	
}
