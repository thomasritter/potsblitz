/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package gui.views;

import java.awt.event.ActionEvent;

import gui.controller.MainController;

public class KeyEnter extends KeyAction 
{
	public KeyEnter(MainController controller)
	{
		super(controller);
	}
	
	public void actionPerformed(ActionEvent event)
	{
		this.controller.showWordinTextarea();
	}
}
