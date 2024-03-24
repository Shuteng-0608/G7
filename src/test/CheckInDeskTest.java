package test;

import static org.junit.Assert.assertTrue;

import checkinSys.*;
import myExceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class CheckInDeskTest {
	
    private SharedObject sharedObject;
    private CheckInDesk checkInDesk;
    private Thread checkInDeskThread;
	
	@BeforeEach
	void setUp() throws InvalidAttributeException, InvalidBookRefException {
        sharedObject = new SharedObject(); // 初始化共享对象
        checkInDesk = new CheckInDesk("Desk 1", sharedObject); // 初始化CheckInDesk
        checkInDeskThread = new Thread(checkInDesk); // 创建一个线程来运行CheckInDesk
	}

    @Test
    public void testCheckInProcess() {
        // 假设已经有乘客信息和航班信息加载到sharedObject中

        // 创建几个乘客实例并加入队列
        Passenger passenger1 = new Passenger("DXBCA3781807232238", "Diane Brewer", "CA378", "180723", "No", 26.42, 1.1);
        Passenger passenger2 = new Passenger("DXBCA3781807236379", "George Hall", "CA378", "180723", "Yes", 31.98, 1.85);
        sharedObject.addQueue1(passenger1);
        sharedObject.addQueue2(passenger2);

        // 启动CheckInDesk线程
        checkInDeskThread.start();

        try {
            Thread.sleep(100); // 等待足够的时间让CheckInDesk处理乘客
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 检查乘客是否已经被处理（即他们的check_in状态应该被设置为"Yes"）
        assertTrue("Passenger1 has checked in!", passenger1.getCheckin().equals("Yes"));
        assertTrue("Passenger2 has checked in!", passenger2.getCheckin().equals("Yes"));    
        
        // 关闭CheckInDesk以结束线程
        checkInDesk.closeDesk();
        try {
            checkInDeskThread.join(); // 确保线程安全地结束
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
