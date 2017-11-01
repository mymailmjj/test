/**
 * 
 */
package cache;

import bo.User;

/**
 * @author mujjiang
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		LruCache<String, User> lruCache = new LruCache<String, User>(20, 3);
		
		for(int i = 0; i < 20; i++){
			User user = new User();
			user.setId(i);
			user.setName("zhangsan" + i);
			lruCache.put("zhangsan" + i, user);
		}
		
		User user = lruCache.get("zhangsan18");
		
		User user1 = lruCache.get("zhangsan15");
		
		User user2 = lruCache.get("zhangsan18");
		
		User user3 = lruCache.get("zhangsan15");
		
			User user4 = lruCache.get("zhangsan18");
			
			User user5 = lruCache.get("zhangsan15");
			
			User user6 = lruCache.get("zhangsan18");
			
			User user7 = lruCache.get("zhangsan1");
			
			user7 = lruCache.get("zhangsan1");
			
			user7 = lruCache.get("zhangsan1");
			
			user7 = lruCache.get("zhangsan1");
			
			user7 = lruCache.get("zhangsan1");
			
			user7 = lruCache.get("zhangsan1");
		
		System.out.println(lruCache);

	}

}
