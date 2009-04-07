/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package gui.views.swing;

import java.awt.*;

/**
 * Class is used to display a splashscreen at the screen<br>
 * only awt classes are used. It loads faster than swing
 * implementations
 * 
 * @author Thomas Ritter
 */
public class SplashScreen extends Window
{
	private Image image;
	String fileName;
	
	/**
	 * Constructor needs the name of the image as a string
	 * @param fileName String
	 */
	public SplashScreen(String fileName)
	{
		super(new Frame());
		this.fileName = fileName;
        this.image = Toolkit.getDefaultToolkit().getImage(this.fileName);
		this.setVisible();
	}
	
	/**
	 * Had to be overwritten from class window
	 */
	public void paint(Graphics g)
	{
		g.drawImage(this.image, 0, 0, this);
	}
	
	/**
	 * Splashscreen needs to be in the middle of the screen
	 */
	public boolean imageUpdate(
		Image img,
		int infoflags,
		int x,
		int y,
		int width,
		int height)
	{
		if ((infoflags & WIDTH + HEIGHT) != 0)
		{
			int w = Math.abs(this.image.getWidth(null));
			int h = Math.abs(this.image.getHeight(null));
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			setBounds((d.width - w) / 2, (d.height - h) / 2, w, h);
		}
		return super.imageUpdate(img, infoflags, x, y, width, height);
	}
	
	/**
	 * Show the splashscreen
	 */
	public void setVisible()
	{
		this.image.getWidth(this); 
		this.setVisible(true);
	}
	
	/**
	 * Kill the splashscreen
	 */
	public void dispose()
	{
		this.image = null;
		super.dispose();
	}

}
