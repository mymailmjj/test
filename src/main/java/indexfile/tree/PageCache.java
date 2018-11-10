/**
 * 
 */
package indexfile.tree;

import java.util.HashMap;
import java.util.Map;

import indexfile.Page;


/**
 * @author az6367
 *
 */
public class PageCache {
	
	private static HashMap<Long,Page> caches = new HashMap<>();
	
	public static void addNode(Long id,Page page){
		caches.put(id, page);
	}
	
	public static Page getNode(Long id){
		return caches.get(id);
	}
	
	public static boolean containsKey(Long id){
		return caches.containsKey(id);
	}

}
