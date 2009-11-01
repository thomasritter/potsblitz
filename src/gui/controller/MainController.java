/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package gui.controller;

import java.awt.Color;
import java.io.File;

import configuration.Configuration;
import configuration.ConfigurationEntry;
import configuration.ConfigurationParser;
import configuration.ConfigurationWriter;
import models.WordlistModel;
import gui.views.IMainView;
import gui.views.NoSeperatorRenderer;
import gui.views.VowelsRenderer;
import gui.views.swing.MainView;
import gui.views.swing.SuccessView;

public class MainController
{
	private IMainView mainView;
	private WordlistModel listModel;
	private boolean randomPlayMode = false;
	private boolean addAsterisk = false;
	private boolean asteriskIsActive = false;
	private boolean wordCountActive = false;
	private VowelsRenderer vowelRenderer;
	private NoSeperatorRenderer noSeperatorRenderer;
	private int wordCount;
	
	public MainController()
	{
		ConfigurationParser configParser = new ConfigurationParser(new File("config.xml"));
		try
		{
			configParser.parseConfig();
		}
		catch (Exception e)
		{
			//Config not found create default config
			this.createDefaultConfig();
		}
	}
	
	public void createDefaultConfig()
	{
		Configuration.getConfigInstance().addConfigEntry(new ConfigurationEntry("backgroundColor", "-3355393"));
		Configuration.getConfigInstance().addConfigEntry(new ConfigurationEntry("textColor", "-16777216"));
		Configuration.getConfigInstance().addConfigEntry(new ConfigurationEntry("vowelColor", "-3407872"));
		Configuration.getConfigInstance().addConfigEntry(new ConfigurationEntry("fontSize", "160"));
		Configuration.getConfigInstance().addConfigEntry(new ConfigurationEntry("font", "Century Gothic"));
		Configuration.getConfigInstance().addConfigEntry(new ConfigurationEntry("delay", "700"));
		Configuration.getConfigInstance().addConfigEntry(new ConfigurationEntry("asterisk", "#"));
		Configuration.getConfigInstance().addConfigEntry(new ConfigurationEntry("showAsterisk", "false"));
		Configuration.getConfigInstance().addConfigEntry(new ConfigurationEntry("showSeparator", "false"));
		Configuration.getConfigInstance().addConfigEntry(new ConfigurationEntry("highlightVowels", "false"));
		Configuration.getConfigInstance().addConfigEntry(new ConfigurationEntry("showRandom", "false"));
		Configuration.getConfigInstance().addConfigEntry(new ConfigurationEntry("maximumWords", "15"));
		Configuration.getConfigInstance().addConfigEntry(new ConfigurationEntry("countWords", "false"));
	}
	
	public void createView()
	{
    	//TODO Constructor Swing specific
		this.mainView = new MainView(this);
		this.setWordviewBackgroundColor(new Color(Integer.parseInt(Configuration.getConfigInstance().getConfigEntry("backgroundColor").getValue())));
		this.setWordviewTextColor(new Color(Integer.parseInt(Configuration.getConfigInstance().getConfigEntry("textColor").getValue())));
		this.createRenderer();
		this.restoreSettingsFromConfig();
		
		Client.splashScreen.dispose();
		Client.splashScreen = null;
		
		this.mainView.showView();
    }
	
	public void createRenderer()
	{
		this.noSeperatorRenderer = new NoSeperatorRenderer();
		this.vowelRenderer = new VowelsRenderer(new Color(Configuration.getConfigInstance().getConfigEntry("vowelColor").getValueAsInt()));
	}
	
	public void setWordviewBackgroundColor(Color color)
	{
		this.mainView.getWordview().setBackgroundColor(color);
	}
	
	public void setWordviewTextColor(Color color)
	{
		this.mainView.getWordview().setTextColor(color);
	}
	
	public void setWordviewFont(String font)
	{
		this.mainView.getWordview().setFont(font);
	}
	
	private void restoreSettingsFromConfig()
	{
		this.restoreViewSettings();
		this.restorePlayModeSetting();
		this.restoreWordPerSession();
	}
	
