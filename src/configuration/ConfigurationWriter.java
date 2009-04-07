/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package configuration;

import java.io.*;
import java.util.Iterator;

/**
 * Writes the <code>Configuration</code> object as a xml-file.
 * 
 * @author Thomas Ritter
 */
public class ConfigurationWriter
{
	private String contents = "";
	private String configfileName="";
	private Configuration options;


	/**
	 * Default constructor
	 * @param configfileName String
	 * @param options Configuration
	 */
	public ConfigurationWriter(String configfileName, Configuration options)
	{
		this.configfileName = configfileName;
		this.options = options;
	}

	/**
	 * Writes the Configuration Object to a XML File
	 * @return boolean
	 */
	public boolean writeConfigToXML()
	{
		//Write XMLHeader
		contents += "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + "\r\n";
		contents += "<configuration>" + "\r\n";
		//Fill with current Options attributes
		//Loop over Vector OptionsEntryList
		Iterator it = this.options.getOptionsEntryList().iterator();
			

		while (it.hasNext())
		{
			ConfigurationEntry TempEntry = (ConfigurationEntry)it.next();

			contents += "<entry " + "name=\""
				+ TempEntry.getKey()
				+ "\""
				+ " value=\""
				+ TempEntry.getValue()
				+ "\"/>" + "\r\n";
		}
		//Close Options Tag
		contents += "</configuration>";

		this.writeFile(contents, this.configfileName);
		return true;
	}

	/**
	 * creates a new xml-file and fill it with a string
	 * @param content String
	 * @param location String
	 */
	private boolean writeFile(String content, String location)
	{
		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(location));
			out.write(content);
			out.newLine();
			out.close();
			return true;
		}
		catch (Exception e)
		{
		    e.printStackTrace();
			return false;
		}
	}

}
