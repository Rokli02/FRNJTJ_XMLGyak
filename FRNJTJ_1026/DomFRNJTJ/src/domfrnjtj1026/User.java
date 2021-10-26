package domfrnjtj1026;

public class User {
	private int id;
	private String firstname;
	private String lastname;
	private String profession;
	
	public User(int id, String firstname, String lastname, String profession) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.profession = profession;
	}
	
	public User() {
		this(-1, "noname", "noname", "novalue");
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	@Override
	public String toString() {
		return "User id: "+id+
				"\nFirst name: "+firstname+
				"\nLast name: "+lastname+
				"\nProfession: "+profession;
	}
	
	
}
