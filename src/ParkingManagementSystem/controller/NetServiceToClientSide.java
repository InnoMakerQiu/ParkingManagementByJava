package ParkingManagementSystem.controller;

import ParkingManagementSystem.model.Bill;
import ParkingManagementSystem.model.ParkingLot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.List;

/**
 * NetServiceToClientSide 类实现了 Runnable 接口，用于创建与客户端通信的服务器。
 */
public class NetServiceToClientSide implements Runnable {
    int port; // 服务器监听的端口号
    ParkingManagement vehicleProcessor; // 处理车辆的 ParkingManagement 实例
    ServerSocket serverSocket; // 服务器套接字
    final SQLBasedFinancialManagement financialManager; // 管理财务记录的 FinancialManagement 实例
    final NetServiceToPeripheralDevice netWork; // 与外部设备通信的 NetServiceToPeripheralDevice 实例
    private boolean flag; // 控制服务器运行状态的标志

    /**
     * NetServiceToClientSide 的构造方法。
     *
     * @param port              服务器监听的端口号。
     * @param vehicleProcessor 处理车辆的 ParkingManagement 实例。
     * @param financialManager 管理财务记录的 FinancialManagement 实例。
     * @param netWork           与外部设备通信的 NetServiceToPeripheralDevice 实例。
     */
    public NetServiceToClientSide(int port, ParkingManagement vehicleProcessor,
                                  SQLBasedFinancialManagement financialManager,
                                  NetServiceToPeripheralDevice netWork) {
        this.port = port;
        this.vehicleProcessor = vehicleProcessor;
        this.financialManager = financialManager;
        this.netWork = netWork;
        this.flag = true;
        try {
            // 创建服务器套接字并输出启动信息
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务器已启动，正在监听端口：" + port);
            this.serverSocket = serverSocket;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止服务器的方法。
     */
    public void stop() {
        flag = false;
        netWork.stop();
        financialManager.closeFinancialManagement();
    }

    /**
     * Runnable 接口的 run 方法实现。等待客户端连接并通过调用 handleClient 处理连接。
     */
    public void run() {
        while (flag) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("客户端已连接：" + clientSocket.getInetAddress());
                handleClient(clientSocket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理与客户端的通信的方法。
     *
     * @param clientSocket 与客户端通信的套接字。
     */
    private void handleClient(Socket clientSocket) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String message;
            while ((message = reader.readLine()) != null) {
                processMessage(message, writer);
            }
        } catch (IOException e) {
            System.out.println("无效的断开连接");
        }
        System.out.println("客户端已断开连接");
    }

    /**
     * 处理从客户端接收到的消息，假定消息格式为 "Type:data"。
     *
     * @param message 接收到的来自客户端的消息。
     * @param writer  用于向客户端发送响应的 PrintWriter。
     */
    private void processMessage(String message, PrintWriter writer) {
        // 解析请求并处理
        System.out.println("从客户端接收到消息：" + message); // 用于调试的输出
        String[] parts = message.split(":");
        if (parts.length == 2 && parts[0].equals("QUERY_ENTRY_TIME")) {
            String licensePlate = parts[1];
            try {
                Date entryTime = vehicleProcessor.getEntryTimeByLicensePlate(licensePlate);
                writer.println(entryTime.toString());
            } catch (Exception pe) {
                writer.println("未找到车辆");
            }
        } else if (parts.length == 2 && parts[0].equals("QUERY_TOTAL_REVENUE")) {
            String[] timeParts = parts[1].split(",");
            String startTime = timeParts[0];
            String endTime = timeParts[1];
            int[] startValues = convertStringArrayToIntArray(startTime.split("/"));
            int[] endValues = convertStringArrayToIntArray(endTime.split("/"));

            // 调用函数并发送数据
            double totalRevenue = financialManager.getTotalRevenueForMonthRange(
                    startValues[0], startValues[1], startValues[2],
                    endValues[0], endValues[1], endValues[2]);

            // 直接发送数据
            writer.println("TOTAL_REVENUE:" + totalRevenue);
        } else if (parts.length == 2 && parts[0].equals("QUERY_VEHICLE_REVENUE")) {
            String text;
            StringBuilder texts = new StringBuilder(); // 存储账单信息的列表

            String[] parts2 = parts[1].split(",");
            String license = parts2[0];
            String number = parts2[1];
            int endValues = Integer.parseInt(number);
            Date end, start;
            if (endValues > 100) {
                List<Bill> yearlyRecords = financialManager.getYearlyRecords(license, endValues);
                for (Bill bill : yearlyRecords) {
                    float revenue = bill.getFee();
                    start = bill.getEntryTime();
                    end = bill.getExitTime();
                    text = license + "," + start + "," + end + "," + revenue;
                    texts.append(text).append("+");
                }
            } else if (endValues < 20) {
                List<Bill> monthlyRecords = financialManager.getMonthlyRecords(license, 2023, endValues);
                for (Bill bill : monthlyRecords) {
                    start = bill.getEntryTime();
                    end = bill.getExitTime();
                    float revenue = bill.getFee();
                    text = license + "," + start + "," + end + "," + revenue;
                    texts.append(text).append("+");
                }
            }
            System.out.println(texts);
            writer.println(texts);
        } else if (parts[0].equals("QUERY_PARKING_LOT")) {
            ParkingLot parkingLot = vehicleProcessor.getParkingLot();
            int totalParkingSpaces = parkingLot.getTotalSpots();
            int availableSpaces = parkingLot.getAvailableSpots();
            writer.println(totalParkingSpaces + "," + availableSpaces);
        } else if (parts.length == 3) { // 这是密码验证
            String account = parts[1].split(",")[0];
            String password = parts[2];
            if (account.equals("1") && password.equals("1")) {
                writer.println("passed");
            } else {
                writer.println("no passed");
            }
        } else if (parts[0].equals("关闭停车系统。")) {
            stop();
        } else {
            writer.println("无效的请求");
        }
    }

    // 将字符串数组转换为整数数组的方法
    private static int[] convertStringArrayToIntArray(String[] stringArray) {
        int length = stringArray.length;
        int[] intArray = new int[length];

        for (int i = 0; i < length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i]);
        }

        return intArray;
    }
}
