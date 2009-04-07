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
import javax.swing.AbstractAction;

public class KeyAction extends AbstractAction 
{
	MainController controller;
	
	public KeyAction(MainController controller)
	{
		this.controller = controller;
	}
	
	public void actionPerformed(ActionEvent event)
	{

	}
}
