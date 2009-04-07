/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package gui.views;

import models.WordlistModel;

public interface IWordlistEditorView 
{
	public abstract void showView();
	public abstract void setWordlist(WordlistModel wordlist);
}