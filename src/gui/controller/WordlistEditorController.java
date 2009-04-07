/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package gui.controller;

import models.WordlistModel;
import gui.views.IMainView;
import gui.views.swing.WordlistEditorView;

public class WordlistEditorController 
{
	private WordlistEditorView editorView;
	private WordlistModel listModel;
	
	public WordlistEditorController(WordlistModel listModel, IMainView mainView)
	{
		//TODO Constructor Swing specific
		this.listModel = listModel;
		editorView = new WordlistEditorView(mainView, this);
		editorView.setWordlist(this.listModel);
		editorView.showView();
	}
	
	public void deleteWord(int position)
	{
		this.listModel.deleteWord(position);
		this.editorView.setWordlist(this.listModel);
	}
	
	public void addWord(String word)
	{
		if(word != null)
		{
			if(word.length() > 0)
			{
				this.listModel.addWord(word);
				this.editorView.setWordlist(this.listModel);					
			}
		}
	}
	
	public void updateWord(int position, String newVersion)
	{
		this.listModel.updateWord(position, newVersion);
		this.editorView.setWordlist(this.listModel);
	}

}
