import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 乘客类
class Passenger {
    private String bookingReference;
    private String name;
    private String flightCode;
    private boolean checkedIn;
    private double luggageWeight;
    private double luggageVolume;

    public Passenger(String bookingReference, String name, String flightCode, boolean checkedIn, double luggageWeight, double luggageVolume) {
        this.bookingReference = bookingReference;
        this.name = name;
        this.flightCode = flightCode;
        this.checkedIn = checkedIn;
        this.luggageWeight = luggageWeight;
        this.luggageVolume = luggageVolume;
    }

    public double getLuggageWeight() {
        return luggageWeight;
    }

    public double getLuggageVolume() {
        return luggageVolume;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public void setLuggageWeight(double luggageWeight) {
        this.luggageWeight = luggageWeight;
    }

    public void setLuggageVolume(double luggageVolume) {
        this.luggageVolume = luggageVolume;
    }

    // 计算超重费用的方法
    public double calculateOverweightFee(double maxWeight, double maxVolume) {
        double weightExcess = Math.max(0, luggageWeight - maxWeight);
        double volumeExcess = Math.max(0, luggageVolume - maxVolume);
        return weightExcess * AirportCheckInSystem2.WEIGHT_FEE_PER_KG + volumeExcess * AirportCheckInSystem2.VOLUME_FEE_PER_UNIT;
    }
}

// 航班类
class Flight {
    private String flightCode;
    private String destination;
    private int maxPassengers;
    private double maxWeight;
    private double maxVolume;

    public Flight(String flightCode, String destination, int maxPassengers, double maxWeight, double maxVolume) {
        this.flightCode = flightCode;
        this.destination = destination;
        this.maxPassengers = maxPassengers;
        this.maxWeight = maxWeight;
        this.maxVolume = maxVolume;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public String getDestination() {
        return destination;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public double getMaxVolume() {
        return maxVolume;
    }
}

// 机场值机系统类
public class AirportCheckInSystem2 {
    public static final double WEIGHT_FEE_PER_KG = 5.0; // 每公斤超重费用
    public static final double VOLUME_FEE_PER_UNIT = 10.0; // 每单位体积超重费用

    private Map<String, Passenger> passengersMap;
    private Map<String, Flight> flightsMap;

    public AirportCheckInSystem2() {
        passengersMap = new HashMap<>();
        flightsMap = new HashMap<>();
    }

    // 从文件中加载乘客信息
    public void loadPassengerData(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 6) {
                passengersMap.put(parts[0], new Passenger(parts[0], parts[1], parts[2], Boolean.parseBoolean(parts[3]), Double.parseDouble(parts[4]), Double.parseDouble(parts[5])));
            }
        }
        reader.close();
    }

    // 从文件中加载航班信息
    public void loadFlightData(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 5) {
                flightsMap.put(parts[0], new Flight(parts[0], parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3]), Double.parseDouble(parts[4])));
            }
        }
        reader.close();
    }

    // 乘客办理登机手续
    public boolean checkInPassenger(String bookingReference, String flightCode, double luggageWeight, double luggageVolume) {
        Passenger passenger = passengersMap.get(bookingReference);
        Flight flight = flightsMap.get(flightCode);

        if (passenger == null || flight == null || passenger.isCheckedIn() || !passenger.getFlightCode().equals(flightCode)) {
            return false; // 无效的乘客或航班，或乘客已办理登机手续或乘客航班不匹配
        }

        // 检查航班是否已满
        int bookedPassengersCount = 0;
        for (Passenger p : passengersMap.values()) {
            if (p.getFlightCode().equals(flightCode)) {
                bookedPassengersCount++;
            }
        }
        if (bookedPassengersCount >= flight.getMaxPassengers()) {
            return false; // 航班已满
        }

        // 检查行李是否超重
        double overweightFee = passenger.calculateOverweightFee(flight.getMaxWeight(), flight.getMaxVolume());

        // 更新乘客信息
        passenger.setCheckedIn(true);
        passenger.setLuggageWeight(luggageWeight);
        passenger.setLuggageVolume(luggageVolume);

        // 生成报告
        generateReport();
        return true;
    }

    // 生成报告方法
    public void generateReport() {
        // 遍历航班列表，统计乘客人数、行李总重量、行李总体积和超重行李费用
        for (Flight flight : flightsMap.values()) {
            int passengerCount = 0;
            double totalWeight = 0.0;
            double totalVolume = 0.0;
            double overweightFee = 0.0;

            for (Passenger passenger : passengersMap.values()) {
                if (passenger.getFlightCode().equals(flight.getFlightCode())) {
                    passengerCount++;
                    totalWeight += passenger.getLuggageWeight();
                    totalVolume += passenger.getLuggageVolume();
                    if (passenger.calculateOverweightFee(flight.getMaxWeight(), flight.getMaxVolume()) > 0) {
                        overweightFee += passenger.calculateOverweightFee(flight.getMaxWeight(), flight.getMaxVolume());
                    }
                }
            }

            // 生成报告
            System.out.println("Flight Code: " + flight.getFlightCode());
            System.out.println("Destination: " + flight.getDestination());
            System.out.println("Passenger Count: " + passengerCount);
            System.out.println("Total Weight: " + totalWeight);
            System.out.println("Total Volume: " + totalVolume);
            System.out.println("Overweight Fee: " + overweightFee);
            System.out.println("Exceeds Capacity: " + (passengerCount > flight.getMaxPassengers()));
            System.out.println("---------------------------------------------");
        }
    }

    public static void main(String[] args) {
        AirportCheckInSystem2 checkInSystem = new AirportCheckInSystem2();
        try {
            checkInSystem.loadPassengerData("C:\\Users\\Python_Gt\\Desktop\\untitled1\\src\\passenger_data.txt");

            checkInSystem.loadFlightData("C:\\Users\\Python_Gt\\Desktop\\untitled1\\src\\flight_data.txt");
            // checkInSystem.checkInPassenger("ABC123", "FL123", 25.0, 1.5); // 示例办理登机手续
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
