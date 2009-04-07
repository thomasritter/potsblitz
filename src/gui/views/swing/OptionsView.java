/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package gui.views.swing;

import gui.controller.OptionsController;
import gui.views.IWordView;
import gui.views.IMainView;
import gui.views.IOptionsView;
import gui.views.VowelsRenderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

public class OptionsView extends JDialog implements ActionListener, IOptionsView
{
	private OptionsController controller;
	
	private JPanel previewPanel;
	private JPanel optionsPanel;
	private JPanel generalPanel;
	private JPanel colorPanel;
	private JPanel controlPanel;
	private IWordView previewArea;
	private JComboBox fontSelection;
	private JButton textColorButton;
	private JButton backgroundColorButton;
	private JButton vowelColorButton;
	private JButton cancelButton;
	private JButton okButton;
	private JSpinner delaySpinner;
	private JComboBox fontSizeSelection;
	private VowelsRenderer vowelRenderer;
	
	public OptionsView(IMainView owner, OptionsController optionController)
	{
		super((JFrame)owner, "Allgemeine Einstellungen", true);
		this.controller = optionController;
		
		optionsPanel = new JPanel(new  BorderLayout());
		optionsPanel.setBorder(BorderFactory.createEtchedBorder());
		
		generalPanel = new JPanel(new GridLayout(3, 1));
		colorPanel = new JPanel(new GridLayout(0, 3));
		colorPanel.setBorder(BorderFactory.createEtchedBorder());
		fontSelection = this.createFontsCombobox();
		fontSelection.addActionListener(this);
		fontSizeSelection = this.createFontsizeCombobox();
		textColorButton = new JButton("Textfarbe wählen");
		textColorButton.setName("textColorButton");
		textColorButton.addActionListener(this);
		backgroundColorButton = new JButton("Hintergrundfarbe wählen");
		backgroundColorButton.setName("backgroundColorButton");
		backgroundColorButton.addActionListener(this);
		vowelColorButton = new JButton("Vokalfarbe");
		vowelColorButton.setName("vowelColorButton");
		vowelColorButton.addActionListener(this);
		
		delaySpinner = new JSpinner();
		delaySpinner.setValue(new Integer(1000));
		JLabel fontLabel = new JLabel("Schriftart");
		JLabel fontSizeLabel = new JLabel("Schriftgröße");
		JLabel delayLabel = new JLabel("Anzeigezeit in Millisekunden");
		generalPanel.add(delayLabel);
		generalPanel.add(delaySpinner);
		generalPanel.add(fontSizeLabel);
		generalPanel.add(fontSizeSelection);
		generalPanel.add(fontLabel);
		generalPanel.add(fontSelection);
		colorPanel.add(textColorButton);
		colorPanel.add(vowelColorButton);
		colorPanel.add(backgroundColorButton);
		
		optionsPanel.add(generalPanel, BorderLayout.NORTH);
		optionsPanel.add(colorPanel, BorderLayout.SOUTH);
		
		previewPanel = new JPanel(new BorderLayout());
		previewPanel.setBorder(BorderFactory.createTitledBorder("Vorschau"));
		previewArea = new WordView();
		previewPanel.add((JEditorPane)previewArea, BorderLayout.CENTER);

		controlPanel = new JPanel();
		controlPanel.setBorder(BorderFactory.createEtchedBorder());
		okButton = new JButton("Ok");
		okButton.setName("okButton");
		okButton.addActionListener(this);
		cancelButton = new JButton("Abbrechen");
		cancelButton.setName("cancelButton");
		cancelButton.addActionListener(this);
		controlPanel.add(okButton);
		controlPanel.add(cancelButton);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.optionsPanel, BorderLayout.NORTH);
		this.getContentPane().add(this.previewPanel, BorderLayout.CENTER);
		this.getContentPane().add(this.controlPanel, BorderLayout.SOUTH);
		
