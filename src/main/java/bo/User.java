/**
 * 
 */
package bo;

/**
 * @author mujjiang
 *
 */
public class User {
	
	private int id;
	
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", " + (name != null ? "name=" + name : "")
				+ "]";
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
