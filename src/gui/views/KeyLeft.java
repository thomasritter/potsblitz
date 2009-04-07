/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package gui.views;

import gui.controller.MainController;

import java.awt.event.ActionEvent;

public class KeyLeft extends KeyAction 
{
	public KeyLeft(MainController controller)
	{
		super(controller);
	}
	
	public void actionPerformed(ActionEvent event)
	{
		this.controller.showPreviousWord();
	}
}
