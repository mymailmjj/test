/**
 * 
 */
package test.command;

/**
 * @author mymai
 *
 */
public class TransanctionCommandInteceptor implements CommandInteceptor {
	
	private CommandInteceptor commandInteceptor;

	/* (non-Javadoc)
	 * @see test.command.CommandInteceptor#setNext(test.command.CommandInteceptor)
	 */
	public void setNext(CommandInteceptor next) {
		commandInteceptor = next;

	}

	/* (non-Javadoc)
	 * @see test.command.CommandInteceptor#inteceptor()
	 */
	public void inteceptor() {
		System.out.println("beging trasanction start ------------------------");
		
		commandInteceptor.inteceptor();
		
		System.out.println("beging trasanction end ------------------------");

	}

}
