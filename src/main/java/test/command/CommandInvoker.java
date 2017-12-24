/**
 * 
 */
package test.command;

/**
 * @author mymai
 *
 */
public class CommandInvoker implements CommandInteceptor,Command {
	
	private CommandInteceptor commandInteceptor = null;

	/* (non-Javadoc)
	 * @see test.command.CommandInteceptor#setNext(test.command.CommandInteceptor)
	 */
	public void setNext(CommandInteceptor next) {

	}

	/* (non-Javadoc)
	 * @see test.command.CommandInteceptor#inteceptor()
	 */
	public void inteceptor() {
		execute();

	}

	public void execute() {
		System.out.println("执行命令");
		System.out.println("-----------------");
	}

}
