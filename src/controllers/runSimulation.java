package controllers;
import controllers.flightManager;
import models.flight;
import threads.queue;

public class runSimulation {
    public static void main(String[] args){
        // 初始化航班管理器
        flightManager flightManager = new flightManager();
        // 添加一些示例航班
        flightManager.addFlight(new flight("AB123", "New York", 200, 15000, 150));
        flightManager.addFlight(new flight("CD456", "London", 150, 10000, 120));

        // 初始化乘客队列管理器
        queue queueManager = new queue(20);
    }


}