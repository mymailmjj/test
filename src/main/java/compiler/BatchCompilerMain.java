/**
 * 
 */
package compiler;

import java.io.PrintWriter;

import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.core.compiler.batch.BatchCompiler;

/**
 * 
 * eclipse jdt 官方提供的一种是用command的便宜方式
 * @author cango
 *
 */
public class BatchCompilerMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		CompilationProgress progress = new MyCompliationProgress(); // instantiate your subclass
		BatchCompiler.compile(
		"-classpath rt.jar C:\\Users\\cango\\Desktop\\000f\\compiler\\test\\HelloJava.java",
		new PrintWriter(System.out),
		new PrintWriter(System.err),
		progress);
		
	}

}
