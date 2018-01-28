package priv.shen.TextCheck;

import org.junit.Test;

public class TextCheckTest {
	@Test
	public void testCheck() {
		String text="we have a apple";
		System.out.println("Text before check:"+text);
		
		String newText=TextCheck.check(text);
		
		System.out.println("Text after check:"+newText);
	}
}
