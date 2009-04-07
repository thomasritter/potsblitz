/*
* Copyright (c) 2004, Tobias Meier, Nicolas Hantzsch and Thomas Ritter
* All rights reserved.
*
* This software is open-source under the BSD license; see 
* "license.txt" for a description
*/
package gui.views.swing;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.filechooser.FileFilter;


/**
 * Used for the <code>JFileChooser</code> so that only "your fileextension"-files
 * are being displayed.
 * 
 * @author Thomas Ritter
 */
public class UniversalFileFilter extends FileFilter
{
    private Vector<String> fileExtensions;
	
	/**
	 * Needs the extension which it should filter
	 * @param fileExtension String
	 */
	public UniversalFileFilter(String fileExtension)
	{
	    this.fileExtensions = new Vector<String>();
		this.fileExtensions.add(fileExtension);
	}
	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	public boolean accept(File f)
	{
		//accept directories
		if (f.isDirectory())
			return true;

		//accept files
		for(Iterator it = this.fileExtensions.iterator(); it.hasNext();)
		{
		    String fileExtension = (String)it.next();
		    
			if (f.isFile() && f.getAbsolutePath().indexOf("."+fileExtension) != -1)
			{
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	* @see javax.swing.filechooser.FileFilter#getDescription()
	*/
	public String getDescription()
	{
	    String temp = "";
	    
	    for(Iterator it = this.fileExtensions.iterator(); it.hasNext();)
	    {
	        temp = temp + " *." + (String)it.next();
	    }
		return "File (" + temp + ")";
	}
	
	/**
	 * If more than extension should be allowed use this method
	 * to add more to the filter
	 * @param fileExtension
	 */
	public void addFilter(String fileExtension)
	{
	    this.fileExtensions.add(fileExtension);
	}
}
