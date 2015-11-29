package bikescheme;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
	private String personalDetails;
	private String cardInfo;
	private String keyID;
	private String takenBikeID = null;
	private boolean hasTakenBike = false; // may not be needed 
	private List<String> tripsInformation= new ArrayList<String>();
	private int charges = 0;
	private Date tripStarted;
	private int tripNo = 0;
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
	public List<String> getActivity(){
		return tripsInformation;
	}
	public void takeBike (String bikeID, Date date, String station){
		Clock clock = Clock.getInstance();
		clock.setDateAndTime(date);
		String dateStr = clock.format(date); // the string date 
		this.tripStarted = date;
		this.hasTakenBike = true;
		this.takenBikeID = bikeID;
		String info = "ordered tuples, "+dateStr+", "+station+", ";
		this.tripsInformation.add(tripNo, info);
	}
	public void returnBike(Date enddate, String station){
		this.hasTakenBike = false;
		this.takenBikeID = null;
		Clock clock = Clock.getInstance();
		//clock.setDateAndTime(enddate);
		//String dateStr = clock.format(enddate);
		int minutes = clock.minutesBetween(tripStarted, enddate);
		this.tripStarted = null;
		String info = this.tripsInformation.get(tripNo);
		info = info+station+", "+String.valueOf(minutes);
		this.tripsInformation.add(tripNo, info);
		this.tripNo++;
		}
	
	
}
