/**
 * 
 */
package test.command;

/**
 * @author mymai
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		LogCommandInteceptor logCommandInteceptor = new LogCommandInteceptor();

		TransanctionCommandInteceptor transanctionCommandInteceptor = new TransanctionCommandInteceptor();

		CommandInvoker commandInvoker = new CommandInvoker();

		logCommandInteceptor.setNext(transanctionCommandInteceptor);

		transanctionCommandInteceptor.setNext(commandInvoker);

		logCommandInteceptor.inteceptor();

	}

}
