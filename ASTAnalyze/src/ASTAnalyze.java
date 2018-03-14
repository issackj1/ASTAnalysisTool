import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import javax.naming.Binding;


/**
 * 
 * @author Jin Song, Issack John
 * @version 1.0
 * @since March/13/2018
 */

public class ASTAnalyze {

	public int declarationCount;
	public int referenceCount;

	public ASTAnalyze() {
		
		this.declarationCount = 0;
		this.referenceCount = 0;
	
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
	
	@Deprecated
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
	
	
	public ASTParser initParser(String code, File fileName) {
		
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
	
		
	public void parse(ASTParser parser, String targetName, String source, File file) {
			
		this.countClass(parser);
		parser = this.initParser(source, file);
		
		this.countAnnotation(parser);
		parser = this.initParser(source, file);
		
		this.countInterface(parser);
		parser = this.initParser(source, file);
		
		this.countEnum(parser);
		parser = this.initParser(source, file);
						
		this.countReference(parser, targetName);
		
	}
			
	public void countClass(ASTParser parser) {

		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		cu.accept(new ASTVisitor() {

			public boolean visit(TypeDeclaration node) {
			
				if(!node.isInterface()) {
					declarationCount++;
					System.out.println("Declaration was found");
				}
												
				return false;
			}

		});
	}
	
	public void countAnnotation(ASTParser parser) {

		CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		cu.accept(new ASTVisitor() {

			public boolean visit(AnnotationTypeDeclaration node) {
				
				if (node.getClass().isAnnotation()) {
					declarationCount++;
					System.out.println("Declaration was found");
				}

				return false;
			}

		});
	}
	
	public void countInterface(ASTParser parser) {
		
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		cu.accept(new ASTVisitor() {
			
			public boolean visit(TypeDeclaration node) {
				
				if (node.isInterface()) {
					declarationCount++;
					System.out.println("Declaration was found");
				}
				
				return false;
			}
		});
	}
	
	public void countReference(ASTParser parser, String targetName) {

		CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		cu.accept(new ASTVisitor() {

			public boolean visit(SimpleName node) {
				
				IBinding binding = node.resolveBinding();
				
				if(binding instanceof IVariableBinding) {
					IVariableBinding varBinding = (IVariableBinding) binding;
					if(targetName.equals(varBinding.getType().getQualifiedName())){
						referenceCount++;
						System.out.println("Reference was found");
					}
				}
				return true;
			}
		});
	}
	
	public void countEnum(ASTParser parser) {
		
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		cu.accept(new ASTVisitor() {
			
			public boolean visit(EnumDeclaration node) {
				
				declarationCount++;
				
				return false;
			}
		});
	}

	public int getDeclarationCount() {
		return this.declarationCount;
	}
	
	public int getReferenceCount() {
		return this.referenceCount;
	}
	
	public static void main(String[] args) throws IOException {

		// Initialize Class ASTAnalyze
		ASTAnalyze analyzer = new ASTAnalyze();

		// Prepare source code from pathfile
		String sourcepath = args[0];		
		String targetName = args[1];
		
		// Source path from terminal argument
		File directory = new File(sourcepath);				// Create File Object with source name
		File[] fileList = directory.listFiles();			// Gets all files in directory into a File[]
		
		for (File files: fileList) {
			String javaFile = analyzer.getFile(files);
			ASTParser parser = analyzer.initParser(javaFile, files);
			analyzer.parse(parser, targetName, javaFile, files);
		}
		
		System.out.println(analyzer.getDeclarationCount());
		
		System.out.println(analyzer.getReferenceCount());
	
	}

}
