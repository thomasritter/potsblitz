/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
package gui.views;

public interface IMainView {

	public void showWordInTextArea(String word, int fontsize, int delay);
    
    public void clearTextArea();

	public void repaint();
    
    public void showView();
    
    public void startTimer(int delay);
    
    public void stopTimer();
    
    public void showInfomessage(String text);
    
    public String requestAsteriskSymbol();
    
    public int requestWordCount();
    
    public IWordView getWordview();
    
    public void toggleSeparatorMenuItem();
    
    public void toggleVowelMenuItem();
    
    public void toggleAsteriskMenuItem();
    
    public void togglePlayModeMenuItem();
    
    public void toggleWordCountActiveMenuItem();
    
    public void setWindowTitle(String title);
}