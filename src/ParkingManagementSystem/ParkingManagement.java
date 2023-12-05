package ParkingManagementSystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
// 创建ParkingSpot类，表示停车位

class ParkingLot {
    final int totalSpots;
    private int availableSpots;

    public ParkingLot(int totalSpots) {
        this.totalSpots = totalSpots;
        this.availableSpots = totalSpots;
    }

    public boolean isFull() {
        return availableSpots == 0;
    }

    public boolean isEmpty() {
        return availableSpots == totalSpots;
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }
    // 分配停车位
    public boolean assignParkingSpot(){
        if(isFull()){
            return false;
        }
        availableSpots--;
        return true;
    }
    // 释放停车位
    public void releaseParkingSpot() {
        availableSpots++;
    }
}




/**
 * 停车管理系统，负责管理停车场、停车票和停车费用计算。
 */
public class ParkingManagement {
    final ParkingLot parkingLot;
    final List<ParkingTicket> activeTickets; // 用于存储当前停车中的车辆信息

    /**
     * 构造函数，初始化停车管理系统。
     *
     * @param totalSpots 停车场总停车位数
     */
    public ParkingManagement(int totalSpots) {
        this.parkingLot = new ParkingLot(totalSpots);
        this.activeTickets = new ArrayList<>();
    }

    ParkingLot getParkingLot() {
        return parkingLot;
    }

    /**
     * 停车操作，将车辆停入停车场。
     *
     * @param vehicle 车牌号
     * @throws ParkingException 如果停车场已满
     */
    void parkVehicle(String vehicle) throws ParkingException {
        if (parkingLot.assignParkingSpot()) {
            Date entryTime = new Date(); // 记录入场时间
            ParkingTicket ticket = new ParkingTicket(vehicle, entryTime);
            activeTickets.add(ticket); // 添加到停车记录
        } else {
            throw new ParkingException("停车场已满");
        }
    }

    /**
     * 取车操作，将车辆从停车场取出。
     *
     * @param vehicle 车牌号
     * @return 停车票信息
     * @throws ParkingException 如果未找到相关车辆信息
     */
    ParkingTicket exitParking(String vehicle) throws ParkingException {
        ParkingTicket ticket = findParkingTicketByLicensePlate(vehicle);
        if (ticket != null) {
            Date exitTime = new Date(); // 记录出场时间
            ticket.setExitTime(exitTime);
            float totalCost = calculateParkingCost(ticket); // 计算费用
            ticket.setFee(totalCost);
            parkingLot.releaseParkingSpot(); // 释放停车位
            activeTickets.remove(ticket); // 从停车记录中移除
            return ticket;
        } else {
            throw new ParkingException("未找到相关车辆信息");
        }
    }

    /**
     * 根据车牌号查找停车票。
     *
     * @param licensePlate 车牌号
     * @return 停车票信息
     */
    private ParkingTicket findParkingTicketByLicensePlate(String licensePlate) {
        for (ParkingTicket ticket : activeTickets) {
            if (ticket.getLicensePlate().equals(licensePlate)) {
                return ticket;
            }
        }
        return null;
    }

    /**
     * 根据车牌号获取车辆入场时间。
     *
     * @param licensePlate 车牌号
     * @return 车辆入场时间
     * @throws ParkingException 如果未找到车辆有效信息
     */
    Date getEntryTimeByLicensePlate(String licensePlate) throws ParkingException {
        ParkingTicket ticket = findParkingTicketByLicensePlate(licensePlate);
        if (ticket == null) {
            throw new ParkingException("未找到车辆有效信息");
        }
        return ticket.getEntryTime();
    }

    /**
     * 计算停车费用。
     *
     * @param ticket 停车票信息
     * @return 停车费用
     */
    private float calculateParkingCost(ParkingTicket ticket) {
        long entryTime = ticket.getEntryTime().getTime();
        long exitTime = ticket.getExitTime().getTime();
        long durationMillis = exitTime - entryTime;
        double durationHours = durationMillis / (1000.0 * 60.0 * 60.0); // 转换为小时
        double hourlyRate = 5.0; // 假设每小时5美元
        return (float) (durationHours * hourlyRate);
    }

    /**
     * 根据车牌号计算停车费用。
     *
     * @param licensePlate 车牌号
     * @return 停车费用
     * @throws ParkingException 如果未找到车辆信息
     */
    double calculateParkingCostByLicensePlate(String licensePlate) throws ParkingException {
        ParkingTicket ticket = findParkingTicketByLicensePlate(licensePlate);
        if (ticket == null) {
            throw new ParkingException("未找到车辆信息");
        }
        return calculateParkingCost(ticket);
    }
}


