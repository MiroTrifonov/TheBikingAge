package bikescheme;

public interface DpToDsBikeObserver {
	 public void userHasTakenBike(String UserID, String keyID);
	 public void userHasReturnedBike(String bikeID);
}
