/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package configuration;

/**
 *
 * Represents a single option. It always consists of a key and 
 * a value.
 *  
 * @author Thomas Ritter
 */
public class ConfigurationEntry
{
	private String key=""; 
	private String value="";
	
	/**
	 * Default constructor
	 * @param key String
	 * @param value String
	 */
	public ConfigurationEntry(String key, String value)
	{
		this.key = key;
		this.value = value;
	}

	/**
	 * Returns the key
	 * @return String
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * Returns the entry value
	 * @return String
	 */
	public String getValue()
	{
		return this.value;
	}
	
	public int getValueAsInt()
	{
		return Integer.parseInt(this.value);
	}
	
	public boolean getValueAsBoolean()
	{
		return Boolean.parseBoolean(this.value);
	}

	/**
	 * Sets a new key
	 * @param string String
	 */
	public void setKey(String string)
	{
		this.key = string;
	}

	/**
	 * Sets a new Value
	 * @param string String
	 */
	public void setValue(String string)
	{
		this.value = string;
	}

}
