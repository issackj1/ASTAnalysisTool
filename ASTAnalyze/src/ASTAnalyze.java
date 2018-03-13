import java.io.*;
import org.eclipse.jdt.core.dom.*;
import java.util.*;

/**
 * 
 * @author Jin Song
 * @version 1.0
 * @since March/13/2018
 */

public class ASTAnalyze {


	public String getFile(File filename) throws IOException {
		
		StringBuilder stringbuilder = new StringBuilder();
		if (filename.isFile()) {
			
			BufferedReader br = null;
			
			try {
				br = new BufferedReader(new FileReader(filename));
				String newLine;
				
				while((newLine = br.readLine()) != null) {
					stringbuilder.append(newLine);
					stringbuilder.append(System.lineSeparator());
					
				}
			}
			finally {
				if(br != null) {
					br.close();
				}
			}
		}
		
	return stringbuilder.toString();
	}
	
	public String getCodeBase(File[] fileList) throws IOException {
	
		ArrayList<String> codeBaseArray = new ArrayList<String>();
		StringBuilder sr = new StringBuilder();
		
		for (File files: fileList) {
			codeBaseArray.add(getFile(files));
		}
		
		for (String strings: codeBaseArray) {
			sr.append(strings);
		}
		
		return sr.toString();
	}
		
	public static void main(String[] args) throws IOException {

		// Initialize Class ASTAnalyze
		ASTAnalyze analyzer = new ASTAnalyze();
		
		// Prepare source code from pathfile
		String sourcepath = args[0];					// Source path from terminal argument
		File directory= new File(sourcepath);			// Create File Object with source name
		File[] fileList = directory.listFiles();		// Gets all files in directory into a File[]
		String codeBase = analyzer.getCodeBase(fileList);
	
		System.out.println(codeBase);
	}

}
