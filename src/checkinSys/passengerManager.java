package checkinSys;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class passengerManager {
	public HashMap<String, passenger> passengers = new HashMap<>();
    public void loadPassengersFromFile(String filename) {
        // 从文件加载乘客数据
    	try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 解析行数据，创建Passenger对象
            	try {
                    passenger passenger = parsePassenger(line);
                    passengers.put(passenger.getBookingReference(), passenger);
                } catch (invalidBookingException e) {
                    // 处理异常，例如记录错误和继续
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private passenger parsePassenger(String line) throws invalidBookingException {
		// 解析文件内容TODO Auto-generated method stub
    	if (line == null) {
            throw new invalidBookingException("Invalid booking reference found in data.");
        }
		return null;
	}

	public passenger findPassenger(String bookingReference) {
        // 查找乘客
		return null;
    }
}
