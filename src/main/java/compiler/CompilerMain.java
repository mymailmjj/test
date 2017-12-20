/**
 * 
 */
package compiler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.Compiler;
import org.eclipse.jdt.internal.compiler.DefaultErrorHandlingPolicies;
import org.eclipse.jdt.internal.compiler.ICompilerRequestor;
import org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy;
import org.eclipse.jdt.internal.compiler.IProblemFactory;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileReader;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFormatException;
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
public class CompilerMain {

	private static final File WORKDIR = new File(
			"C:\\Users\\cango\\Desktop\\000f\\compiler");

	static class MyINameEnvironment implements INameEnvironment {

		public NameEnvironmentAnswer findType(char[][] compoundTypeName) {
			return findType(join(compoundTypeName));
		}

		public NameEnvironmentAnswer findType(char[] typeName,
				char[][] packageName) {
			return findType(join(packageName) + "." + new String(typeName));
		}

		private NameEnvironmentAnswer findType(final String name) {

			System.out.println("findtypename:" + name);

			File file = new File(WORKDIR, name.replace('.', '/') + ".java");

			if (file.isFile()) {

				return new NameEnvironmentAnswer(new CompilationUnit(file),
						null);
			}

			try {

				InputStream input = this.getClass().getClassLoader()
						.getResourceAsStream(name.replace(".", "/") + ".class");

				if (input != null) {

					byte[] bytes = IOUtils.toByteArray(input);

					if (bytes != null) {

						ClassFileReader classFileReader = new ClassFileReader(
								bytes, name.toCharArray(), true);

						return new NameEnvironmentAnswer(classFileReader, null);

					}

				}

			} catch (ClassFormatException e) {

				throw new RuntimeException(e);

			} catch (IOException e) {

				throw new RuntimeException(e);

			}

			return null;

		}

		public boolean isPackage(char[][] parentPackageName, char[] packageName) {
			String name = new String(packageName);
			
			System.out.println("name11:"+name);
			
			if (parentPackageName != null) {

				name = join(parentPackageName) + "." + name;
			}

			File target = new File(WORKDIR, name.replace('.', '/'));
			
			System.out.println("nametarget:"+target.getName());
			
			System.out.println("nametarget:"+target.getAbsolutePath());

			return !target.isFile();
		}

		public void cleanup() {

		}

		private String join(char[][] chars) {

			StringBuilder sb = new StringBuilder();

			for (char[] item : chars) {

				if (sb.length() > 0) {

					sb.append(".");

				}

				sb.append(item);

			}

			return sb.toString();

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

	static class CompilationUnit implements ICompilationUnit {

		private File file;

		public CompilationUnit(File file) {
			this.file = file;
		}

		public char[] getFileName() {
			return file.getName().toCharArray();
		}

		public char[] getContents() {

			try {
				return FileUtils.readFileToString(file).toCharArray();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;

		}

		public char[] getMainTypeName() {
			return file.getName().replace(".java", "").toCharArray();
		}

		public char[][] getPackageName() {

			String absolutePath = this.file.getParentFile().getAbsolutePath();

			System.out.println("absolutePath:\t" + absolutePath);

			String fullPkgName = absolutePath.replace(
					WORKDIR.getAbsolutePath(), "");

			fullPkgName = fullPkgName.replace("/", ".").replace("\\", ".");

			if (fullPkgName.startsWith("."))

				fullPkgName = fullPkgName.substring(1);

			String[] items = fullPkgName.split("[.]");

			char[][] pkgName = new char[items.length][];

			for (int i = 0; i < items.length; i++) {

				pkgName[i] = items[i].toCharArray();

			}

			System.out.println("pkgName:\t" + join(pkgName));

			return pkgName;
		}

		public boolean ignoreOptionalProblems() {
			return false;
		}

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

		final IProblemFactory problemFactory = new DefaultProblemFactory(
				Locale.getDefault());

		Compiler compiler = new Compiler(new MyINameEnvironment(), policy,
				cOptions, requestor, problemFactory);
		compiler.compile(new ICompilationUnit[] { new CompilationUnit(new File(
				WORKDIR, "test\\HelloJava.java")) });

	}

}