		this.pack();
		this.setSize(660, 283);
		this.setLocationRelativeTo((JFrame)owner);
	}
	
	/* (non-Javadoc)
	 * @see gui.views.swing.IOptionsView#showView()
	 */
	public void showView()
	{
		this.setVisible(true);		
	}
	
	public void hideView() 
	{
		this.setVisible(false);
	}
	
	public void setPreviewBackgroundColor(int color)
	{
		this.previewArea.setBackgroundColor(new Color(color));
	}
	
	public void setPreviewTextColor(int color)
	{
		this.previewArea.setTextColor(new Color(color));
	}
	
	public void setPreviewText(String word)
	{
		this.previewArea.setWord(word, 91);
	}

	public void setFont(String font) 
	{
		this.previewArea.setFont(font);
		this.fontSelection.setSelectedItem(font);
	}

	public void setFontsize(int fontSize) 
	{
		this.previewArea.setFontSize(fontSize);
		this.fontSizeSelection.setSelectedItem(new Integer(fontSize));
	}

	public void setDelay(int delay) 
	{
		this.delaySpinner.setValue(new Integer(delay));
	}
	
	public void setVowelColor(int vowelColor) 
	{
		if(this.vowelRenderer == null)
		{
			this.vowelRenderer = new VowelsRenderer(new Color(vowelColor));
			this.previewArea.addRenderer(this.vowelRenderer);
		}
		else
		{
			this.vowelRenderer.setColor(new Color(vowelColor));
		}
	}

	public int getDelay() 
	{
		return (Integer)this.delaySpinner.getValue();
	}

	public int getFontSize() 
	{
		return (Integer)this.fontSizeSelection.getSelectedItem();
	}
	
	public String getTextFont() 
	{
		return (String)this.fontSelection.getSelectedItem();
	}

	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() instanceof JButton)
		{
			JButton button = (JButton)event.getSource();
		
			if(button.getName() == "okButton")
			{
				this.controller.requestOk();
			}
			if(button.getName() == "cancelButton")
			{
				this.controller.requestCancel();
			}
	        if(button.getName() == "backgroundColorButton")
	        {
	        	Color newColor = JColorChooser.showDialog(
	                    this, "Farbe auswählen", new Color(0,0,0));
	        	if(newColor != null)
	        	{
	        		this.controller.backgroundColorSelected(newColor);
	        	}
	        }
			if(button.getName() == "textColorButton")
	        {
	        	Color newColor = JColorChooser.showDialog(
	                    this, "Farbe auswählen", new Color(0,0,0));
	        	if(newColor != null)
	        	{
	        		this.controller.textColorSelected(newColor);
	        	}
	        }
			if(button.getName() == "vowelColorButton")
	        {
	        	Color newColor = JColorChooser.showDialog(
	                    this, "Farbe auswählen", new Color(0,0,0));
	        	if(newColor != null)
	        	{
	        		this.controller.vowelColorSelected(newColor);
	        	}
	        }
		}
		if(event.getSource() instanceof JComboBox)
		{
			this.controller.fontSelected(this.fontSelection.getSelectedItem().toString());
        }
	}
	
	private JComboBox createFontsizeCombobox()
	{
		JComboBox combobox;
		Vector<Integer> fontsizes = new Vector<Integer>();
		for(int i = 10; i < 300; i=i+10)
		{
			fontsizes.add(i);
		}
		combobox = new JComboBox(fontsizes);
		combobox.setSelectedItem(110);
		
		return combobox; 
	}
	
	private JComboBox createFontsCombobox()
	{
		Font font;
		Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		String[] fontNames = new String[fonts.length];
		for(int i = 0; i < fonts.length; i++)
		{
			font = fonts[i];
			fontNames[i] = font.getFontName();
		}
		return new JComboBox(fontNames);
	}
	
	public void setSelectedFontsize(int fontsize)
	{
		this.fontSizeSelection.setSelectedItem(new Integer(fontsize));
	}
}
