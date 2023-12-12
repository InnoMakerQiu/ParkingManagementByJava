package ParkingManagementSystem.controller;

import ParkingManagementSystem.model.Bill;
import ParkingManagementSystem.model.ParkingTicket;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;


/**
 * SQLBasedFinancialManagement 类用于管理基于 SQL 的财务数据。
 */
public class SQLBasedFinancialManagement {

    private final Connection connection;

    /**
     * 构造方法，用于初始化数据库连接和创建账单表。
     */
    public SQLBasedFinancialManagement() {
        this.connection = createDatabaseConnection();
        createBillTable();
    }

    /**
     * 创建数据库连接。
     *
     * @return 数据库连接
     */
    private Connection createDatabaseConnection() {
        try {
            Class.forName("org.h2.Driver");
            // 使用随机生成的数据库名称，防止冲突
            String connectionUrl = "jdbc:h2:mem:" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1";
            return DriverManager.getConnection(connectionUrl);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create database connection", e);
        }
    }

    /**
     * 创建账单表。
     */
    private void createBillTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS bills (" +
                            "licensePlate VARCHAR(255), " +
                            "entryTime TIMESTAMP, " +
                            "exitTime TIMESTAMP, " +
                            "fee FLOAT, " +
                            "date INT, " +
                            "PRIMARY KEY (licensePlate, entryTime)" +
                            ")"
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create bill table", e);
        }
    }

    /**
     * 添加账单到数据库。
     *
     * @param bill 要添加的账单对象
     */
    void addBill(Bill bill) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO bills (licensePlate, entryTime, exitTime, fee, date) VALUES (?, ?, ?, ?, ?)"
        )) {
            preparedStatement.setString(1, bill.getLicensePlate());
            preparedStatement.setTimestamp(2, new Timestamp(bill.getEntryTime().getTime()));
            preparedStatement.setTimestamp(3, new Timestamp(bill.getExitTime().getTime()));
            preparedStatement.setFloat(4, bill.getFee());
            preparedStatement.setInt(5, bill.getDate());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取指定车牌号和年份的年度记录。
     *
     * @param licensePlate 车牌号
     * @param year         年份
     * @return 包含年度记录的列表
     */
    List<Bill> getYearlyRecords(String licensePlate, int year) {
        List<Bill> yearlyRecords = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM bills WHERE licensePlate = ? AND YEAR(entryTime) = ?"
        )) {
            preparedStatement.setString(1, licensePlate);
            preparedStatement.setInt(2, year);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Bill bill = createBillFromResultSet(resultSet);
                    yearlyRecords.add(bill);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve yearly records from the database");
        }
        return yearlyRecords;
    }

    /**
     * 获取指定车牌号、年份和月份的月度记录。
     *
     * @param licensePlate 车牌号
     * @param year         年份
     * @param month        月份
     * @return 包含月度记录的列表
     */
    List<Bill> getMonthlyRecords(String licensePlate, int year, int month) {
        List<Bill> monthlyRecords = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM bills WHERE licensePlate = ? AND YEAR(entryTime) = ? AND MONTH(entryTime) = ?"
        )) {
            preparedStatement.setString(1, licensePlate);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, month);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Bill bill = createBillFromResultSet(resultSet);
                    monthlyRecords.add(bill);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve monthly records from the database");
        }
        return monthlyRecords;
    }

    /**
     * 计算指定日期范围内的总收入。
     *
     * @param startYear  开始年份
     * @param startMonth 开始月份
     * @param startDay   开始日期
     * @param endYear    结束年份
     * @param endMonth   结束月份
     * @param endDay     结束日期
     * @return 总收入金额
     */
    double getTotalRevenueForMonthRange(int startYear, int startMonth, int startDay,
                                        int endYear, int endMonth, int endDay) {
        double totalRevenue = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT fee FROM bills WHERE date BETWEEN ? AND ?"
        )) {
            int startDate = FinancialManagement.calendarToDate(startYear, startMonth, startDay);
            int endDate = FinancialManagement.calendarToDate(endYear, endMonth, endDay);
            preparedStatement.setInt(1, startDate);
            preparedStatement.setInt(2, endDate);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    totalRevenue += resultSet.getFloat("fee");
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve total revenue from the database");
        }
        return totalRevenue;
    }

    /**
     * 从 ResultSet 中创建 Bill 对象。
     *
     * @param resultSet 包含查询结果的 ResultSet 对象
     * @return 创建的 Bill 对象
     * @throws SQLException 如果操作数据库时发生异常
     */
    private Bill createBillFromResultSet(ResultSet resultSet) throws SQLException {
        ParkingTicket ticket = new ParkingTicket(
                resultSet.getString("licensePlate"),
                resultSet.getTimestamp("entryTime"),
                resultSet.getTimestamp("exitTime"),
                resultSet.getFloat("fee")
        );
        return new Bill(ticket);
    }

    /**
     * 关闭数据库连接。
     */
    public void closeFinancialManagement() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


