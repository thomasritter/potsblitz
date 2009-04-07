/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package configuration;

import java.io.*;
import javax.xml.parsers.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

/**
 * Generates a <code>Configuration</code> object from a xml-file and fills it with <code>ConfigurationEntry</code>
 * objects. Uses SAX for parsing.
 * 
 * @author Thomas Ritter
 */
public class ConfigurationParser extends DefaultHandler
{
	private File configFile;

	/**
	 * Default constructor
	 * @param configFile File
	 */
	public ConfigurationParser(File configFile)
	{
		this.configFile = configFile;
	}

	public boolean parseConfig() throws Exception
	{
		if (configFile.canRead())
		{
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();
				saxParser.parse(configFile, this);
				return true;
		}
		else
		{
			throw new Exception("config-file not found!");
		}
	}

	/**
	 * Will be invoked when a element has been found
	 * @param uri String
	 * @param localName String
	 * @param qName String
	 * @param attributes Attributes
	 * @throws SAXException
	 */
	public void startElement(
		String uri,
		String localName,
		String qName,
		Attributes attributes)
		throws SAXException
	{
		//general Tag
		if (qName.equalsIgnoreCase("entry"))
		{
			if (attributes.getValue(attributes.getIndex("name")).length() > 0)
			{
				try
				{
					Configuration.getConfigInstance().addConfigEntry(new ConfigurationEntry(
						attributes.getValue(attributes.getIndex("name")),
						attributes.getValue(attributes.getIndex("value"))));
				}
				catch (Exception e)
				{
					throw new SAXException(e.toString());
				}
			}
		}

	}
}
