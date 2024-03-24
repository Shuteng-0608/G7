package test;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import checkinSys.PassengerQueue;
import checkinSys.SharedObject;

import static org.junit.Assert.*;

public class PassengerQueueTest {
    private SharedObject sharedObject;
    private Thread passengerQueueThread;
    private PassengerQueue passengerQueue;

    @Before
    public void setUp() {
        sharedObject = new SharedObject(); // 假设这个对象已经被正确初始化和配置
        // 假设FlightList中已经有乘客数据可供选择
    }

    @Test
    public void testPassengerQueueEconomy1() throws InterruptedException {
        passengerQueue = new PassengerQueue("economy 1", sharedObject);
        passengerQueueThread = new Thread(passengerQueue);
        passengerQueueThread.start();

        // 给线程一些时间来处理任务
        Thread.sleep(200); // 调整此值根据实际情况可能需要

        passengerQueue.queueClose(); // 通知线程关闭
        passengerQueueThread.join(); // 等待线程安全结束

        assertFalse("Queue 1 should not be empty after running", sharedObject.getQueue1().isEmpty());
        assertTrue("Queue 2 should be empty when only queue 1 is being used", sharedObject.getQueue2().isEmpty());
    }

    @Test
    public void testPassengerQueueEconomy2() throws InterruptedException {
        passengerQueue = new PassengerQueue("economy 2", sharedObject);
        passengerQueueThread = new Thread(passengerQueue);
        passengerQueueThread.start();

        // 给线程一些时间来处理任务
        Thread.sleep(200); // 调整此值根据实际情况可能需要

        passengerQueue.queueClose(); // 通知线程关闭
        passengerQueueThread.join(); // 等待线程安全结束

        assertTrue("Queue 1 should be empty when only queue 2 is being used", sharedObject.getQueue1().isEmpty());
        assertFalse("Queue 2 should not be empty after running", sharedObject.getQueue2().isEmpty());
    }

    @After
    public void tearDown() {
        // 清理资源，例如关闭线程等
        if (passengerQueue != null) {
            passengerQueue.queueClose(); // 尝试关闭，如果测试失败
        }
        // 如果需要，可以在这里清空队列等
    }
}
