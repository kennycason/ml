package kenny.ml.collections.trie;

import kenny.ml.trie.StringTrie;
import kenny.ml.trie.Trie;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestTrie {

	@Test
	public void testStringTrie() {
		
		StringTrie trie = new StringTrie();
		assertEquals(0, trie.size());
		trie.add("HELLO");
		assertEquals(1, trie.size());
		trie.add("HELLO");					// duplicate words do not count
		assertEquals(1, trie.size());
		trie.add("FROG");
		assertEquals(2, trie.size());
		
		assertTrue(trie.contains("HELLO"));		// should find it
		assertTrue(trie.contains("FROG"));		// should find it
		assertFalse(trie.contains("HEL"));		// not a full word
		
		// load a file
		trie = new StringTrie();
        final long startTime = System.currentTimeMillis();
        trie.loadFile("classify/nlp/dict/chinese_simple.list");
        System.out.println(System.currentTimeMillis() - startTime + "ms");
		assertTrue(trie.contains("朋友"));
		assertTrue(trie.contains("遗传"));
		assertTrue(trie.contains("洲际弹道导弹"));
	}
	
	
	@Test
	public void testObjectTrie() {	
		Trie<Object> trie = new Trie<Object>(new Object());
		Object o1 = 12;
		Object o2 = "ASFAS";
		Object o3 = new Character('X');
		Object o4 = new HashMap<String, String>();
		
		Object[] oArray1 = new Object[] {o1, o1, o2, o3, o3, o4};
		Object[] oArray2 = new Object[] {o1, o2, o3, o4, o4, o1};
		Object[] oArray3 = new Object[] {o1, o1, o2, o2};
		
		assertEquals(0, trie.size());
		trie.add(oArray1);
		assertEquals(1, trie.size());
		trie.add(oArray1);					// duplicate words do not count
		assertEquals(1, trie.size());
		trie.add(oArray2);
		assertEquals(2, trie.size());
		
		assertTrue(trie.contains(oArray1));		// should find it
		assertTrue(trie.contains(oArray2));		// should find it
		assertFalse(trie.contains(oArray3));		// not a full word
	}

}
