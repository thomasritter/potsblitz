/*
* Copyright (c) 2006, Thomas Alexander Ritter
* All rights reserved.
*
* This software is open-source under the MIT license; see 
* "license.txt" for a description
*/
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for JUnitTests");
		//$JUnit-BEGIN$
		suite.addTestSuite(WordListModelTests.class);
		suite.addTestSuite(RendererTest.class);
		//$JUnit-END$
		return suite;
	}

}
