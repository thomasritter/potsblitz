/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package models;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

public class WordlistModel
{
	private Vector<String> wordlist;
	private int pointer = 0;
	
	public WordlistModel()
	{
		wordlist = new Vector<String>();
		pointer = 0;
	}
	
	public void addWord(String word)
	{
		this.wordlist.add(word);
	}
	
	public String getCurrentWord()
	{
		if(this.wordlist.size() > 0)
		{
			return (String)this.wordlist.get(pointer);
		}
		else
		{
			return null;
		}
	}
	
	public String getNextWord()
	{
		if(wordlist.size()-1 > pointer)
		{
			pointer++;
			return (String)wordlist.get(pointer);
		}
		return "";
	}
	
	public String getNextRandomWord()
	{
		if(wordlist.size() != 0)
		{
			this.pointer = (int)Math.floor(Math.random()*wordlist.size());
			return (String)wordlist.get(this.pointer);
		}
		else
		{
			return null;
		}
	}
	
	public String getPreviousWord()
	{
		if(pointer != 0)
		{
			pointer--;
			return (String)wordlist.get(pointer);
		}
		return "";
	}
	
	public Vector getListAsVector()
	{
		return this.wordlist;
	}
	
	public void setList(Vector<String> wordlist)
	{
		this.wordlist = wordlist;
	}
	
	public void deleteWord(int position)
	{
		if(position >= 0 && position < this.wordlist.size())
		{
			this.wordlist.remove(position);
		}
	}
	
	public void updateWord(int position, String newVersion)
	{
		if(position >= 0 && position < this.wordlist.size())
		{
			this.wordlist.set(position, newVersion);		
		}
	}
	
	public static WordlistModel readFromFile(File file)
	{
		WordlistModel model = new WordlistModel();
		String temp;
		Vector<String> list = new Vector<String>();
		
		try
		{
			BufferedReader filereader = new BufferedReader(new FileReader(file));
			
			while((temp = filereader.readLine()) != null)
			{
			    if(temp.length() > 0)
			    {
			        list.add(temp);
			    }
			}
			filereader.close();
			
			model.setList(list);
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return model;
	}
	
	public static boolean saveToFile(File file, WordlistModel model)
	{
		  try
		  {
			  BufferedWriter out = new BufferedWriter(new FileWriter(file));
			  for(Iterator it = model.getListAsVector().iterator(); it.hasNext();)
			  {
				  out.write(it.next().toString());
				  out.newLine();
			  }
			  out.close();
			  return true;
		  }
		  catch(Exception e)
		  {
		  	  e.printStackTrace();
		  	  return false;
		  }
	}
}
