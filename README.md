# ASTAnalysisTool

A simple analysis tool that will find the number of occurences of the declarations of and references to a Java type (as a fully qualified name, like java.lang.String), within a specified directory.

A standalone Java application that: 
1. takes a pathname to indicate a directory of interest,
2. takes a string to indicate a fully qualified name of a Java type,
3. counts the number of declarations of that type within that directory (non-recursively!), and
4. counts the number of references to each occurrence of that type within that directory (non-recursively!).
<type>. Declarations found: <count>; references found: <count>.
