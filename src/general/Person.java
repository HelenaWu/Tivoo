package general;
public class Person {
	private String myId;
	private String myName;
	private String myEmail;
	private String myPhoneNumber;

	public Person(String id, String name, String email, String phoneNumber) {
		myId = id;
		myName = name;
		myEmail = email;
		myPhoneNumber = phoneNumber;
	}

	public String getID() {
		return myId;
	}

	public String getName() {
		return myName;
	}

	public String getEmail() {
		return myEmail;
	}

	public String getPhoneNumber() {
		return myPhoneNumber;
	}
	
	public String toString() {
	    return myId + "\n" + myName + "\n" + myEmail + "\n" + myPhoneNumber;
	}


}
