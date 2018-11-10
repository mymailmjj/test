/**
 * 
 */
package indexfile.utils;

import java.util.UUID;

/**
 * @author az6367
 *
 */
public class KeyGenerator {
	public static String genKey(){
		UUID id=UUID.randomUUID();
        String[] idd=id.toString().split("-");
        return idd[0]+idd[1]+idd[2];
	}
	
	public static void main(String[] args) {
		String genKey = genKey();
		System.out.println(genKey);
	}

}
