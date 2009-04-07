/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package configuration;

import java.util.*;

import configuration.ConfigurationEntry;

/**
 * 
 * Represents a configlist which can be generated from a xml-file
 * using <code>ConfigurationParser</code> or saved as a xml-file using
 * <code>ConfigurationWriter</code>. <code>Options</code> is implemented
 * as a singleton.
 * 
 * @author Thomas Ritter
 */
public class Configuration
{
	private static Configuration currentOptions;
	
	private Vector<ConfigurationEntry> configEntryList = new Vector<ConfigurationEntry>();

	/**
	 * Singleton so needs a private constructor
	 */
	private Configuration()
	{
	}

	/**
	 * Used to control that there is only one Configuration Instance
	 * @return Options
	 */	
	public static Configuration getConfigInstance()
	{
		if(currentOptions == null)
		{
			currentOptions = new Configuration();
		}
		return currentOptions;
	}
	
	/**
	 * Add a Configurationentry to the Configurationentrylist throws a exception when a
	 * key already exists
	 * @param oe ConfigurationEntry
	 */
	public boolean addConfigEntry(ConfigurationEntry oe)
	{
		//check if key already exists
		for(Iterator it = this.configEntryList.iterator(); it.hasNext();)
		{
			if(((ConfigurationEntry)it.next()).getKey().equalsIgnoreCase(oe.getKey()))
			{
				return false;
			}
		}
		//add to list
		return this.configEntryList.add(oe);
	}
	
	/**
	 * Used to get a specific ConfigurationEntry
	 * @param name String
	 * @return ConfigurationEntry
	 */
	public ConfigurationEntry getConfigEntry(String name)
	{
		Iterator it = this.configEntryList.iterator();
		
		while(it.hasNext())
		{
			ConfigurationEntry Temp = (ConfigurationEntry)it.next();
			
			if(name.equalsIgnoreCase(Temp.getKey()))
			{
				return Temp;
			}	
		}
		return null;
	}
	
	/**
	 * Used to change a ConfigurationEntry Value
	 * @param name String
	 * @param value String
	 */
	public void setConfigEntry(String name, String value)
	{
		for(int i=0; i<this.configEntryList.size(); i++)
		{
			if(name.equalsIgnoreCase(((ConfigurationEntry)this.configEntryList.get(i)).getKey()))
			{
				((ConfigurationEntry)this.configEntryList.get(i)).setValue(value);
			}	
		}
	}

	/**
	 * Returns the Configurationentrylist
	 * @return Vector
	 */
	public Vector getOptionsEntryList()
	{
		return configEntryList;
	}

}
