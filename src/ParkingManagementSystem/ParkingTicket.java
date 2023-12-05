package ParkingManagementSystem;

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


class Bill extends ParkingTicket {
    int year;
    int month;
    int day;

    int date;

    public Bill(ParkingTicket ticket) {
        super(ticket.getLicensePlate(), ticket.getEntryTime(),
                ticket.getExitTime(),ticket.getFee());

        // 使用 Calendar 获取年份、月份和日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ticket.getEntryTime());

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1; // 月份从0开始，需要加1
        day = calendar.get(Calendar.DAY_OF_MONTH);

        date = FinancialManagement.calendarToDate(year, month, day);
    }

    public Bill(String licensePlate, Date entryTime,Date exitTime,float fee){
        super(licensePlate, entryTime,exitTime,fee);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(super.getEntryTime());

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1; // 月份从0开始，需要加1
        day = calendar.get(Calendar.DAY_OF_MONTH);

        date = FinancialManagement.calendarToDate(year, month, day);


    }

    int getYear() {
        return year;
    }

    int getMonth() {
        return month;
    }

    int getDay() {
        return day;
    }

    int getDate() {
        return date;
    }
}

