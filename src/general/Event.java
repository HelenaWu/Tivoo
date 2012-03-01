package general;
import org.joda.time.*;

import java.util.*;

public class Event {
    
	private String myTitle;
	private String mySummary;
	private String myDescription;
	private Person myContactInfo;
	private DateTime myDateTime;
	private DateTime myEndTime;
	private String myLink;
	private String myCostOfEvent;
	private HashMap<String, String> myAdditionalInfo;

	public Event(String title, String summary, String description, Person contact, DateTime datetime, DateTime endTime, String link, String cost, HashMap<String,String> additionalInfo) {
	    myTitle = title;
		mySummary = summary;
		myDescription = description;
		myContactInfo = contact;
		myDateTime = datetime;
		myEndTime = endTime;
		myLink = link;
		myCostOfEvent = cost;
		myAdditionalInfo = additionalInfo;
	}

	
	public String getTitle() {
		return myTitle;
	}

	public String getSummary() {
		return mySummary;
	}


	public String getDescription() {
		return myDescription;
	}


	public Person getContactInfo() {
		return myContactInfo;
	}

	public DateTime getDateTime() {
		return myDateTime;
	}

    public DateTime getEndTime() {
        return myEndTime;
    }
    
	public String getLink() {
		return myLink;
	}

	public String getCostOfEvent() {
		return myCostOfEvent;
	}
	
	public HashMap<String,String> getAdditionalInfo() {
	    return myAdditionalInfo;
	}
	
	public void addAdditionalInfo(String label, String description) {
	    myAdditionalInfo.put(label, description);
	}
	
	public String toString() {
	    String printString = myTitle + "\n" + mySummary + "\n" + myDescription + "\n" + 
	            myContactInfo.toString() + "\n" + myDateTime + "\n" + myEndTime + "\n" + myLink + "\n" +  
	            myCostOfEvent;
	    for (String key: myAdditionalInfo.keySet()) {
	        printString += "\n" + myAdditionalInfo.get(key);
	    }
	    return printString.toLowerCase();
	}

}
