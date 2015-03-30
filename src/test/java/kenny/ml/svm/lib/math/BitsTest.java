package kenny.ml.svm.lib.math;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BitsTest {

	@Test
	public void test() {
		
		Bits b = new Bits(16, 10);
		assertEquals(16, b.getBits());
		b = new Bits(32, 10);
		assertEquals(32, b.getBits());
		b = new Bits(64, 10);
		assertEquals(64, b.getBits());
		
		b = new Bits(16, 1);
		assertTrue(b.isBit(0));
		for(int i = 1; i < 16; i++) {
			assertFalse(b.isBit(i));
		}
		
		b = new Bits(32, 0xFFFFFFFF);
		double[] arr = b.toDoubleArray();
		for(int i = 0; i < arr.length; i++) {
			assertEquals(1, arr[i], 0.0005);
		}

		b = new Bits(new double[]{1, 1, 1, 1, 
								 0, 0, 0, 0,
								 0, 0, 0, 0, 
								 0, 0, 0, 0});
		assertEquals(15, b.value(), 0.0005);
		
		b = new Bits(new double[]{1, 0, 0, 0, 
								 0, 0, 0, 0,
								 0, 0, 0, 0, 
								 0, 0, 1, 0});
		assertEquals(Math.pow(2, 14) + 1, b.value(), 0.0005);		
		
		
	}

}
