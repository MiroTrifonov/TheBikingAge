/**
 * 
 */
package bikescheme;

import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;

/**
 *  
 * Docking Point for a Docking Station.
 * 
 * @author pbj
 *
 */
public class DPoint implements KeyInsertionObserver,BikeDockingObserver {
    public static final Logger logger = Logger.getLogger("bikescheme");

    private KeyReader keyReader; 
    private OKLight okLight;
    private String instanceName;
    private int index; 
    private BikeSensor sensor;
    private boolean bikeAvailable = false;
    private Bike bike = null; // or just one bike here ? TODO
 
    /**
     * 
     * Construct a Docking Point object with a key reader and green ok light
     * interface devices.
     * 
     * @param instanceName a globally unique name
     * @param index of reference to this docking point  in owning DStation's
     *  list of its docking points.
     */
    public DPoint(String instanceName, int index) {

     // Construct and make connections with interface devices
        
        keyReader = new KeyReader(instanceName + ".kr");
        keyReader.setObserver(this);
        okLight = new OKLight(instanceName + ".ok");
        this.instanceName = instanceName;
        this.index = index;
        sensor = new BikeSensor("sensor" + ".bs");
        sensor.setObserver(this);
        
    }
       
    public void setDistributor(EventDistributor d) {
        keyReader.addDistributorLinks(d); 
        sensor.addDistributorLinks(d);
    }
    
    public void setCollector(EventCollector c) {
        okLight.setCollector(c);
        
    }
    private DpToDsBikeObserver observer;
    public void addObserver(DpToDsBikeObserver o){
    	observer = o;
    }
    
    public String getInstanceName() {
        return instanceName;
    }
    public int getIndex() {
        return index;
    }
    
    /** 
     * In this method only the main success scenario for give bike is considered. This mean no check if user had already taken bike is implemented - this is to be done at later stage of code implementation 
     * Also the case where Dpoint is empty is not considered
     * 
     */
    public void keyInserted(String keyId) {
    	
    	
        logger.fine(getInstanceName());
        userTakesBike(keyId);
        okLight.flash(); 
    }
    // if Dp is empty error will be generated here. However this is not part of MSS
	public void userTakesBike(String keyID){
		if (bikeAvailable==true){
			//TODO generate even unlock bike,
			Bike unlockedBike = bike;
			this.bike = null;
			String bikeID = unlockedBike.getBikeID();
			observer.userHasTakenBike(keyID, bikeID);
			bikeAvailable = false;
		}
		else{
			//generate error message
		}
		
    }
    public void bikeDocked(String bikeID){
    	observer.userHasReturnedBike(bikeID);
    	Bike retBike = new Bike(bikeID);
    	this.bike = retBike;
    	this.bikeAvailable = true;
    	// generate output lock bike
    }
    public void addBike(String bikeID){
    	this.bike = new Bike(bikeID);
    	this.bikeAvailable = true;
    	// generate output lock bike
    }
    public void staffMemberRemoveBike(String keyId){
    	if(bikeAvailable = true){
    		Bike unlockedBike = bike;
    		this.bike = null;
    		// generate output unlock bike
    		bikeAvailable = false;
    	}
    	else{
    		// generate error message
    	}
    }
    	


}