	private void restoreViewSettings()
	{
		boolean isEnabled = Configuration.getConfigInstance().getConfigEntry("showAsterisk").getValueAsBoolean();
		
		if(isEnabled == true)
		{
			this.toogleAsterisk();
			this.mainView.toggleAsteriskMenuItem();
		}
		
		isEnabled = Configuration.getConfigInstance().getConfigEntry("highlightVowels").getValueAsBoolean();
		
		if(isEnabled == true)
		{
			this.addVowelRenderer();
			this.mainView.toggleVowelMenuItem();
		}
		
		isEnabled = Configuration.getConfigInstance().getConfigEntry("countWords").getValueAsBoolean();
		
		if(isEnabled == true)
		{
			this.mainView.toggleWordCountActiveMenuItem();
		}
		
		isEnabled = Configuration.getConfigInstance().getConfigEntry("showSeparator").getValueAsBoolean();
		
		if(isEnabled == false)
		{
			this.addNoSeparatorRenderer();
		}
		else
		{
			this.mainView.toggleSeparatorMenuItem();
		}
	}
	
	private void restorePlayModeSetting()
	{
		this.randomPlayMode = Configuration.getConfigInstance().getConfigEntry("showRandom").getValueAsBoolean();
		
		if(this.randomPlayMode == true)
		{
			this.mainView.togglePlayModeMenuItem();
		}
	}
	
	private void restoreWordPerSession()
	{
		this.wordCount = Configuration.getConfigInstance().getConfigEntry("maximumWords").getValueAsInt();
		this.wordCountActive = Configuration.getConfigInstance().getConfigEntry("countWords").getValueAsBoolean();
	}
	
	public void togglePlayMode()
	{
		if(this.randomPlayMode == true)
		{
			this.randomPlayMode = false;
			Configuration.getConfigInstance().getConfigEntry("showRandom").setValue("false");
		}
		else
		{
			this.randomPlayMode = true;
			Configuration.getConfigInstance().getConfigEntry("showRandom").setValue("true");
		}
	}
	
	public void setModel(WordlistModel listModel)
	{
		this.listModel = listModel;
	}
	
	public void showWordinTextarea() 
	{
		int fontsize = Configuration.getConfigInstance().getConfigEntry("fontsize").getValueAsInt();
		int delay = Configuration.getConfigInstance().getConfigEntry("delay").getValueAsInt();
		this.mainView.showWordInTextArea(this.listModel.getCurrentWord(), fontsize, delay);
	}
	
	public void showPreviousWord()
    {
		this.listModel.getPreviousWord();
		showWordinTextarea();
	}

	public void showNextWord()
    {
		if(this.randomPlayMode == false)
		{
			this.listModel.getNextWord();
		}
		else
		{
			this.listModel.getNextRandomWord();
		}
		
		this.showWordinTextarea();
		
		if(this.listModel.getCurrentWord() != null && this.wordCountActive == true)
		{
			this.wordCount--;
		}
	}
    
    public void timerEvent()
    {
        this.mainView.clearTextArea();
        this.mainView.stopTimer();
        
        if(this.addAsterisk == true)
        {
        	this.showAsterisk();
        }
        
        if(this.wordCountActive == true && this.asteriskIsActive == false)
        {
			if(this.wordCount == 0)
			{
				new SuccessView(this.mainView).show(this.mainView, new File("Bilder/finished.jpg"));
				this.resetCounter();
			}
        }
    }
    
    public void showAsterisk()
    {
    	if(this.asteriskIsActive == false)
    	{
    		int numberofAsterisk = this.listModel.getCurrentWord().replaceAll("-", "").length();
    		String asterisk = "";
    		
    		for(int i = 0; i < numberofAsterisk; i++)
    		{
    			asterisk += Configuration.getConfigInstance().getConfigEntry("asterisk").getValue();
    		}
    		
    		this.mainView.showWordInTextArea(asterisk, Configuration.getConfigInstance().getConfigEntry("fontsize").getValueAsInt(), 1000);
    		this.asteriskIsActive = true;
    	}
    	else
    	{
    		this.asteriskIsActive = false;
    	}
    }
    
