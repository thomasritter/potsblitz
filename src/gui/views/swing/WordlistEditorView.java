/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package gui.views.swing;

import gui.controller.WordlistEditorController;
import gui.views.IWordlistEditorView;
import gui.views.IMainView;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import models.WordlistModel;

public class WordlistEditorView extends JDialog implements ActionListener, IWordlistEditorView
{
	private JList wordList;
	private WordlistEditorController controller;
	
	public WordlistEditorView(IMainView owner, WordlistEditorController controller)
	{
		super((JFrame)owner, "Wörterliste bearbeiten", true);
		this.controller = controller;
		
		this.getContentPane().setLayout(new BorderLayout());
		
		this.wordList = new JList();
		JPanel wordlistPanel = new JPanel();
		wordlistPanel.setBorder(BorderFactory.createEtchedBorder());
		wordlistPanel.setLayout(new BorderLayout());
		wordlistPanel.add(new JScrollPane(this.wordList), BorderLayout.CENTER);
		this.getContentPane().add(wordlistPanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEtchedBorder());
		JButton addButton = new JButton("Wort hinzufügen");
		addButton.setName("addWord");
		addButton.addActionListener(this);
		JButton updateButton = new JButton("Wort verändern");
		updateButton.setName("updateWord");
		updateButton.addActionListener(this);
		JButton removeButton = new JButton("Wort entfernen");
		removeButton.setName("removeWord");
		removeButton.addActionListener(this);
        JButton cancelButton = new JButton("Schließen");
        cancelButton.setName("cancel");
        cancelButton.addActionListener(this);
		buttonPanel.add(addButton);
		buttonPanel.add(updateButton);
		buttonPanel.add(removeButton);
        buttonPanel.add(cancelButton);
		
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		this.pack();
		this.setSize(500, 400);
		this.setLocationRelativeTo((JFrame)owner);
	}
	
	/* (non-Javadoc)
	 * @see gui.views.swing.IGuiViewEditWordlist#showView()
	 */
	public void showView()
	{
		this.setVisible(true);		
	}
	
	public void actionPerformed(ActionEvent event)
	{
		int selectedIndex = this.wordList.getSelectedIndex();
		
		if(((JButton)event.getSource()).getName() == "addWord")
		{
	        String newWord = "";
	        JOptionPane DateiName = new JOptionPane();
	        newWord = (String) DateiName.showInputDialog(this,
	                "Neues Wort eingeben", "Eingabe",
	                JOptionPane.QUESTION_MESSAGE);
	        this.controller.addWord(newWord);
		}
		if(((JButton)event.getSource()).getName() == "removeWord")
		{
			if(selectedIndex != -1)
	        {
	            int result = JOptionPane.showConfirmDialog(this, "Wort wirklich entfernen?"
	                    ,"Frage",
	                    JOptionPane.OK_CANCEL_OPTION);
	            if (result == JOptionPane.YES_OPTION)
	            {
	            	this.controller.deleteWord(this.wordList.getSelectedIndex());
	            }
	        }
		}
		if(((JButton)event.getSource()).getName() == "updateWord")
		{
			if(selectedIndex != -1)
	        {
				String updateWord = "";
		        JOptionPane word = new JOptionPane();
		        updateWord = (String) word.showInputDialog(this,
		                "Wort eingeben", this.wordList.getSelectedValue().toString());
				if(updateWord != null)
				{
			        this.controller.updateWord(selectedIndex, updateWord);
				}
	        }
		}
        if(((JButton)event.getSource()).getName() == "cancel")
        {
            this.setVisible(false);
        }
	}

	public void setWordlist(WordlistModel wordlist) 
	{
		this.wordList.setListData(wordlist.getListAsVector());
	}
}
