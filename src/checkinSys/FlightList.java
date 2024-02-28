//maintains a list of Flight objects as an ArrayList

import java.util.ArrayList;

public class FlightList {
	// Storage for an arbitrary number of details.
	private ArrayList <Flight> flightList;
	
    /**
     * Perform any initialization for the text file.
     */
    public FlightList()
    {
    	flightList = new ArrayList<Flight>() ;
    }
    
    /**
     * Look up a flight code and return the
     * corresponding flight details.
     * @param flight_code The flight code to be looked up.
     * @return The details corresponding to the flight code, null if none
     */
    public Flight findByCode(String flight_code)
    {
    	for (Flight f : flightList)
    	{
    		if (f.getFlight().equals(flight_code))
    		{
    			return f;
    		}
    	}
    	return null;
    }
    
    /**
     * Add a new set of details to the list
     * @param flight The details of the flight
     */
    public void addFlight(Flight flight) 
    {
    	flightList.add(flight);
    }
    
    /**
     * @return The number of entries currently in the flightList.
     */
    public int getNumberOfEntries()
    {
        return flightList.size();
    }
}
