/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package gui.views.swing;

import java.awt.Color;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JEditorPane;

import gui.views.IWordView;
import gui.views.IWordRenderer;

public class WordView  extends JEditorPane implements IWordView
{
	private int fontSize;
	private String font;
	private String textColor;
	private Vector<IWordRenderer> rendererList;
	
	public WordView()
	{
		this.setEditable(false);
		this.setContentType("text/html");
		this.textColor = "0";
		this.rendererList = new Vector<IWordRenderer>();
	}
	
	/* (non-Javadoc)
	 * @see gui.views.swing.IWordView#setText(java.lang.String)
	 */
	public void setWord(String word)
	{
		this.setWord(word, this.getHeight());
	}
	
	public void setWord(String word, int height)
	{
		word = this.renderWord(word);
		word = "<html><head></head><body><table width=\"100%\"><tr><td style=\"height:" + (height - (height / 4)) + "px\" align=\"center\" valign=\"middle\">"
		+ "<span style=\"color:" + textColor + ";font-family:" + this.font + ";font-size:" + this.fontSize + "\">" + word + "</span>" + "</td></tr></table></body></html>";
		this.setText(word);
	}
	
	private String renderWord(String word)
	{
		for(Iterator it = this.rendererList.iterator(); it.hasNext();)
		{
			IWordRenderer renderer = (IWordRenderer)it.next();
			word = renderer.renderWord(word);
		}
		return word;
	}
	
	/* (non-Javadoc)
	 * @see gui.views.swing.IWordView#setFont(java.lang.String)
	 */
	public void setFont(String font)
	{
		this.font = font;
	}
	
	/* (non-Javadoc)
	 * @see gui.views.swing.IWordView#setFontSize(int)
	 */
	public void setFontSize(int fontSize)
	{
		this.fontSize = fontSize;
	}
	
	/* (non-Javadoc)
	 * @see gui.views.swing.IWordView#setBackgroundColor(java.lang.String)
	 */
	public void setBackgroundColor(Color color)
	{
		this.setBackground(color);
	}
	
	/* (non-Javadoc)
	 * @see gui.views.swing.IWordView#setTextColor(java.lang.String)
	 */
	public void setTextColor(Color color)
	{
		this.textColor = Integer.toHexString(color.getRGB() & 0x00ffffff);
	}

	public void addRenderer(IWordRenderer renderer) 
	{
		this.rendererList.add(renderer);
	}

	public void removeRenderer(IWordRenderer renderer)
	{
		this.rendererList.remove(renderer);
	}
}
