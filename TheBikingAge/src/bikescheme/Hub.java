/**
 * 
 */
package bikescheme;

import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *  
 * Hub system.
 *
 * 
 * @author pbj
 *
 */
public class Hub implements AddDStationObserver,AddUserObserver {
    public static final Logger logger = Logger.getLogger("bikescheme");

    private HubTerminal terminal;
    private HubDisplay display;
    private Map<String,DStation> dockingStationMap;
    private List<User> users;
    
    /**
     * 
     * Construct a hub system with an operator terminal, a wall display 
     * and connections to a number of docking stations (initially 0). 
     * 
     * Schedule update of the hub wall display every 5 minutes with
     * docking station occupancy data.
     * 
     * @param instanceName
     */
    public Hub() {

        // Construct and make connections with interface devices
        terminal = new HubTerminal("ht");
        terminal.setObserver(this);
        display = new HubDisplay("hd");
        dockingStationMap = new HashMap<String,DStation>();
        users = new ArrayList<User>();
        Clock.createInstance();
        
        // Schedule timed notification for generating updates of 
        // hub display. 

        // The idiom of an anonymous class is used here, to make it easy
        // for hub code to process multiple timed notification, if needed.
         
        Clock.getInstance().scheduleNotification(
                new TimedNotificationObserver() {

                    /** 
                     * Generate dummy display of station occupancy data.
                     */
                    @Override
                    public void processTimedNotification() { //State Occupancy create
                        logger.fine("");

                        String[] occupancyArray = 
                                // "DSName","East","North","Status","#Occupied","#DPoints"
                            {  "A",      "100",  "200",  "HIGH",       "19",     "20",
                               "B",      "300", "-500",   "LOW",        "1",     "50" };

                        List<String> occupancyData = Arrays.asList(occupancyArray);
                        display.showOccupancy(occupancyData);
                    }

                },
                Clock.getStartDate(), 
                0, 
                5);
     
    }

    public void setDistributor(EventDistributor d) {
        
        // The clock device is connected to the EventDistributor here, even
        // though the clock object is not constructed here, 
        // as no distributor is available to the Clock constructor.
        Clock.getInstance().addDistributorLinks(d);
        terminal.addDistributorLinks(d);
    }
    
    public void setCollector(EventCollector c) {
        display.setCollector(c); 
        terminal.setCollector(c);
    }
    

    /**
     * 
     */
    @Override
    public void addDStation(
            String instanceName, 
            int eastPos, 
            int northPos,
            int numPoints) {
        logger.fine("");
        
        DStation newDStation = 
                new DStation(instanceName, eastPos, northPos, numPoints);
        dockingStationMap.put(instanceName, newDStation);
        
        // Now connect up DStation to event distributor and collector.
        
        EventDistributor d = terminal.getDistributor();
        EventCollector c = display.getCollector();
        
        newDStation.setDistributor(d);
        newDStation.setCollector(c);
        newDStation.addObserver(this);
    }
    public void addUser(User user){
    	users.add(user);
    }
    
    public DStation getDStation(String instanceName) {
        return dockingStationMap.get(instanceName);
    }
    public void newUserCreated(User user){
    	users.add(user);
    	//TODO check if user is indeed added
    }
    // method for getting user when he has inserted key
    /* since test may want to get bike with key without registering key, a dummy user object is created for this key if user is not found in regitered users list 
     * in later implementation this will generate an exception instead*/
    public User getUserByKey(String keyID){
    for(User user:users){
    	if(user.getKeyID().equals(keyID)){
    		return user;
    	}
   
    } 
    User dummy = new User("Dummy","dummy cardInfo",keyID);
    return dummy;
    }
    // same comment as above method
    public User getUserByBike(String bikeID){
        for(User user:users){
        	if(user.getKeyID().equals(bikeID)){
        		return user;
        	}
       
        } 
        User dummy = new User("Dummy","dummy cardInfo", "dummyKey");
        return dummy;
        }
    public void bikeTaken(String keyID, String bikeID, String dStation){
    	Date date = Clock.getStartDate();
    	getUserByKey(keyID).takeBike(bikeID, date, dStation);;
    }
    public void bikeReturned (String bikeID, String dStation){
    	Date date = Clock.getStartDate();
    	getUserByBike(bikeID).returnBike(date, dStation);
    }
    public List<String> getActivity(String keyID){
    	User user = getUserByKey(keyID);
    	return user.getActivity();
    }

}
