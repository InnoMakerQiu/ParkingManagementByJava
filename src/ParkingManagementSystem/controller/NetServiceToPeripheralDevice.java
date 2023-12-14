package ParkingManagementSystem.controller;
import ParkingManagementSystem.model.Bill;
import ParkingManagementSystem.model.ParkingTicket;
import ParkingManagementSystem.model.VIPUsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;



/**
 * NetServiceToPeripheralDevice 类实现了 Runnable 接口，用于创建与外部设备通信的服务器。
 */
public class NetServiceToPeripheralDevice implements Runnable {
    private ServerSocket serverSocket; // 服务器套接字
    final int port; // 服务器监听的端口号
    final VIPUsers vipUsers;// 管理VIP用户列表实例
    final ParkingManagement vehicleProcessor; // 处理车辆的 ParkingManagement 实例
    final SQLBasedFinancialManagement financialManager; // 管理财务记录的 FinancialManagement 实例

    private boolean isRunning; // 控制服务器运行状态的标志

    /**
     * NetServiceToPeripheralDevice 的构造方法。
     *
     * @param port              服务器监听的端口号。
     * @param vehicleProcessor 处理车辆的 ParkingManagement 实例。
     * @param financialManager 管理财务记录的 FinancialManagement 实例。
     */
    public NetServiceToPeripheralDevice(int port, ParkingManagement vehicleProcessor,
                                        SQLBasedFinancialManagement financialManager,VIPUsers vipUsers) {
        this.port = port;
        this.vehicleProcessor = vehicleProcessor;
        this.financialManager = financialManager;
        this.isRunning = true;
        this.vipUsers = vipUsers;
        try {
            // 创建服务器套接字并输出启动信息
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务器已启动，正在监听端口：" + port);
            this.serverSocket = serverSocket;
        } catch (IOException e) {
            System.out.println(port + " 端口被占用。");
        }
    }

    /**
     * 停止服务器的方法。
     */
    public void stop() {
        isRunning = false;
        // 关闭 BufferedReader 以中断阻塞的 readLine()
    }

    /**
     * Runnable 接口的 run 方法实现。等待客户端连接并通过调用 handleClient 处理连接。
     */
    public void run() {
        while (isRunning) {
            try (Socket clientSocket = serverSocket.accept()) {
                System.out.println("客户端已连接：" + clientSocket.getInetAddress());
                handleClient(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理与客户端的通信的方法。
     *
     * @param clientSocket 与客户端通信的套接字。
     */
    private void handleClient(Socket clientSocket) {
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String message;
            while ((message = reader.readLine()) != null) {
                if (!isRunning) {
                    break;
                }
                processMessage(message, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理从客户端接收到的消息，假定消息格式为 "licensePlate,in/out"。
     *
     * @param message 接收到的来自客户端的消息。
     * @param writer  用于向客户端发送响应的 PrintWriter。
     */
    private void processMessage(String message, PrintWriter writer) {
        // 假设消息格式为 "licensePlate,in/out"
        String[] parts = message.split(",");
        if (parts.length == 2) {
            String licensePlate = parts[0];

            // 使用提供的函数处理车辆进入/离开
            if (parts[1].equals("in")) {
                try {
                    vehicleProcessor.parkVehicle(licensePlate);
                    // 向客户端发送响应
                    writer.println("成功！");
                } catch (Exception e) {
                    writer.println("失败");
                }
            } else if (parts[1].equals("out")) {
                try {
                    ParkingTicket ticket = vehicleProcessor.exitParking(licensePlate);
                    if(vipUsers.reduceBalance(licensePlate,ticket.getFee())){
                        writer.println("成功！您好，vip用户，你的用户余额还剩"+
                                vipUsers.queryTheAmountOfVIPUser(licensePlate));
                        ticket.setFee(ticket.getFee()/2);
                    }
                    else{
                        writer.println("成功！" + licensePlate + " 的费用为 " + ticket.getFee());
                    }
                    financialManager.addBill(new Bill(ticket));
                } catch (Exception e) {
                    writer.println("失败");
                }
            } else {
                writer.println("失败");
            }
        } else {
            writer.println("失败");
        }
    }
}

