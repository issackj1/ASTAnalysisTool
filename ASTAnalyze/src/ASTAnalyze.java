import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * 
 * @author Jin Song, Issack John
 * @version 1.0
 * @since March/13/2018
 */

public class ASTAnalyze {

	public int decCount;
	public int refCount;

	public ASTAnalyze() {
		
		this.decCount = 0;
		this.refCount = 0;
	
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
		
	public void parse(ASTParser parser, String targetName) {
		
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		cu.accept(new ASTVisitor() {
			
			public boolean visit(TypeDeclaration node) {
			
				ITypeBinding type = node.resolveBinding();
				String nodeName = type.getQualifiedName();
				
				if (targetName.equals(nodeName)) {
					decCount++;
				}
				return false;
			}
			
			public boolean visit(AnnotationTypeDeclaration node) {
				
				ITypeBinding type =  node.resolveBinding();
				String nodeName = type.getQualifiedName();
				
				if (targetName.equals(nodeName)) {
					decCount++;
				}
				return false;				
			}
			
			public boolean visit(EnumDeclaration node) {
				
				ITypeBinding type = node.resolveBinding();
				String nodeName = type.getQualifiedName();
				
				if (targetName.equals(nodeName)) {
					decCount++;
				}
				return false;
			}
			
		});
		
		cu.accept(new ASTVisitor() {
			
			public boolean visit(SimpleName node) {
				
				IBinding binding = node.resolveBinding();
			
			
				if(binding instanceof IVariableBinding) {
					IVariableBinding varBinding = (IVariableBinding) binding;
					
					if(varBinding.isField() && 
							targetName.equals(varBinding.getType().getQualifiedName())){
						refCount++;
						System.out.println("I've been hit!");
					}
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
					ITypeBinding declaringType = varBinding.getDeclaringClass();
					//System.out.println(varBinding.getVariableDeclaration().);

					if(varBinding.isField() && 
							targetName.equals(varBinding.getType().getQualifiedName()) ){
						referenceCount++;
					}
				}
				return true;
			}
		});
	}
	
	public int getDeclarationCount() {
		return this.decCount;
	}
	
	public int getReferenceCount() {
		return this.refCount;
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
			analyzer.parse(parser, targetName);
			
		}
		
		System.out.println("Type to Count: " + targetName);
		System.out.println("Number of Declarations: " + analyzer.getDeclarationCount());
		System.out.println("Number of References: " + analyzer.getReferenceCount());	
	}

}
