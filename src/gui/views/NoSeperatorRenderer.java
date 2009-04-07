/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package gui.views;

public class NoSeperatorRenderer implements IWordRenderer {

	public String renderWord(String word) 
	{
		return word.replaceAll("-", "");
	}
}
