package bikescheme;

import java.util.List;

public interface AddUserObserver {
	void newUserCreated(User user);
	void bikeTaken(String keyID, String bikeID, String dStation);
	void bikeReturned(String bikeID, String dStation);
	List<String> getActivity(String keyID);
}
