/**
 * 
 */
package compiler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.Compiler;
import org.eclipse.jdt.internal.compiler.DefaultErrorHandlingPolicies;
import org.eclipse.jdt.internal.compiler.ICompilerRequestor;
import org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy;
import org.eclipse.jdt.internal.compiler.IProblemFactory;
import org.eclipse.jdt.internal.compiler.batch.CompilationUnit;
import org.eclipse.jdt.internal.compiler.batch.FileSystem;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory;

/**
 * 
 * 测试用jdt去编译一个java文件 这里使用原始的方法去编译
 * 这个计划没有成功，需要继续
 * 
 * @author cango
 * 
 */
public class CompilerMain {

	private static final File WORKDIR = new File(
			"C:\\Users\\cango\\Desktop\\000f\\compiler");

	static class MyINameEnvironment extends FileSystem {

		private static Classpath[] classpaths;

		static {
			initclasspath();
		}

		private static void initclasspath() {

			String classProp = System.getProperty("java.class.path")+File.pathSeparator+System.getenv("CLASSPATH");
			
			StringTokenizer tokenizer = new StringTokenizer(classProp,
					File.pathSeparator);

			int countTokens = tokenizer.countTokens();

			classpaths = new Classpath[countTokens];

			int i = 0;

			String token;
			while (tokenizer.hasMoreTokens()) {
				token = tokenizer.nextToken();
				FileSystem.Classpath currentClasspath = FileSystem
						.getClasspath(token, "utf-8", null);
				if (currentClasspath != null) {
					classpaths[i++] = currentClasspath;
				}
			}

//			classpaths[countTokens] =  
			
			System.out.println("classProp:" + classProp);
			
		}

		protected MyINameEnvironment(String[] initialFileNames) {
			super(classpaths, initialFileNames);
		}
	}

	private static String join(char[][] chars) {

		StringBuilder sb = new StringBuilder();

		for (char[] item : chars) {

			if (sb.length() > 0) {

				sb.append(".");

			}

			sb.append(item);

		}

		return sb.toString();

	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final IErrorHandlingPolicy policy = DefaultErrorHandlingPolicies
				.proceedWithAllProblems();

		final Map<String, String> settings = new HashMap<String, String>();
		settings.put(CompilerOptions.OPTION_LineNumberAttribute,
				CompilerOptions.GENERATE);
		settings.put(CompilerOptions.OPTION_SourceFileAttribute,
				CompilerOptions.GENERATE);
		settings.put(CompilerOptions.OPTION_ReportDeprecation,
				CompilerOptions.IGNORE);

		settings.put(CompilerOptions.OPTION_Source, CompilerOptions.VERSION_1_7); // 1.7选项

		CompilerOptions cOptions = new CompilerOptions(settings);
		cOptions.parseLiteralExpressionsAsConstants = true;

		final ICompilerRequestor requestor = new ICompilerRequestor() {

			public void acceptResult(CompilationResult result) {

				if (result.hasErrors()) {

					for (IProblem problem : result.getErrors()) {

						String className = new String(
								problem.getOriginatingFileName()).replace("/",
								".");

						className = className.substring(0,
								className.length() - 5);

						String message = problem.getMessage();

						if (problem.getID() == IProblem.CannotImportPackage) {

							message = problem.getArguments()[0]
									+ " cannot be resolved";

						}

						throw new RuntimeException(className + ":" + message);

					}

				}

			}

		};

		
		File file = new File("C://Users//cango//Desktop//000f//compiler//test//HelloJava.java");
		
		System.out.println(file.getName());
		
		final IProblemFactory problemFactory = new DefaultProblemFactory(
				Locale.getDefault());

		MyINameEnvironment myINameEnvironment = new MyINameEnvironment(new String[]{file.getAbsolutePath()});
		
		Compiler compiler = new Compiler(myINameEnvironment, policy,
				cOptions, requestor, problemFactory);
		
		String canonicalPath = null;
		try {
			canonicalPath = file.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		CompilationUnit CompilationUnit = new CompilationUnit(null, file.getAbsolutePath(), null, canonicalPath, true);
		
		compiler.compile(new ICompilationUnit[] { CompilationUnit});

	}

}
