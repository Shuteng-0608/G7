package checkinSys;

import java.io.IOException;

import myExceptions.InvalidAttributeException;
import myExceptions.InvalidBookRefException;

public class testFun {

	public static void main(String[] args) throws InvalidAttributeException, IOException, InvalidBookRefException {
		// TODO Auto-generated method stub
		Manager m = new Manager();
		Flight flight = new Flight();
		flight = m.getFlightList().getFlight(1);
		System.out.println(flight.numberOfCheckIn());
		System.out.println(flight.totalWeight());
		System.out.println(flight.totalFees());
	}

}
