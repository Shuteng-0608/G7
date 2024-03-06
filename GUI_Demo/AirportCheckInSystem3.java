import java.util.ArrayList;
import java.util.List;

public class AirportCheckInSystem3 {
    private List<RegistrationDesk> registrationDesks;

    public AirportCheckInSystem3(int numberOfDesks) {
        registrationDesks = new ArrayList<>();
        for (int i = 0; i < numberOfDesks; i++) {
            registrationDesks.add(new RegistrationDesk());
        }
    }

    public void checkInPassenger(String name, String reservationNumber) {
        for (RegistrationDesk desk : registrationDesks) {
            if (desk.checkIn(name, reservationNumber)) {
                return;
            }
        }
        System.out.println("No available desk for check-in.");
    }

    public void processLuggageCheck(double weight, double volume) {
        for (RegistrationDesk desk : registrationDesks) {
            desk.processLuggage(weight, volume);
        }
    }
}

class RegistrationDesk {
    private boolean checkedIn;
    private String passengerName;
    private String reservationNumber;

    public boolean checkIn(String name, String reservationNumber) {
        if (!checkedIn && this.reservationNumber.equals(reservationNumber)) {
            this.passengerName = name;
            this.reservationNumber = reservationNumber;
            this.checkedIn = true;
            System.out.println(name + " checked in successfully.");
            return true;
        }
        return false;
    }

    public void processLuggage(double weight, double volume) {
        if (!checkedIn) {
            System.out.println("Please check in before processing luggage.");
            return;
        }
        if (weight > 23.0 || volume > 1.5) {
            System.out.println("Payment required for excess luggage.");
        } else {
            System.out.println("Luggage checked successfully.");
        }
    }
}
