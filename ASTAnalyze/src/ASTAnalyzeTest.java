import org.eclipse.jdt.core.dom.*;

/**
 * 
 * @author Alan Fung
 * @version 1.0
 * @since March/13/2018
 */

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
public class ASTAnalyzeTest {
	
	private static String BASEDIR = "\\some-path\\"; 
	/**
	 * Checks that a parser can be constructed.
	 */
	@Test
	public void testNumberOfFilesFound() {
		// Prepare source code from pathfile
		ASTAnalyze analyzer = new ASTAnalyze();
		String sourcepath = args[0];						// Source path from terminal argument
		File directory = new File(sourcepath);				// Create File Object with source name
		File[] fileList = directory.listFiles();			// Gets all files in directory into a File[]
		String codeBase = analyzer.getCodeBase(fileList);
	}
}
