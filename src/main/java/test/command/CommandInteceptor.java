/**
 * 
 */
package test.command;

/**
 * @author mymai
 *
 */
public interface CommandInteceptor {
	
	public void setNext(CommandInteceptor next);
	
	public void inteceptor();

}
