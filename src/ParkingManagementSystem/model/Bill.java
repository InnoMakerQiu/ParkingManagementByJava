package ParkingManagementSystem.model;

import ParkingManagementSystem.controller.FinancialManagement;

import java.util.Calendar;
import java.util.Date;

/**
 * Bill 类继承自 ParkingTicket 类，用于表示停车费用账单。
 */
public class Bill extends ParkingTicket {
    int year; // 账单所属的年份
    int month; // 账单所属的月份
    int day; // 账单所属的日期

    int date; // 以整数形式表示的日期（格式：YYYYMMDD）

    /**
     * 构造方法，创建 Bill 的实例，基于给定的 ParkingTicket 对象。
     *
     * @param ticket ParkingTicket 对象，包含车辆停车信息。
     */
    public Bill(ParkingTicket ticket) {
        // 调用父类 ParkingTicket 的构造方法，传递车辆停车信息
        super(ticket.getLicensePlate(), ticket.getEntryTime(),
                ticket.getExitTime(), ticket.getFee());

        // 使用 Calendar 获取年份、月份和日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ticket.getEntryTime());

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1; // 月份从0开始，需要加1
        day = calendar.get(Calendar.DAY_OF_MONTH);

        // 使用 FinancialManagement 的辅助方法将年月日转换为整数形式的日期
        date = FinancialManagement.calendarToDate(year, month, day);
    }

    /**
     * 构造方法，创建 Bill 的实例，基于给定的参数。
     *
     * @param licensePlate 车牌号。
     * @param entryTime    车辆进入停车场的时间。
     * @param exitTime     车辆离开停车场的时间。
     * @param fee          停车费用。
     */
    public Bill(String licensePlate, Date entryTime, Date exitTime, float fee) {
        // 调用父类 ParkingTicket 的构造方法，传递车辆停车信息
        super(licensePlate, entryTime, exitTime, fee);

        // 使用 Calendar 获取年份、月份和日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(super.getEntryTime());

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1; // 月份从0开始，需要加1
        day = calendar.get(Calendar.DAY_OF_MONTH);

        // 使用 FinancialManagement 的辅助方法将年月日转换为整数形式的日期
        date = FinancialManagement.calendarToDate(year, month, day);
    }

    /**
     * 获取账单所属的年份。
     *
     * @return 账单所属的年份。
     */
    public int getYear() {
        return year;
    }

    /**
     * 获取账单所属的月份。
     *
     * @return 账单所属的月份。
     */
    public int getMonth() {
        return month;
    }

    /**
     * 获取账单所属的日期。
     *
     * @return 账单所属的日期。
     */
    int getDay() {
        return day;
    }

    /**
     * 获取以整数形式表示的账单日期。
     *
     * @return 以整数形式表示的账单日期（格式：YYYYMMDD）。
     */
    public int getDate() {
        return date;
    }
}
