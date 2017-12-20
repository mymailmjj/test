package compiler;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.internal.compiler.batch.Main;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/**
 * 这个直接使用ecj提供的main编译方法进行编译
 * @author cango
 *
 */
public class CompilerMainMain {


	public static void main(String[] args) {
		
		MyCompliationProgress compliationProgress = new MyCompliationProgress();
		
		final Map<String, String> settings = new HashMap<String, String>();
		settings.put(CompilerOptions.OPTION_LineNumberAttribute,
				CompilerOptions.GENERATE);
		settings.put(CompilerOptions.OPTION_SourceFileAttribute,
				CompilerOptions.GENERATE);
		settings.put(CompilerOptions.OPTION_ReportDeprecation,
				CompilerOptions.IGNORE);
		
		settings.put(CompilerOptions.OPTION_Source, CompilerOptions.VERSION_1_7); // 1.7选项
		
		Main main = new Main(new PrintWriter(System.out),
				new PrintWriter(System.err), true, settings, compliationProgress);
		
		String[] ars = {"-classpath","rt.jar","C:\\Users\\cango\\Desktop\\000f\\compiler\\test\\HelloJava.java"};
		
		main.compile(ars);
		
//		main.compile("-classpath rt.jar C:\\Users\\cango\\Desktop\\000f\\compiler\\test\\HelloJava.java");

	}

}
