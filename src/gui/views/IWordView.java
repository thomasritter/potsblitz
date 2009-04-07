/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package gui.views;

import java.awt.Color;

public interface IWordView
{
	public void setWord(String word);
	
	public void setWord(String word, int height);

	public void setFont(String font);

	public void setFontSize(int fontSize);

	public void setBackgroundColor(Color color);

	public void setTextColor(Color color);
	
	public void addRenderer(IWordRenderer renderer);
	
	public void removeRenderer(IWordRenderer renderer);
}