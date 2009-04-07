/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package gui.views.swing;

import gui.controller.MainController;
import gui.views.IWordView;
import gui.views.IMainView;
import gui.views.KeyEnter;
import gui.views.KeyLeft;
import gui.views.KeyRight;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

import configuration.Configuration;

public class MainView extends JFrame implements IMainView, ActionListener
{
	private JPanel wordViewPanel;
	private JPanel buttonPanel;
	private IWordView wordView;
	private JButton nextButton;
	private JButton previousButton;
	private JButton showButton;
	private JCheckBoxMenuItem highlightVowels;
	private JCheckBoxMenuItem showSeparator;
	private JCheckBoxMenuItem showAsterisk;
	private JCheckBoxMenuItem playRandom;
	private JCheckBoxMenuItem countWords;
    private Timer timer;
    private boolean timerAtWork;
    private MainController controller;
    private JFileChooser filechooser;
	
	public MainView(MainController controller)
	{
        this.controller = controller;
        this.setLookAndFeel();
        
        this.buttonPanel = new JPanel(new GridLayout(0, 3));
		this.buttonPanel.setBorder(BorderFactory.createEtchedBorder());
		this.showButton = new JButton("Wort anzeigen");
		this.showButton.setName("showWord");
		this.showButton.addActionListener(this);
		this.nextButton = new JButton("Nächstes >>");
		this.nextButton.setName("next");
		this.nextButton.addActionListener(this);
		this.previousButton = new JButton("<< Vorheriges");
		this.previousButton.setName("previous");
		this.previousButton.addActionListener(this);
		
		this.buttonPanel.add(this.previousButton);
		this.buttonPanel.add(this.showButton);
		this.buttonPanel.add(this.nextButton);
		
		wordViewPanel = new JPanel();
		wordViewPanel.setLayout(new BorderLayout());
		wordViewPanel.setBorder(BorderFactory.createEtchedBorder());
		
		wordView = new WordView();
		wordView.setFont(Configuration.getConfigInstance().getConfigEntry("font").getValue());
		wordViewPanel.add((JEditorPane)wordView, BorderLayout.CENTER);
		wordViewPanel.add(this.buttonPanel, BorderLayout.SOUTH);
		
		//Key bindings
		((JEditorPane)wordView).getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "showWord");
		((JEditorPane)wordView).getActionMap().put("showWord", new KeyEnter(controller));
		((JEditorPane)wordView).getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "nextWord");
		((JEditorPane)wordView).getActionMap().put("nextWord", new KeyRight(controller));
		((JEditorPane)wordView).getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "previousWord");
		((JEditorPane)wordView).getActionMap().put("previousWord", new KeyLeft(controller));
		
		this.setJMenuBar(this.createMenu());
		this.getContentPane().add(wordViewPanel);
		this.setFocusable(true);
		this.setTitle("PotsBlitz 1.1");
		this.pack();
		this.setSize(1000, 700);
		this.setLocation();
	}
	
    /**
     * Set the jframe location so that it is in the middle of the screen
     *
     */
	private void setLocation()
	{
		//Window has to be in the middle of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = getSize();

		if (size.height > screenSize.height)
		{
			size.height = screenSize.height;
		}
		if (size.width > screenSize.width)
		{
			size.width = screenSize.width;
		}
		screenSize.height = screenSize.height / 2;
		screenSize.width = screenSize.width / 2;
		size.height = size.height / 2;
		size.width = size.width / 2;
		this.setLocation(
			screenSize.height - size.height,
			screenSize.width - size.width);
	}
	
	public void setWindowTitle(String title)
	{
		this.setTitle(title);
	}
	
	/**
	 * Sets the JFrame look and feel
	 */
	private void setLookAndFeel()
	{
		//set Look and Feel
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    SwingUtilities.updateComponentTreeUI(this);
		}
		catch (Exception e)
		{
			this.showInfomessage(e.toString());
		}
	}
    
	/* (non-Javadoc)
	 * @see IGuiView#setWord(java.lang.String)
	 */
	public void showWordInTextArea(String word, int fontsize, int delay)
	{
		if(word != null)
		{
	        if(timerAtWork == false)
	        {
	            this.setFontSize(fontsize);
	            this.wordView.setWord(word);
	            this.startTimer(delay);
	        }
		}
	}
    
    public void clearTextArea()
    {
        this.wordView.setWord("");
    }
	
	/* (non-Javadoc)
	 * @see IGuiView#setWordSize(int)
	 */
	public void setFontSize(int size)
	{
		this.wordView.setFontSize(size);
	}
	
    public void showView()
    {
        this.setVisible(true);
    }
	
	private JMenuBar createMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu wordlistMenu = new JMenu("Wörterliste");
		JMenu optionsMenu = new JMenu("Optionen");
        JMenu infoMenu = new JMenu("Info");

		JMenuItem openWordlistMenuEntry = new JMenuItem("Wörterliste öffnen");
		openWordlistMenuEntry.addActionListener(this);
		openWordlistMenuEntry.setName("openWordlist");
		wordlistMenu.add(openWordlistMenuEntry);
		
		JMenuItem editWordlistMenuEntry = new JMenuItem("Wörterliste bearbeiten");
		editWordlistMenuEntry.addActionListener(this);
		editWordlistMenuEntry.setName("editWordlist");
		wordlistMenu.add(editWordlistMenuEntry);
		
		JMenuItem saveWordlistMenuEntry = new JMenuItem("Wörterliste speichern");
		saveWordlistMenuEntry.addActionListener(this);
		saveWordlistMenuEntry.setName("saveWordlist");
		wordlistMenu.add(saveWordlistMenuEntry);

		JMenuItem generalOptionsMenuEntry = new JMenuItem("Allgemeine Einstellungen");
		generalOptionsMenuEntry.addActionListener(this);
		generalOptionsMenuEntry.setName("generalOptions");
		optionsMenu.add(generalOptionsMenuEntry);
		
		/*JMenuItem defineAsterisk = new JMenuItem("Asterisk definieren");
		defineAsterisk.addActionListener(this);
		defineAsterisk.setName("defineAsterisk");
		optionsMenu.add(defineAsterisk);*/
		
		this.playRandom = new JCheckBoxMenuItem("Zufallsreihenfolge");
		this.playRandom.setName("playRandom");
		this.playRandom.addActionListener(this);
		optionsMenu.add(this.playRandom);
		
		JMenu wordCountMenu = new JMenu("Hinweis zur Wortanzahl");
		optionsMenu.add(wordCountMenu);
		
		this.countWords = new JCheckBoxMenuItem("Aktiv");
		this.countWords.setName("countWords");
		this.countWords.addActionListener(this);
		wordCountMenu.add(this.countWords);
		
		JMenuItem resetCountedWords = new JMenuItem("Zähler zurücksetzen");
		resetCountedWords.setName("resetCountedWords");
		resetCountedWords.addActionListener(this);
		wordCountMenu.add(resetCountedWords);
		
		JMenuItem configureNumberOfWords = new JMenuItem("Anzahl angeben");
		configureNumberOfWords.setName("configureNumberOfWords");
		configureNumberOfWords.addActionListener(this);
		wordCountMenu.add(configureNumberOfWords);
		
		JMenu wordViewRendererMenu = new JMenu("Wortansicht");
		
		this.showSeparator = new JCheckBoxMenuItem("Trennzeichen anzeigen");
		showSeparator.setName("showSeperator");
		showSeparator.addActionListener(this);
		wordViewRendererMenu.add(this.showSeparator);
		
		this.highlightVowels = new JCheckBoxMenuItem("Vokale hervorheben");
		highlightVowels.setName("showVowels");
		highlightVowels.addActionListener(this);
		wordViewRendererMenu.add(this.highlightVowels);
		
		this.showAsterisk = new JCheckBoxMenuItem("Asterisk anzeigen");
		showAsterisk.setName("showAsterisk");
		showAsterisk.addActionListener(this);
		//wordViewRendererMenu.add(this.showAsterisk);
        
        JMenuItem aboutMenuEntry = new JMenuItem("Über...");
        aboutMenuEntry.addActionListener(this);
        aboutMenuEntry.setName("about");
        infoMenu.add(aboutMenuEntry);
		
		menuBar.add(wordlistMenu);
		menuBar.add(optionsMenu);
		menuBar.add(wordViewRendererMenu);
        menuBar.add(infoMenu);
		
		return menuBar;
	}
		
	protected void processWindowEvent(WindowEvent e)
	{
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING)
		{
			this.controller.appClosed();
			System.exit(0);
		}
	}
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent event)
    {
        if(event.getSource() instanceof Timer)
        {
            this.controller.timerEvent();
        }
        if(event.getSource() instanceof JButton)
        {
            if(timerAtWork == false)
            {
                JButton button = (JButton)event.getSource();
                if(button.getName().equalsIgnoreCase("showWord"))
                {
                    this.controller.showWordinTextarea();
                }
                if(button.getName().equalsIgnoreCase("next"))
                {
                    this.controller.showNextWord();
                }
                if(button.getName().equalsIgnoreCase("previous"))
                {
                    this.controller.showPreviousWord();
                }
            }
        }
        if(event.getSource() instanceof JMenuItem)
        {
	        if(((JMenuItem)event.getSource()).getName() == "openWordlist")
	        {
	    		if (this.getFileChooser().showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
	    		{
	            	this.controller.loadWordlistFromFile(this.getFileChooser().getSelectedFile());
	    		}
	        }
	        if(((JMenuItem)event.getSource()).getName() == "editWordlist")
	        {
	        	this.controller.showWordListEditor();
	        }
	        if(((JMenuItem)event.getSource()).getName() == "saveWordlist")
	        {
	    		if (this.getFileChooser().showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
	    		{
	            	this.controller.saveWordlistToFile(this.getFileChooser().getSelectedFile());
	    		}
	        }
	        if(((JMenuItem)event.getSource()).getName() == "generalOptions")
	        {
	            this.controller.requestOptionsDialog();
	        }
	        if(((JMenuItem)event.getSource()).getName() == "defineAsterisk")
	        {
	            this.controller.setAsteriskSymbol();
	        }
            if(((JMenuItem)event.getSource()).getName() == "about")
            {
                this.controller.aboutPopUp();
            }
            if(((JMenuItem)event.getSource()).getName() == "resetCountedWords")
            {
                this.controller.resetCounterFromView();
            }
            if(((JMenuItem)event.getSource()).getName() == "configureNumberOfWords")
            {
                this.controller.setWordCounter();
            }
        }

        if(event.getSource() instanceof JCheckBoxMenuItem)
        {
        	JCheckBoxMenuItem checkBox = (JCheckBoxMenuItem)event.getSource();
        	
        	if(checkBox.getName() == "showVowels")
        	{
        		if(checkBox.isSelected() == true)
        		{
        			this.controller.addVowelRenderer();
        		}
        		else
        		{
        			this.controller.removeVowelRenderer();
        		}
        	}
        	if(checkBox.getName() == "showSeperator")
        	{
        		if(checkBox.isSelected() == true)
        		{
        			this.controller.removeNoSeparatorRenderer();
        		}
        		else
        		{
        			this.controller.addNoSeparatorRenderer();
        		}
        	}
        	if(checkBox.getName() == "showAsterisk")
        	{
        		this.controller.toogleAsterisk();
        	}
        	if(checkBox.getName() == "playRandom")
        	{
        		this.controller.togglePlayMode();
        	}
        	if(checkBox.getName() == "countWords")
        	{
        		this.controller.toogleWordCount();
        	}
        }
    }
    
	public void showInfomessage(String text)
	{
		JOptionPane.showMessageDialog(
			this,
			text,
			"Info",
			JOptionPane.INFORMATION_MESSAGE);
	}
	
	public String requestAsteriskSymbol()
	{
		String asterisk = "";
        JOptionPane asteriskInput = new JOptionPane();
        asterisk = asteriskInput.showInputDialog(this,
                "Bitte ein Symbol eingeben", 
                Configuration.getConfigInstance().getConfigEntry("asterisk").getValue());
        if (asterisk != null)
        {
            if (asterisk.length() > 0)
            {
            	return asterisk;
            } 
            else
            {
                this.showInfomessage("Mindestens ein Zeichen eingeben");
                this.requestAsteriskSymbol();
            }
        }
		return "";
	}
	
	public int requestWordCount()
	{
		String input;
		int count;
        JOptionPane wordCount = new JOptionPane();
        input = wordCount.showInputDialog(this,
                "Bitte angeben nach wie vielen Wörter\nein Hinweis angezeigt werden soll", 
                Configuration.getConfigInstance().getConfigEntry("maximumWords").getValue());
        if (input != null)
        {
        	count = Integer.parseInt(input);
            if (count > 0)
            {
            	return count;
            } 
            else
            {
                this.showInfomessage("Bitte eine Zahl größer als null eingeben");
                this.requestWordCount();
            }
        }
		return 0;
	}
    
    public void setBackgroundColor(int rgbColor)
    {
    	this.wordView.setBackgroundColor(new Color(rgbColor));
	}

	public void setTextColor(int rgbColor) 
	{
		this.wordView.setTextColor(new Color(rgbColor));
	}

	public JFileChooser getFileChooser()
    {
        if(this.filechooser == null)
        {
            this.filechooser = new JFileChooser();
            this.filechooser.setFileFilter(new UniversalFileFilter("txt"));
            this.filechooser.setAcceptAllFileFilterUsed(false);
            this.filechooser.setMultiSelectionEnabled(false);
        }
        return this.filechooser;
    }
    
    public void startTimer(int delay)
    {
        this.timer = new Timer(delay, this);
        this.timerAtWork = true;
        this.timer.start();
    }
    
    public void stopTimer()
    {
        this.timer.stop();
        this.timerAtWork = false;
    }
    
    public IWordView getWordview()
    {
    	return this.wordView;
    }

	public void toggleAsteriskMenuItem()
	{
		this.showAsterisk.setState(true);
	}

	public void toggleSeparatorMenuItem()
	{
		this.showSeparator.setState(true);
	}

	public void toggleVowelMenuItem() 
	{
		this.highlightVowels.setState(true);
	}

	public void togglePlayModeMenuItem()
	{
		this.playRandom.setState(true);
	}
	
	public void toggleWordCountActiveMenuItem()
	{
		this.countWords.setState(true);
	}
}
