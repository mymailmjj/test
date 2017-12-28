/**
 * 
 */
package lifecycle;

/**
 * 
 * 目前可以用的状态
 * @author cango
 * 
 */
public enum LifeStatus {

	NEW,
	
	BEFORE_INIT,

	START_INIT,

	INITIZING,

	INITIZED,

	BEFORE_START,

	STARTING,

	STARTED,

	BEFORE_DESTROY,

	DESTROYING,

	DESTROYED

}
