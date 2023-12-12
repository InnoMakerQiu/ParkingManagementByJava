package ParkingManagementSystem.model;

import ParkingManagementSystem.controller.FinancialManagement;

import java.util.Calendar;
import java.util.Date;

public class Bill extends ParkingTicket {
    int year;
    int month;
    int day;

    int date;

    public Bill(ParkingTicket ticket) {
        super(ticket.getLicensePlate(), ticket.getEntryTime(),
                ticket.getExitTime(), ticket.getFee());

        // 使用 Calendar 获取年份、月份和日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ticket.getEntryTime());

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1; // 月份从0开始，需要加1
        day = calendar.get(Calendar.DAY_OF_MONTH);

        date = FinancialManagement.calendarToDate(year, month, day);
    }

    public Bill(String licensePlate, Date entryTime, Date exitTime, float fee) {
        super(licensePlate, entryTime, exitTime, fee);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(super.getEntryTime());

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1; // 月份从0开始，需要加1
        day = calendar.get(Calendar.DAY_OF_MONTH);

        date = FinancialManagement.calendarToDate(year, month, day);


    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    int getDay() {
        return day;
    }

    public int getDate() {
        return date;
    }
}
