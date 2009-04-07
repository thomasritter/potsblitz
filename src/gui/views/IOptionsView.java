/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package gui.views;

public interface IOptionsView 
{
	public abstract void showView();
	public abstract void hideView();
	public abstract void setPreviewBackgroundColor(int color);
	public abstract void setPreviewTextColor(int color);
	public abstract void setFont(String font);
	public abstract void setFontsize(int fontSize);
	public abstract void setDelay(int delay);
	public abstract int getDelay();
	public abstract int getFontSize();
	public abstract String getTextFont();
	public abstract void setPreviewText(String word);
	public abstract void setSelectedFontsize(int fontsize);
	public abstract void setVowelColor(int vowelColor);
}