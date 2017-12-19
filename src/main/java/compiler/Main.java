/**
 * 
 */
package compiler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.Compiler;
import org.eclipse.jdt.internal.compiler.DefaultErrorHandlingPolicies;
import org.eclipse.jdt.internal.compiler.ICompilerRequestor;
import org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy;
import org.eclipse.jdt.internal.compiler.IProblemFactory;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.env.INameEnvironment;
import org.eclipse.jdt.internal.compiler.env.NameEnvironmentAnswer;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory;

/**
 * 测试用jdt去编译一个java文件
 * 
 * @author cango
 * 
 */
public class Main {

	static class CompilationUnit implements ICompilationUnit {

		private final String className;
		private final String sourceFile;

		CompilationUnit(String sourceFile, String className) {
			this.className = className;
			this.sourceFile = sourceFile;
		}

		public char[] getFileName() {
			return sourceFile.toCharArray();
		}

		public char[] getContents() {
			char[] result = null;
			FileInputStream is = null;
			InputStreamReader isr = null;
			Reader reader = null;
			try {
				is = new FileInputStream(sourceFile);
				isr = new InputStreamReader(is, "utf-8");
				reader = new BufferedReader(isr);
				char[] chars = new char[8192];
				StringBuilder buf = new StringBuilder();
				int count;
				while ((count = reader.read(chars, 0, chars.length)) > 0) {
					buf.append(chars, 0, count);
				}
				result = new char[buf.length()];
				buf.getChars(0, result.length, result, 0);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException ioe) {/* Ignore */
					}
				}
				if (isr != null) {
					try {
						isr.close();
					} catch (IOException ioe) {/* Ignore */
					}
				}
				if (is != null) {
					try {
						is.close();
					} catch (IOException exc) {/* Ignore */
					}
				}
			}
			return result;
		}

		public char[] getMainTypeName() {
			int dot = className.lastIndexOf('.');
			if (dot > 0) {
				return className.substring(dot + 1).toCharArray();
			}
			return className.toCharArray();
		}

		public char[][] getPackageName() {
			StringTokenizer izer = new StringTokenizer(className, ".");
			char[][] result = new char[izer.countTokens() - 1][];
			for (int i = 0; i < result.length; i++) {
				String tok = izer.nextToken();
				result[i] = tok.toCharArray();
			}
			return result;
		}

		public boolean ignoreOptionalProblems() {
			return false;
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String sourceFile = "C://Users//cango//Desktop//000f//compiler//HelloJava.java";

		String targetClassName = "C://Users//cango//Desktop//000f//compiler//HelloJava.class";

		String[] fileNames = new String[] { sourceFile };
		String[] classNames = new String[] { targetClassName };

		final INameEnvironment env = new INameEnvironment() {

			public NameEnvironmentAnswer findType(char[][] compoundTypeName) {
				// TODO Auto-generated method stub
				return null;
			}

			public NameEnvironmentAnswer findType(char[] typeName,
					char[][] packageName) {
				// TODO Auto-generated method stub
				return null;
			}

			public boolean isPackage(char[][] parentPackageName,
					char[] packageName) {
				// TODO Auto-generated method stub
				return false;
			}

			public void cleanup() {
				// TODO Auto-generated method stub

			}

		};

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

			}

		};

		final IProblemFactory problemFactory = new DefaultProblemFactory(
				Locale.getDefault());

		ICompilationUnit[] compilationUnits = new ICompilationUnit[classNames.length];
		for (int i = 0; i < compilationUnits.length; i++) {
			String className = classNames[i];
			compilationUnits[i] = new CompilationUnit(fileNames[i], className);
		}

		Compiler compiler = new Compiler(env, policy, cOptions, requestor,
				problemFactory);
		compiler.compile(compilationUnits);

	}

}
