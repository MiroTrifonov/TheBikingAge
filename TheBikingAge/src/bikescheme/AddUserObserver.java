package bikescheme;

public interface AddUserObserver {
	void newUserCreated(User user);
	void bikeTaken(String keyID, String bikeID);
	void bikeReturned(String bikeID);
	String getActivity(String keyID);
}
