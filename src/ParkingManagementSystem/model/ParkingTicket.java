package ParkingManagementSystem.model;

import java.util.Calendar;
import java.util.Date;

// 创建ParkingTicket类，记录车辆的入场时间、出场时间、费用等信息
import java.util.Date;

/**
 * ParkingTicket 类表示停车券，包含车辆的停车信息和费用。
 */
public class ParkingTicket {
    final String licensePlate; // 车牌号
    final Date entryTime; // 车辆进入停车场的时间
    Date exitTime; // 车辆离开停车场的时间
    float fee; // 停车费用

    /**
     * 构造方法，创建 ParkingTicket 的实例，仅包含车辆的车牌号和进入停车场的时间。
     *
     * @param licensePlate 车牌号。
     * @param entryTime    车辆进入停车场的时间。
     */
    public ParkingTicket(String licensePlate, Date entryTime) {
        this.licensePlate = licensePlate;
        this.entryTime = entryTime;
    }

    /**
     * 构造方法，创建 ParkingTicket 的实例，包含车辆的车牌号、进入停车场的时间、离开停车场的时间和停车费用。
     *
     * @param licensePlate 车牌号。
     * @param entryTime    车辆进入停车场的时间。
     * @param exitTime     车辆离开停车场的时间。
     * @param fee          停车费用。
     */
    public ParkingTicket(String licensePlate, Date entryTime, Date exitTime, float fee) {
        this.licensePlate = licensePlate;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.fee = fee;
    }

    /**
     * 获取车牌号。
     *
     * @return 车牌号。
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * 获取车辆进入停车场的时间。
     *
     * @return 车辆进入停车场的时间。
     */
    public Date getEntryTime() {
        return entryTime;
    }

    /**
     * 获取车辆离开停车场的时间。
     *
     * @return 车辆离开停车场的时间。
     */
    public Date getExitTime() {
        return exitTime;
    }

    /**
     * 设置车辆离开停车场的时间。
     *
     * @param exitTime 车辆离开停车场的时间。
     */
    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    /**
     * 设置停车费用。
     *
     * @param fee 停车费用。
     */
    public void setFee(float fee) {
        this.fee = fee;
    }

    /**
     * 获取停车费用。
     *
     * @return 停车费用。
     */
    public float getFee() {
        return fee;
    }

    /**
     * 重写 toString 方法，返回 ParkingTicket 对象的字符串表示形式。
     *
     * @return 字符串表示形式，包含车牌号、进入时间、离开时间和费用。
     */
    @Override
    public String toString() {
        return "licensePlate:" + licensePlate + ",entryTime:" + entryTime.toString() + ",exitTime:"
                + exitTime.toString() + ",fee:" + fee;
    }
}