    private void resetCounter()
    {
    	this.wordCount = Configuration.getConfigInstance().getConfigEntry("maximumWords").getValueAsInt();

    }
    
    public void resetCounterFromView()
    {
    	this.resetCounter();
    	this.mainView.showInfomessage("Zähler zurückgesetzt");
    }
    
    public void showWordListEditor()
    {
    	new WordlistEditorController(this.listModel, this.mainView);
    }
    
    public void loadWordlistFromFile(File file)
    {
    	this.listModel = WordlistModel.readFromFile(file);
    	if (this.listModel != null)
    	{
    		this.mainView.showInfomessage("Wörterliste erfolgreich geladen");
    		this.resetCounter();
    	}
    }
    
    public void saveWordlistToFile(File file)
    {
        if(file.getAbsolutePath().indexOf(".txt") == -1)
        {
            file = new File(file.getAbsolutePath()+".txt");
        }
        
    	if(WordlistModel.saveToFile(file, this.listModel))
    	{
    		this.mainView.showInfomessage("Wörterliste erfolgreich gespeichert");
    	}
    	else
    	{
    		this.mainView.showInfomessage("Wörterliste konnte nicht gespeichert werden");
    	}
    }
    
    public void appClosed()
    {
    	this.saveConfiguration();
    }
    
    public void requestOptionsDialog()
    {
    	new OptionsController(this.mainView, this);
    }
    
    public void aboutPopUp()
    {
        this.mainView.showInfomessage("Version: 1.1.1\nWissenschaftliche Leitung: Dr. Christiane Ritter\nTechnische Umsetzung: Thomas Ritter\n2007 All rights reserved");
    }
    
    public void toogleAsterisk()
    {
    	if(this.addAsterisk == true)
    	{
    		Configuration.getConfigInstance().getConfigEntry("showAsterisk").setValue("false");
    		this.addAsterisk = false;
    	}
    	else
    	{
    		Configuration.getConfigInstance().getConfigEntry("showAsterisk").setValue("true");
    		this.addAsterisk = true;
    	}
    }
    
    public void toogleWordCount()
    {
    	if(this.wordCountActive == true)
    	{
    		Configuration.getConfigInstance().getConfigEntry("countWords").setValue("false");
    		this.wordCountActive = false;
    	}
    	else
    	{
    		Configuration.getConfigInstance().getConfigEntry("countWords").setValue("true");
    		this.wordCountActive = true;
    	}
    }
    
    public void setAsteriskSymbol()
    {
    	String symbol = this.mainView.requestAsteriskSymbol();
    	
    	if(symbol.length() > 0)
    	{
    		Configuration.getConfigInstance().getConfigEntry("asterisk").setValue(symbol);
    	}
    }
    
    public void setWordCounter()
    {
    	int count = this.mainView.requestWordCount();
    	
    	if(count > 0)
    	{
    		Configuration.getConfigInstance().getConfigEntry("maximumWords").setValue(String.valueOf(count));
    		this.wordCount = count;
    	}
    }
    
    public void removeVowelRenderer()
    {
    	this.mainView.getWordview().removeRenderer(this.vowelRenderer);
		Configuration.getConfigInstance().getConfigEntry("highlightVowels").setValue("false");
    }
    
    public void addVowelRenderer()
    {
    	this.mainView.getWordview().addRenderer(this.vowelRenderer);
		Configuration.getConfigInstance().getConfigEntry("highlightVowels").setValue("true");
    }
    
    public void addNoSeparatorRenderer()
    {
    	this.mainView.getWordview().addRenderer(this.noSeperatorRenderer);
		Configuration.getConfigInstance().getConfigEntry("showSeparator").setValue("false");
    }
    
    public void removeNoSeparatorRenderer()
    {
    	this.mainView.getWordview().removeRenderer(this.noSeperatorRenderer);
		Configuration.getConfigInstance().getConfigEntry("showSeparator").setValue("true");
    }
    
    public void setVowelRendererColor(Color color)
    {
    	this.vowelRenderer.setColor(color);
    }
    
    private void saveConfiguration()
    {
    	new ConfigurationWriter("config.xml", Configuration.getConfigInstance()).writeConfigToXML();
    }
}
