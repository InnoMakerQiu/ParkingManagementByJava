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

import ParkingManagementSystem.model.VIPUsers;
import org.json.*;


/**
 * NetServiceToClientSide 类实现了 Runnable 接口，用于创建与客户端通信的服务器。
 */
public class NetServiceToClientSide implements Runnable {
    int port; // 服务器监听的端口号
    ParkingManagement vehicleProcessor; // 处理车辆的 ParkingManagement 实例
    ServerSocket serverSocket; // 服务器套接字
    final VIPUsers vipUsers; //管理vip用户实例
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
                                  NetServiceToPeripheralDevice netWork,VIPUsers vipUsers) {
        this.port = port;
        this.vehicleProcessor = vehicleProcessor;
        this.financialManager = financialManager;
        this.netWork = netWork;
        this.flag = true;
        this.vipUsers = vipUsers;
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
     * 处理从客户端接收到的消息,消息一般采用json格式，假定消息格式为
     * {TYPE:TYPE,DATA:DATA}
     * 现在需要处理的数据消息格式有
     * 接收到{TYPE:QUERY_ENTER_TIME,DATA:Date}
     * 发送{TYPE:ENTRY_TIME,DATA:entryTime}
     * 接受到{TYPE:QUERY_TOTAL_REVENUE,DATA:[startTime,endTime]}
     * 发送{TYPE:TOTAL_REVENUE,DATA:totalRevenue}
     * 接收到{TYPE:QUERY_VEHICLE_REVENUE,DATA:licensePlate+","+IntValue}
     * 发送{TYPE:VEHICLE_REVENUE,DATA:billList}
     * 接收到{TYPE:QUERY_PARKING_LOT}
     * 发送{TYPE:PARKING_LOT,DATA:totalParkingSpaces+","+availableSpaces}
     * 接受到{TYPE:ADD_VIP_USER,DATA:licensePlate+","+amount}
     * 发送{TYPE:SUCCESS}
     * 接受到{TYPE:SHOW_VIP_USERS}
     * 发送{TYPE:VIP_USERS,DATA:vipUsersList}
     * 接受到{TYPE:AUTHENTICATION,DATA:account+","+password}
     * 发送{TYPE:PASS}OR{TYPE:NO_PASS}
     * 接受到{TYPE:SHUT_DOWN}
     * 不发送
     * 如果数据格式不匹配{TYPE:INVALID_REQUEST}
     *
     * @param message 接收到的来自客户端的消息。
     * @param writer  用于向客户端发送响应的 PrintWriter。
     */
    private void processMessage(String message, PrintWriter writer) {
        // 解析请求并处理
        System.out.println("从客户端接收到消息：" + message); // 用于调试的输出
        try {
            JSONObject jsonObject = new JSONObject(message);
            String type = jsonObject.getString("TYPE");

            //根据不同类型来执行不同逻辑
            if (type.equals("QUERY_ENTRY_TIME")) {
                Object dataObject = jsonObject.get("DATA");
                String licensePlate = dataObject.toString();
                try {
                    Date entryTime = vehicleProcessor.getEntryTimeByLicensePlate(licensePlate);

                    JSONObject sendMessage = new JSONObject();
                    sendMessage.put("TYPE", "ENTRY_TIME");
                    sendMessage.put("DATA", entryTime);
                    writer.println(sendMessage);
                } catch (Exception pe) {
                    writer.println("{\"TYPE\":\"FAILURE\"}");
                }
            }
            else if (type.equals("QUERY_TOTAL_REVENUE")) {
                try{
                    Object dataObject = jsonObject.get("DATA");
                    JSONArray dataArray = (JSONArray) dataObject;
                    String startTime = dataArray.getString(0);
                    String endTime = dataArray.getString(1);
                    int[] startValues = convertStringArrayToIntArray(startTime.split("/"));
                    int[] endValues = convertStringArrayToIntArray(endTime.split("/"));

                    double totalRevenue = financialManager.getTotalRevenueForMonthRange(
                            startValues[0], startValues[1], startValues[2],
                            endValues[0], endValues[1], endValues[2]);

                    // 直接发送数据
                    JSONObject sendMessage = new JSONObject();
                    sendMessage.put("TYPE","TOTAL_REVENUE");
                    sendMessage.put("DATA",totalRevenue);

                    writer.println(sendMessage);
                }catch(Exception e){
                    writer.println("{\"TYPE\":\"FAILURE\"}");
                }
            }
            else if (type.equals("QUERY_VEHICLE_REVENUE")) {
                Object dataObject = jsonObject.get("DATA");

                String[] parts2 = dataObject.toString().split(",");
                String license = parts2[0];
                String number = parts2[1];
                int endValues = Integer.parseInt(number);
                List<Bill> records = null;
                if (endValues > 100) {
                    records = financialManager.getYearlyRecords(license, endValues);

                } else if (endValues < 20) {
                    records = financialManager.getMonthlyRecords(license, 2023, endValues);
                }
                try{
                    JSONArray jsonArray = new JSONArray(records);
                    JSONObject sendMessage = new JSONObject();
                    sendMessage.put("TYPE","VEHICLE_REVENUE");
                    sendMessage.put("DATA",jsonArray);
                    writer.println(records);
                }catch(Exception e){
                    writer.println("{\"TYPE\":\"FAILURE\"}");
                }
            }
            else if (type.equals("QUERY_PARKING_LOT")) {
                ParkingLot parkingLot = vehicleProcessor.getParkingLot();
                int totalParkingSpaces = parkingLot.getTotalSpots();
                int availableSpaces = parkingLot.getAvailableSpots();
                try{
                    JSONObject sendMessage = new JSONObject();
                    sendMessage.put("TYPE","PARKING_LOT");
                    sendMessage.put("DATA",totalParkingSpaces+","+availableSpaces);
                    writer.println(sendMessage);
                }catch(Exception e){
                    writer.println("{\"TYPE\":\"FAILURE\"}");
                }
            }
            else if (type.equals("ADD_VIP_USER")) {
                Object dataObject = jsonObject.get("DATA");
                String licensePlate = dataObject.toString().split(",")[0];
                String amount = dataObject.toString().split(",")[1];
                if(vipUsers.isVIPUser(licensePlate)){
                    vipUsers.topUpBalance(licensePlate, Float.parseFloat(amount));
                }else{
                    vipUsers.addVIPUser(licensePlate,Float.parseFloat(amount));
                }
                writer.println("{\"TYPE\":\"ADD SUCCESS\"}");
            }
            else if (type.equals("SHOW_VIP_USERS")){
                try{
                    JSONObject sendMessage = new JSONObject();
                    JSONArray jsonArray = new JSONArray(vipUsers);
                    sendMessage.put("TYPE","VIP_USERS");
                    sendMessage.put("DATA",jsonArray);
                    writer.println(sendMessage);
                }catch(Exception e){
                    writer.println("{\"TYPE\":\"FAILURE\"}");
                }
            }
            else if (type.equals("AUTHENTICATION")) { // 这是密码验证
                Object dataObject = jsonObject.get("DATA");
                String[] parts = dataObject.toString().split(",");
                String account = parts[0];
                String password = parts[1];
                if (account.equals("1") && password.equals("1")) {
                    writer.println("{\"TYPE\":\"PASS\"}");
                } else {
                    writer.println("{\"TYPE\":\"NO_PASS\"}");
                }
            } else if (type.equals("SHUT_DOWN")) {
                stop();
            } else {
                writer.println("{\"TYPE\":\"INVALID REQUEST\"}");
            }
        }catch(Exception e){
            e.printStackTrace();
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

