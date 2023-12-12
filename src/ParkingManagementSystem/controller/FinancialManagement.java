package ParkingManagementSystem.controller;

import ParkingManagementSystem.model.Bill;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * FinancialManagement 类用于管理财务相关信息，包括账单的记录和查询。
 */
public class FinancialManagement {

    private final List<Bill> bills; // 存储账单信息的列表
    final File filePath;

    /**
     * FinancialManagement 类的构造方法，用于初始化账单列表。
     */
    public FinancialManagement(File dataFilePath){
        this.bills = new ArrayList<>();
        filePath = dataFilePath;
        loadBillsFromFile();
    }

    /**
     * 从文件加载账单数据。
     */
    private void loadBillsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Bill bill = createBillFromString(line);
                if (bill != null) {
                    bills.add(bill);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions (e.g., file not found)
        }
    }

    /**
     * 从字符串创建 Bill 对象。
     *
     * @param line 包含账单信息的字符串
     * @return Bill 对象，如果解析失败返回 null
     */
    private Bill createBillFromString(String line) {
        try {
            String[] parts = line.split(",");
            String licensePlate = parts[0].substring(parts[0].indexOf(":") + 1);
            String entryTimeStr = parts[1].substring(parts[1].indexOf(":") + 1);
            String exitTimeStr = parts[2].substring(parts[2].indexOf(":") + 1);
            float fee = Float.parseFloat(parts[3].substring(parts[3].indexOf(":") + 1));
            SimpleDateFormat defaultFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            Date entryTime = defaultFormat.parse(entryTimeStr);
            Date exitTime = defaultFormat.parse(exitTimeStr);
            return new Bill(licensePlate, entryTime, exitTime, fee);
        } catch (ParseException | NumberFormatException e) {
            // Handle parsing errors
            return null;
        }
    }



    /**
     * 将账单列表保存到文件。
     */
    private void saveBillsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Bill bill : bills) {
                writer.write(bill.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            // Handle exceptions (e.g., file not found)
        }
    }

    /**
     * 添加账单到账单列表。
     *
     * @param bill 要添加的账单对象
     */
    void addBill(Bill bill) {
        bills.add(bill);
    }

    /**
     * 根据车牌号和年份查询一年内的消费记录。
     *
     * @param licensePlate 车牌号
     * @param year         年份
     * @return 包含消费记录的列表
     */
    List<Bill> getYearlyRecords(String licensePlate, int year) {
        List<Bill> yearlyRecords = new ArrayList<>();
        for (Bill bill : bills) {
            if (bill.getLicensePlate().equals(licensePlate) && bill.getYear() == year) {
                yearlyRecords.add(bill);
            }
        }
        return yearlyRecords;
    }

    /**
     * 根据车牌号、年份和月份查询一个月内的消费记录。
     *
     * @param licensePlate 车牌号
     * @param year         年份
     * @param month        月份
     * @return 包含消费记录的列表
     */
    List<Bill> getMonthlyRecords(String licensePlate, int year, int month) {
        List<Bill> monthlyRecords = new ArrayList<>();
        for (Bill bill : bills) {
            if (bill.getLicensePlate().equals(licensePlate) && bill.getYear() == year
                    && bill.getMonth() == month) {
                monthlyRecords.add(bill);
            }
        }
        return monthlyRecords;
    }

    /**
     * 生成指定月份范围内的总车辆入库营收。
     *
     * @param startYear  开始年份
     * @param startMonth 开始月份
     * @param startDay   开始日期
     * @param endYear    结束年份
     * @param endMonth   结束月份
     * @param endDay     结束日期
     * @return 总车辆入库营收
     */
    double getTotalRevenueForMonthRange(int startYear, int startMonth,
                                               int startDay, int endYear, int endMonth, int endDay) {
        double totalRevenue = 0;
        for (Bill bill : bills) {
            int billDate = bill.getDate();
            if (isWithinDayRange(billDate, calendarToDate(startYear, startMonth, startDay),
                    calendarToDate(endYear, endMonth, endDay))) {
                totalRevenue += bill.getFee();
            }
        }
        return totalRevenue;
    }

    /**
     * 将年、月、日转换为整数形式，例如yyyyMMdd。
     *
     * @param year  年份
     * @param month 月份
     * @param day   日期
     * @return 整数形式的日期
     */
    public static int calendarToDate(int year, int month, int day) {
        return year * 10000 + month * 100 + day;
    }

    /**
     * 辅助方法，判断目标日期是否在指定的时间范围内。
     *
     * @param targetDate 目标日期
     * @param startDate  开始日期
     * @param endDate    结束日期
     * @return 如果目标日期在范围内，返回 true；否则返回 false
     */
    public static boolean isWithinDayRange(int targetDate, int startDate, int endDate) {
        // 将日期转换为整数形式，例如yyyyMMdd
        // 使用 if 语句判断目标日期是否在范围内
        return targetDate >= startDate && targetDate <= endDate;
    }

    void closeFinancialManagement(){
        saveBillsToFile();
    }
}

