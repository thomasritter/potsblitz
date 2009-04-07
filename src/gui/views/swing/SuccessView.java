package gui.views.swing;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;

import gui.views.IMainView;
import gui.views.ISuccessView;

public class SuccessView extends JDialog implements ISuccessView
{
    private Image successImage;
    
    public SuccessView(IMainView owner)
    {
    	super((JFrame)owner, "Fertig!", true);
    }
    
	public void show(IMainView owner, File imageLocation) 
	{
	    successImage = getToolkit().getImage(imageLocation.getAbsolutePath());
	        
	    MediaTracker tracker = new MediaTracker(this);
	    tracker.addImage(successImage, 0);
	        
	    try
	    {
	        tracker.waitForAll();
	    } catch (InterruptedException e)
	    {
	        e.printStackTrace();
	    }
	        
	    this.setResizable(false);
	    this.pack();
	    this.setSize(successImage.getWidth(this)+6, successImage.getHeight(this)+23);
	    this.setLocationRelativeTo((JFrame)owner);
	    this.setVisible(true);		
	}
    
    public void paint(Graphics g)
    {
      super.paint(g);
      if(successImage != null)
      {
        //Paint image
        g.drawImage(successImage, 3, 20, successImage.getWidth(this), successImage.getHeight(this), this);
      }
    }
}
