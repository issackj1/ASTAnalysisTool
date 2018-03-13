import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import java.io.*;
import java.util.*;


/**
 * 
 * @author Jin Song
 * @version 1.0
 * @since March/13/2018
 */

public class ASTAnalyze {

	public int classDeclarations;
	public int annotationDeclarations;
	public int enumDeclarations;
	public int interfaceDeclarations;

	public ASTAnalyze() {
		
		this.classDeclarations = 0;
		this.annotationDeclarations = 0;
		this.enumDeclarations = 0;
		this.interfaceDeclarations = 0;
	
	}
	
	public String getFile(File filename) throws IOException {
		
		StringBuilder stringbuilder = new StringBuilder();
		if (filename.isFile() && filename.getName().endsWith(".java")) {
			
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
	
	public ASTParser initParser(String code) {
		
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(code.toCharArray());
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		Map options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5);
		parser.setCompilerOptions(options);
		return parser;
	}
	
	// Take note that many methods below have not been created yet -
	// Use classCount as a framework to create the other methods to increment the 
	// count of the declarations/references 
	
	public void parse(ASTParser parser, String targetName) {
			
		switch(targetName) {
		
		case "Class":
			this.classCount(parser);
		case "Annotation":
			this.annotationCount(parser);
		case "Interface":
			this.interfaceCount(parser);
		case "Enumeration":
			this.enumCount(parser);

		}
	}
	
	
	// Sample createAST portion method that will go under parse(ASTParser parse, String targetName)
	
	public void classCount(ASTParser parser) {
		
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		cu.accept(new ASTVisitor() {
			
			public boolean visit(TypeDeclaration node) {			
				
				return false;
			}
			
		});
	}
	
	public void interfaceCount(ASTParser parser) {
		
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		cu.accept(new ASTVisitor() {
			
			public boolean visit(TypeDeclaration node) {
				
				if (node.isInterface()) {
					System.out.println(node.getName().getFullyQualifiedName());
					interfaceDeclarations++;
				}
				return false;
			}
		});
	}
	
	public void enumCount(ASTParser parser) {
		
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		cu.accept(new ASTVisitor() {
			
			public boolean visit(EnumDeclaration node) {
				
				System.out.println(node.getName().getFullyQualifiedName());
				enumDeclarations++;
				
				return false;
			}
		});
	}

	
	public static void main(String[] args) throws IOException {

		// Initialize Class ASTAnalyze
		ASTAnalyze analyzer = new ASTAnalyze();

		// Prepare source code from pathfile
		String sourcepath = args[0];						// Source path from terminal argument
		File directory = new File(sourcepath);				// Create File Object with source name
		File[] fileList = directory.listFiles();			// Gets all files in directory into a File[]
		String codeBase = analyzer.getCodeBase(fileList);
	
		// Initialize ASTParser
		ASTParser parser = analyzer.initParser(codeBase);	// Initialize ASTParser with given preferences
		
		// Determine fully qualified name to count
		//String target = args[1];
		//analyzer.parse(parser, target);
		
		analyzer.enumCount(parser);
			

		
	
		
		
	}

}
