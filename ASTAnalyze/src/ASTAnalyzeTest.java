import org.eclipse.jdt.core.JavaCore;
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
import java.util.Map;

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
		String javaFile="";
		for (File files: fileList) {
			javaFile = analyzer.getFile(files);
		}
		assertEquals(javaFile,"b");
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
		String javaFile="";
		for (File files: fileList) {
			javaFile = analyzer.getFile(files);
		}
		assertEquals(javaFile,"");
	}
	
	/**
	 * Check that a directory with empty files returns an empty string
	 * @throws IOException 
	 * @see getFile
	 * @see getCodeBase
	 */
	@Test
	public void testEmptyFiles() throws IOException {
		// Prepare source code from pathfile
		ASTAnalyze analyzer = new ASTAnalyze();
		String sourcepath = BASEDIR+"TestingFiles";			// Source path from terminal argument
		File directory = new File(sourcepath);				// Create File Object with source name
		File[] fileList = directory.listFiles();			// Gets all files in directory into a File[]
		String javaFile="";
		for (File files: fileList) {
			javaFile = analyzer.getFile(files);
		}
		assertEquals(javaFile,"b");
	}
	
	
	/**
	 * Check for a null-pointer exception when an invalid directory is provided
	 * @throws IOException 
	 * @see getFile
	 * @see getCodeBase
	 */
	@Test(expected = NullPointerException.class)
	public void testInvalidDirectory() throws IOException {
		// Prepare source code from pathfile
		ASTAnalyze analyzer = new ASTAnalyze();
		String sourcepath = BASEDIR+"TestingFiles";			// Source path from terminal argument
		File directory = new File(sourcepath);				// Create File Object with source name
		File[] fileList = directory.listFiles();			// Gets all files in directory into a File[]
		String javaFile="";
		for (File files: fileList) {
			javaFile = analyzer.getFile(files);
		}
	}
	
	
	/**
	 * Check for a null-pointer exception when an invalid set of files is provided
	 * @throws IOException 
	 * @see getFile
	 * @see getCodeBase
	 */
	@Test(expected = NullPointerException.class)
	public void testReference() throws IOException {
		ASTAnalyze analyzer = new ASTAnalyze();
		String sourcepath = BASEDIR+"TestingFiles"+File.separator+;			// Source path from terminal argument
		File directory = new File(sourcepath);				// Create File Object with source name
		File[] fileList = directory.listFiles();			// Gets all files in directory into a File[]
		String javaFile="";
		String targetName = "java.util."
		ASTParser parser = analyzer.initParser(javaFile, null);
			analyzer.parse(parser, "java.util.Class");
		}
	}
	
	
	/**
	 * Test if TypeDeclaration nodes are counted properly
	 * @throws IOException 
	 * 
	 */	
	@Test
	public void testClassDeclaration() throws IOException {
		int decCount;
		ASTAnalyze analyzer = new ASTAnalyze();
		String sourcepath = BASEDIR+"TestingFiles"+File.separator+;			// Source path from terminal argument
		File directory = new File(sourcepath);				// Create File Object with source name
		File[] fileList = directory.listFiles();			// Gets all files in directory into a File[]
		String javaFile="";
		String targetName = "java.util."
		for (File files: fileList) {
			javaFile = analyzer.getFile(files);
			ASTParser parser = analyzer.initParser(javaFile, files);
			analyzer.parse(parser, "java.util.Class");
		}
		assertEquals(decCount,1);
	}
	
	/**
	 * Test if AnnotationTypeDeclaration nodes are counted properly
	 * @throws IOException
	 */	
	@Test
	public void testAnnotationDeclaration() throws IOException {
		int decCount;
		ASTAnalyze analyzer = new ASTAnalyze();
		String sourcepath = BASEDIR+"TestingFiles"+File.separator+;			// Source path from terminal argument
		File directory = new File(sourcepath);				// Create File Object with source name
		File[] fileList = directory.listFiles();			// Gets all files in directory into a File[]
		String javaFile="";
		String targetName = "java.util."
		for (File files: fileList) {
			javaFile = analyzer.getFile(files);
			ASTParser parser = analyzer.initParser(javaFile, files);
			analyzer.parse(parser, "java.util.Class");
		}
		assertEquals(decCount,1);
	}
	
	/**
	 * Test if EnumDeclaration nodes are counted properly
	 * @throws IOException
	 */	
	@Test
	public void testEnumDeclaration() throws IOException {
		int decCount;
		ASTAnalyze analyzer = new ASTAnalyze();
		String sourcepath = BASEDIR+"TestingFiles"+File.separator+;			// Source path from terminal argument
		File directory = new File(sourcepath);				// Create File Object with source name
		File[] fileList = directory.listFiles();			// Gets all files in directory into a File[]
		String javaFile="";
		String targetName = "java.util."
		for (File files: fileList) {
			javaFile = analyzer.getFile(files);
			ASTParser parser = analyzer.initParser(javaFile, files);
			analyzer.parse(parser, "java.util.Class");
		}
		assertEquals(decCount,1);
	}
	
	/**
	 * Test if references are counted properly
	 * @throws IOException 
	 *
	 * 
	 */	
	@Test
	public void testReference() throws IOException {
		int refCount;
		ASTAnalyze analyzer = new ASTAnalyze();
		String sourcepath = BASEDIR+"TestingFiles"+File.separator+;			// Source path from terminal argument
		File directory = new File(sourcepath);				// Create File Object with source name
		File[] fileList = directory.listFiles();			// Gets all files in directory into a File[]
		String javaFile="";
		String targetName = "java.util."
		for (File files: fileList) {
			javaFile = analyzer.getFile(files);
			ASTParser parser = analyzer.initParser(javaFile, files);
			analyzer.parse(parser, "java.util.Class");
		}
		assertEquals(refCount,1);
	}
	
	/**
	 * Test for zero references and zero declarations in an empty file
	 * @throws IOException 
	 * 
	 * 
	 */	
	@Test
	public void testReference() throws IOException {
		int refCount;
		int decCount;
		ASTAnalyze analyzer = new ASTAnalyze();
		String sourcepath = BASEDIR+"TestingFiles"+File.separator+;			// Source path from terminal argument
		File directory = new File(sourcepath);				// Create File Object with source name
		File[] fileList = directory.listFiles();			// Gets all files in directory into a File[]
		String javaFile="";
		String targetName = "java.util."
		for (File files: fileList) {
			javaFile = analyzer.getFile(files);
			ASTParser parser = analyzer.initParser(javaFile, files);
			analyzer.parse(parser, "java.util.Class");
		}
		assertEquals(refCount,0);
		assertEquals(decCount, 0);
	}
	
}
