/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package gui.controller;
import models.WordlistModel;
import gui.views.swing.SplashScreen;

public class Client
{
	public static SplashScreen splashScreen;

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		splashScreen = new SplashScreen("Bilder/splash.jpg");
		MainController controller = new MainController();
		WordlistModel list = new WordlistModel();
		controller.setModel(list);
		controller.createView();
	}

}
