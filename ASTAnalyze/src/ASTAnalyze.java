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
	
	public ASTParser initParser(String code, File fileName, String source) {
		
		String classPathReplacer = new File("").getAbsolutePath();
		String[] classPathReplacerArray = {classPathReplacer};
				
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(code.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		Map options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5);
		parser.setCompilerOptions(options);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setUnitName(fileName.getName());
		
		parser.setEnvironment(classPathReplacerArray, null, null, false);
		return parser;
	}
		
	public void parse(ASTParser parser, String targetName) {
			
		switch(targetName) {
		
		case "Class":
			this.classCount(parser);
			break;
			
		case "Annotation":
			this.annotationCount(parser);
			break;
			
		case "Interface":
			this.interfaceCount(parser);
			break;
			
		case "Enumeration":
			this.enumCount(parser);
			break;
			
		default:
			break;
			
		}
	}
		
	public void classCount(ASTParser parser) {

		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		cu.accept(new ASTVisitor() {

			public boolean visit(TypeDeclaration node) {
			
				if(!node.isInterface()) {
					System.out.println(node.getName().getFullyQualifiedName());
					classDeclarations++;
				}
			
				return false;
			}

		});
	}

	public void annotationCount(ASTParser parser) {

		CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		cu.accept(new ASTVisitor() {

			public boolean visit(AnnotationTypeDeclaration node) {
				
				if (node.getClass().isAnnotation()) {
					System.out.println(node.getName().getFullyQualifiedName());
					annotationDeclarations++;
				}

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
		String sourcepath = args[0];		
		//String targetClass = args[1];
		
		// Source path from terminal argument
		File directory = new File(sourcepath);				// Create File Object with source name
		File[] fileList = directory.listFiles();			// Gets all files in directory into a File[]
		
		for (File files: fileList) {
			String javaFile = analyzer.getFile(files);
			ASTParser parser = analyzer.initParser(javaFile, files, sourcepath);
			
			analyzer.classCount(parser);
		}
		
	
	}

}
