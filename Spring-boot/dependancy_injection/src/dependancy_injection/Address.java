package dependancy_injection;

public class Address {
	public String name;
	private String country;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public Address(String name, String country) {
		super();
		this.name = name;
		this.country = country;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Address() {
	}
	
}
