/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
import java.awt.Color;

import gui.views.IWordRenderer;
import gui.views.NoSeperatorRenderer;
import gui.views.VowelsRenderer;
import junit.framework.TestCase;

public class RendererTest extends TestCase {

	/*
	 * Test method for 'gui.views.NoSeperatorRenderer.renderWord(String)'
	 */
	public void testNoSeperatorRenderWord() 
	{
		IWordRenderer renderer = new NoSeperatorRenderer();
		assertEquals("Einstein", renderer.renderWord("Ein-stein"));
	}
	
	public void testVowelsRenderer()
	{
		VowelsRenderer renderer = new VowelsRenderer(new Color(-1));
		renderer.setColor(new Color(-1));
		assertEquals("<span style=\"color:ffffff\">E</span><span style=\"color:ffffff\">i</span>n-st<span style=\"color:ffffff\">e</span><span style=\"color:ffffff\">i</span>n", renderer.renderWord("Ein-stein"));
	}
}
