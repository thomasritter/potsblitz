/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
import java.io.File;

import models.WordlistModel;
import junit.framework.TestCase;

public class WordListModelTests extends TestCase
{
	public void testAddWord()
	{
		WordlistModel model = new WordlistModel();
		model.addWord("test");
		assertEquals("test", model.getCurrentWord());
	}
	
	public void testNextWord()
	{
		WordlistModel model = this.setUpTestModel();
		assertEquals("test1", model.getCurrentWord());
		assertEquals("test2", model.getNextWord());
		assertEquals("test3", model.getNextWord());
		assertEquals(null, model.getNextWord());
	}
	
	public void testPreviousWord()
	{
		WordlistModel model = this.setUpTestModel();
		model.getNextWord();
		model.getNextWord();
		model.getNextWord();
		assertEquals("test3", model.getCurrentWord());
		assertEquals("test2", model.getPreviousWord());
		assertEquals("test1", model.getPreviousWord());
		assertEquals("", model.getPreviousWord());
	}
	
	public WordlistModel setUpTestModel()
	{
		WordlistModel model = new WordlistModel();
		model.addWord("test1");
		model.addWord("test2");
		model.addWord("test3");
		
		return model;
	}
	
	public void testgetListAsVector()
	{
		WordlistModel model = this.setUpTestModel();
		assertEquals(3, model.getListAsVector().size());
	}
	
	public void testdeleteWord()
	{
		WordlistModel model = this.setUpTestModel();
		model.deleteWord(0);
		assertEquals("test2", model.getCurrentWord());
	}
	
	public void testUpdateWord()
	{
		WordlistModel model = this.setUpTestModel();
		model.updateWord(0, "renamedWord");
		assertEquals("renamedWord", model.getCurrentWord());
	}
	
	public void testSaveModel()
	{
		File testFile = new File("save_test.txt");
		WordlistModel model = this.setUpTestModel();
		assertEquals(true, WordlistModel.saveToFile(testFile, model));
		
		WordlistModel reloadModel = WordlistModel.readFromFile(testFile);
		assertEquals(true, testFile.delete());
		assertEquals("test1", reloadModel.getCurrentWord());
		assertEquals("test2", reloadModel.getNextWord());
		assertEquals("test3", reloadModel.getNextWord());		
	}
	
	public void testReadModel()
	{
		WordlistModel model = WordlistModel.readFromFile(new File("test.txt"));
		assertEquals("test1", model.getCurrentWord());
		assertEquals("test2", model.getNextWord());
		assertEquals("test3", model.getNextWord());		
	}
	
	public void testGetRandomWordEmptyList()
	{
		try
		{
			WordlistModel model = new WordlistModel();
			model.getNextRandomWord();
		}
		catch (Exception e) 
		{
			fail();
		}
	}
}
