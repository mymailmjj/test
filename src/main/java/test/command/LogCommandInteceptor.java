package test.command;

public class LogCommandInteceptor implements CommandInteceptor {
	
	private CommandInteceptor commandInteceptor;

	public void setNext(CommandInteceptor next) {
		commandInteceptor = next;
	}

	public void inteceptor() {
		System.out.println("beging log start ------------------------");
		
		commandInteceptor.inteceptor();
		
		System.out.println("beging log end ------------------------");


	}

}
