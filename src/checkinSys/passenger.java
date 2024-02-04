package checkinSys;

public class passenger {


	private String name;
	private String bookingReference;
    private String flightCode;
    private boolean checkedIn;
    private double weight;
    private double volume;
    private double initialBaggage;
    private double individualBaggageWeight;
    private double individualBaggageVolume;//每人应分配额度
    private double individuaActuallBaggageWeight;
    private double individuaActuallBaggageVolume;//每人实际登记额度
    
    public passenger(String name) {
    this.name = name;	
    	
    }
    /**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the bookingReference
	 */
	public String getBookingReference() {
		return bookingReference;
	}

	/**
	 * @param bookingReference the bookingReference to set
	 */
	public void setBookingReference(String bookingReference) {
		this.bookingReference = bookingReference;
	}

	/**
	 * @return the flightCode
	 */
	public String getFlightCode() {
		return flightCode;
	}

	/**
	 * @param flightCode the flightCode to set
	 */
	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	/**
	 * @return the checkedIn
	 */
	public boolean isCheckedIn() {
		return checkedIn;
	}

	/**
	 * @param checkedIn the checkedIn to set
	 */
	public void setCheckedIn(boolean checkedIn) {
		this.checkedIn = checkedIn;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return the volume
	 */
	public double getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(double volume) {
		this.volume = volume;
	}

	/**
	 * @return the initialBaggage
	 */
	public double getInitialBaggage() {
		return initialBaggage;
	}

	/**
	 * @param initialBaggage the initialBaggage to set
	 */
	public void setInitialBaggage(double initialBaggage) {
		this.initialBaggage = initialBaggage;
	}

	/**
	 * @return the individualBaggageWeight
	 */
	public double getIndividualBaggageWeight() {
		return individualBaggageWeight;
	}

	/**
	 * @param individualBaggageWeight the individualBaggageWeight to set
	 */
	public void setIndividualBaggageWeight(double individualBaggageWeight) {
		this.individualBaggageWeight = individualBaggageWeight;
	}

	/**
	 * @return the individualBaggageVolume
	 */
	public double getIndividualBaggageVolume() {
		return individualBaggageVolume;
	}

	/**
	 * @param individualBaggageVolume the individualBaggageVolume to set
	 */
	public void setIndividualBaggageVolume(double individualBaggageVolume) {
		this.individualBaggageVolume = individualBaggageVolume;
	}

	/**
	 * @return the individuaActuallBaggageWeight
	 */
	public double getIndividuaActuallBaggageWeight() {
		return individuaActuallBaggageWeight;
	}

	/**
	 * @param individuaActuallBaggageWeight the individuaActuallBaggageWeight to set
	 */
	public void setIndividuaActuallBaggageWeight(double individuaActuallBaggageWeight) {
		this.individuaActuallBaggageWeight = individuaActuallBaggageWeight;
	}

	/**
	 * @return the individuaActuallBaggageVolume
	 */
	public double getIndividuaActuallBaggageVolume() {
		return individuaActuallBaggageVolume;
	}

	/**
	 * @param individuaActuallBaggageVolume the individuaActuallBaggageVolume to set
	 */
	public void setIndividuaActuallBaggageVolume(double individuaActuallBaggageVolume) {
		this.individuaActuallBaggageVolume = individuaActuallBaggageVolume;
	}
}
