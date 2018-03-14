import org.eclipse.jdt.core.dom.*;

/**
 * 
 * @author Alan Fung
 * @version 1.0
 * @since March/13/2018
 */

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
public class ASTAnalyzeTest {
	
	private static String BASEDIR = "C:"+File.separator+"Users"+File.separator+"songjayw"+File.separator+"Desktop"+File.separator; 
	
	
	/**
	 * Check that the getFile and getCodeBase methods return the correct number of files.
	 * @throws IOException 
	 * @see getFile
	 * @see getCodeBase
	 */
	@Test
	public void testNumberOfFilesFound() throws IOException {
		// Prepare source code from pathfile
		ASTAnalyze analyzer = new ASTAnalyze();
		String sourcepath = BASEDIR+"TestingFiles";			// Source path from terminal argument
		File directory = new File(sourcepath);				// Create File Object with source name
		File[] fileList = directory.listFiles();			// Gets all files in directory into a File[]
		String codeBase = analyzer.getCodeBase(fileList);
		
		assertEquals(4, fileList.length);
		
	}
	
	/**
	 * Check that the getCodeBase returns the correct string.
	 * @throws IOException 
	 * @see getCodeBase
	 */
	@Test
	public void getcodeBase() throws IOException {
		// Prepare source code from pathfile
		ASTAnalyze analyzer = new ASTAnalyze();
		String sourcepath = BASEDIR+"TestingFiles";			// Source path from terminal argument
		File directory = new File(sourcepath);				// Create File Object with source name
		File[] fileList = directory.listFiles();			// Gets all files in directory into a File[]
		String codeBase = analyzer.getCodeBase(fileList);
		
		assertEquals(codeBase,"b");
	}
	
	/**
	 * Check that an empty directory returns an empty string
	 * @throws IOException 
	 * @see getFile
	 * @see getCodeBase
	 */
	@Test
	public void testNoFiles() throws IOException {
		// Prepare source code from pathfile
		ASTAnalyze analyzer = new ASTAnalyze();
		String sourcepath = BASEDIR+"TestingFiles";			// Source path from terminal argument
		File directory = new File(sourcepath);				// Create File Object with source name
		File[] fileList = directory.listFiles();			// Gets all files in directory into a File[]
		String codeBase = analyzer.getCodeBase(fileList);
		
		assertEquals(4, fileList.length);
		
	}
}
