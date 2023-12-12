package ParkingManagementSystem.model;

import java.util.Calendar;
import java.util.Date;

// 创建ParkingTicket类，记录车辆的入场时间、出场时间、费用等信息
public class ParkingTicket {
    final String licensePlate;
    final Date entryTime;
    Date exitTime;
    float fee;

    public ParkingTicket(String licensePlate, Date entryTime) {
        this.licensePlate = licensePlate;
        this.entryTime = entryTime;
    }

    public ParkingTicket(String licensePlate, Date entryTime, Date exitTime, float fee){
        this.licensePlate = licensePlate;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.fee = fee;
    }
    public String getLicensePlate() {
        return licensePlate;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public Date getExitTime() {
        return exitTime;
    }
    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }
    public void setFee(float fee){
        this.fee = fee;
    }
    public float getFee(){
        return fee;
    }

    @Override
    public String toString() {
        return "licensePlate:"+licensePlate+",entryTime:"+entryTime.toString()+",exitTime:"
                +exitTime.toString()+",fee:"+fee;
    }
}


