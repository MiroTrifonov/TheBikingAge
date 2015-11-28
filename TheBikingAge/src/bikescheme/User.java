package bikescheme;

public class User {
	private String personalDetails;
	private String cardInfo;
	private String keyID;
	private String takenBikeID = null;
	private boolean hasTakenBike = false; // may not be needed 
	private String tripsInformation;
		User (String a, String b, String c){
			this.personalDetails = a;
			this.cardInfo = b;
			this.keyID = c;
		}
		
	public String getPersonalDetails() {
		return personalDetails;
	}
	public String getCardInfo() {
		return cardInfo;
	}
	public boolean gethasTakenBike(){
		return hasTakenBike;
	}
	public String getKeyID(){
		return keyID;
	}
	public String getActivity(){
		return tripsInformation;
	}
	public void takeBike (String bikeID){
		this.hasTakenBike = true;
		this.takenBikeID = bikeID;
		// generate journey information.
	}
	public void returnBike(){
		this.hasTakenBike = false;
		this.takenBikeID = null;
		// finish journery information
	}
	
	
}
