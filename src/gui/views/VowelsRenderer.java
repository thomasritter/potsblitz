/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package gui.views;

import java.awt.Color;

public class VowelsRenderer implements IWordRenderer 
{
	private Color color;
	
	public VowelsRenderer(Color color)
	{
		this.color = color;
	}
	
	public String renderWord(String word)
	{
		word = this.maskVowels(word);
		
		word = word.replaceAll("&a&", this.hightlightVowel("&a&"));
		word = word.replaceAll("&e&", this.hightlightVowel("&e&"));
		word = word.replaceAll("&i&", this.hightlightVowel("&i&"));
		word = word.replaceAll("&o&", this.hightlightVowel("&o&"));
		word = word.replaceAll("&u&", this.hightlightVowel("&u&"));
		word = word.replaceAll("&ä&", this.hightlightVowel("&ä&"));
		word = word.replaceAll("&ö&", this.hightlightVowel("&ö&"));
		word = word.replaceAll("&ü&", this.hightlightVowel("&ü&"));
		
		word = word.replaceAll("&A&", this.hightlightVowel("&A&"));
		word = word.replaceAll("&E&", this.hightlightVowel("&E&"));
		word = word.replaceAll("&I&", this.hightlightVowel("&I&"));
		word = word.replaceAll("&O&", this.hightlightVowel("&O&"));
		word = word.replaceAll("&U&", this.hightlightVowel("&U&"));
		word = word.replaceAll("&Ä&", this.hightlightVowel("&Ä&"));
		word = word.replaceAll("&Ö&", this.hightlightVowel("&Ö&"));
		word = word.replaceAll("&Ü&", this.hightlightVowel("&Ü&"));
		
		return word;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	private String maskVowels(String word)
	{
		word = word.replace("a", "&a&");
		word = word.replace("e", "&e&");
		word = word.replace("i", "&i&");
		word = word.replace("o", "&o&");
		word = word.replace("u", "&u&");
		word = word.replace("ä", "&ä&");
		word = word.replace("ö", "&ö&");
		word = word.replace("ü", "&ü&");
		
		word = word.replace("A", "&A&");
		word = word.replace("E", "&E&");
		word = word.replace("I", "&I&");
		word = word.replace("O", "&O&");
		word = word.replace("U", "&U&");
		word = word.replace("Ä", "&Ä&");
		word = word.replace("Ü", "&Ü&");
		word = word.replace("Ö", "&Ö&");
		
		return word;
	}
	
	private String hightlightVowel(String vowel)
	{
		vowel = vowel.replaceAll("&", "");
		return "<span style=\"color:"+ Integer.toHexString(color.getRGB() & 0x00ffffff) +"\">"+vowel+"</span>";
	}
}
