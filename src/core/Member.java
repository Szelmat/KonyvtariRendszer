package core;

public class Member {
	private int id = 0;
	private String name;
	private String address;
	private String memberType;
	private String contact;

//getters	
	public int getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public String getAddress() {
		return this.address;
	}
	public String getMemberType() {
		return this.memberType;
	}
	
	public String getContact() {
		return this.contact;
	}
	
//setters	
	public void setId(int identifier) {
		this.id = identifier;
	}
	public void setName(String n) {
		this.name = n;
	}
	public void setAddress(String ad) {
		this.address = ad;
	}
	public void setMemberType(String type) {
		this.memberType = type;
	}
	
	public void setContact(String con) {
		this.contact = con;
	}
}
