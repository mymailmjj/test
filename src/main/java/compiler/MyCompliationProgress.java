/**
 * 
 */
package compiler;

import org.eclipse.jdt.core.compiler.CompilationProgress;

/**
 * @author cango
 *
 */
public class MyCompliationProgress extends CompilationProgress {

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.compiler.CompilationProgress#begin(int)
	 */
	@Override
	public void begin(int remainingWork) {

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.compiler.CompilationProgress#done()
	 */
	@Override
	public void done() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.compiler.CompilationProgress#isCanceled()
	 */
	@Override
	public boolean isCanceled() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.compiler.CompilationProgress#setTaskName(java.lang.String)
	 */
	@Override
	public void setTaskName(String name) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.compiler.CompilationProgress#worked(int, int)
	 */
	@Override
	public void worked(int workIncrement, int remainingWork) {
		// TODO Auto-generated method stub

	}

}
