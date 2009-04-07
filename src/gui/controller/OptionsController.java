/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package gui.controller;

import java.awt.Color;

import configuration.Configuration;

import gui.views.IMainView;
import gui.views.IOptionsView;
import gui.views.swing.OptionsView;

public class OptionsController 
{
	private IOptionsView optionsView;
	private Color backgroundColor;
	private Color textColor;
	private Color vowelColor;
	private String font;
	private int delay;
	private MainController mainController;
	
	public OptionsController(IMainView mainView, MainController mainController)
	{
		this.mainController = mainController;
		this.optionsView = new OptionsView(mainView, this);
		this.loadConfigValues();
		this.updatePreviewView();
		this.optionsView.showView();
	}
	
	public void loadConfigValues()
	{
		this.backgroundColor = new Color(Integer.parseInt(Configuration.getConfigInstance().getConfigEntry("backgroundColor").getValue()));
		this.textColor = new Color(Integer.parseInt(Configuration.getConfigInstance().getConfigEntry("textColor").getValue()));
		this.vowelColor = new Color(Integer.parseInt(Configuration.getConfigInstance().getConfigEntry("vowelColor").getValue()));
		this.font = Configuration.getConfigInstance().getConfigEntry("delay").getValue();
		this.delay = Integer.parseInt(Configuration.getConfigInstance().getConfigEntry("delay").getValue());
		
		this.setOptionViewValues(this.optionsView);
	}
	
	public void setOptionViewValues(IOptionsView optionsView)
	{
		this.optionsView.setPreviewBackgroundColor(this.backgroundColor.getRGB());
		this.optionsView.setPreviewTextColor(this.textColor.getRGB());
		this.optionsView.setDelay(this.delay);
		this.optionsView.setFont(Configuration.getConfigInstance().getConfigEntry("font").getValue());
		this.optionsView.setFontsize(30);
		this.optionsView.setSelectedFontsize(Integer.parseInt(Configuration.getConfigInstance().getConfigEntry("fontsize").getValue()));
		this.optionsView.setVowelColor(this.vowelColor.getRGB());
	}
	
	public void requestCancel()
	{
		this.optionsView.hideView();
	}
	
	public void requestOk()
	{
		this.optionsView.hideView();
		this.mainController.setWordviewBackgroundColor(this.backgroundColor);
		this.mainController.setWordviewTextColor(this.textColor);
		this.mainController.setVowelRendererColor(this.vowelColor);
		this.mainController.setWordviewFont(this.font);
		Configuration.getConfigInstance().getConfigEntry("backgroundColor").setValue(String.valueOf(this.backgroundColor.getRGB()));
		Configuration.getConfigInstance().getConfigEntry("textColor").setValue(String.valueOf(this.textColor.getRGB()));
		Configuration.getConfigInstance().getConfigEntry("vowelColor").setValue(String.valueOf(this.vowelColor.getRGB()));
		Configuration.getConfigInstance().getConfigEntry("fontsize").setValue(String.valueOf(this.optionsView.getFontSize()));
		Configuration.getConfigInstance().getConfigEntry("font").setValue(this.font);
		Configuration.getConfigInstance().getConfigEntry("delay").setValue(String.valueOf(this.optionsView.getDelay()));
	}
	
    public void backgroundColorSelected(Color color)
    {
    	this.backgroundColor = color;
    	this.optionsView.setPreviewBackgroundColor(color.getRGB());
    	this.updatePreviewView();
    }
    
    public void fontSelected(String font)
    {
    	this.font = font;
    	this.optionsView.setFont(font);
    	this.updatePreviewView();
    }
    
    public void textColorSelected(Color color)
    {
    	this.textColor = color;
    	this.optionsView.setPreviewTextColor(color.getRGB());
    	this.updatePreviewView();
    }
    
    public void vowelColorSelected(Color color)
    {
    	this.vowelColor = color;
    	this.optionsView.setVowelColor(color.getRGB());
    	this.updatePreviewView();
    }
    
    public void updatePreviewView()
    {
    	this.optionsView.setPreviewText("abcdefghijklmnopqrstuvwxyz");
    }
}
