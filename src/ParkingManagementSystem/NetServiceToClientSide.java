package ParkingManagementSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.List;

/**
 * NetServiceToClientSide class implements the Runnable interface
 * to create a server for communication with Client
 */
public class NetServiceToClientSide implements Runnable {
    int port;
    ParkingManagement vehicleProcessor;
    ServerSocket serverSocket;
    final SQLBasedFinancialManagement financialManager;
    final NetServiceToPeripheralDevice netWork;
    private boolean flag;


    /**
     * Constructor for NetServiceToClientSide.
     *
     * @param port              The port number on which the server will listen.
     * @param vehicleProcessor The ParkingManagement instance for vehicle processing.
     * @param financialManager The FinancialManagement instance for managing financial records.
     */
    public NetServiceToClientSide(int port, ParkingManagement vehicleProcessor,
                                  SQLBasedFinancialManagement financialManager,
                                  NetServiceToPeripheralDevice netWork){
        this.port = port;
        this.vehicleProcessor = vehicleProcessor;
        this.financialManager = financialManager;
        this.netWork = netWork;
        this.flag = true;
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started and listening on port " + port);
            this.serverSocket = serverSocket;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void stop(){
        flag = false;
        netWork.stop();
        financialManager.closeFinancialManagement();
    }

    /**
     * Implementation of the run method from the Runnable interface.
     * Waits for client connections and handles them by invoking handleClient.
     */
    public void run(){
            while(flag){
                try{
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                    handleClient(clientSocket);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
    }

    /** Handles communication with a client
     *
     * @param clientSocket The socket for communication with client
     */
    private void handleClient(Socket clientSocket) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ){
            String message;
            while ((message = reader.readLine()) != null) {
                processMessage(message, writer);
            }
        } catch (IOException e) {
            System.out.println("invalid disconnected");
        }
        System.out.println("Client disconnected");
    }

    /**
     * Processes a message received from the client, which is assumed
     * to be in the format "Type:data".
     *
     * @param message The message received from the client.
     * @param writer  The PrintWriter for sending responses to the client.
     */
    private void processMessage(String message,PrintWriter writer) {
        // Parse the request and process
        System.out.println("receive message from client:"+message);//this part is for debug
        String[] parts = message.split(":");
        if (parts.length == 2 && parts[0].equals("QUERY_ENTRY_TIME")) {
            String licensePlate = parts[1];
            try {
                Date entryTime = vehicleProcessor.getEntryTimeByLicensePlate(licensePlate);
                writer.println(entryTime.toString());
            } catch (Exception pe) {
                writer.println("Don't found the vehicle");
            }
        }
        else if(parts.length == 2 && parts[0].equals("QUERY_TOTAL_REVENUE")){
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
        }
        else if(parts.length == 2 && parts[0].equals("QUERY_VEHICLE_REVENUE")){
            String text;
            StringBuilder texts = new StringBuilder(); // 存储账单信息的列表

            String[] parts2 = parts[1].split(",");
            String license = parts2[0];
            String number = parts2[1];
            int endValues = Integer.parseInt(number);
            Date end,start;
            if(endValues>100){
                List<Bill> yearlyRecords = financialManager.getYearlyRecords(license,endValues);
                for (Bill bill : yearlyRecords) {
                    float revenue = bill.getFee();
                    start = bill.getEntryTime();
                    end = bill.getExitTime();
                    text = license+","+start+","+end+","+revenue;
                    texts.append(text).append("+");
                }
            }
            else if(endValues<20){
                List<Bill> monthlyRecords = financialManager.getMonthlyRecords(license,2023,endValues);
                for (Bill bill : monthlyRecords) {
                    start = bill.getEntryTime();
                    end = bill.getExitTime();
                    float revenue = bill.getFee();
                    text = license+","+start+","+end+","+revenue;
                    texts.append(text).append("+");
                }
            }
            System.out.println(texts);
            writer.println(texts);
        }
        else if(parts[0].equals("QUERY_PARKING_LOT")){
            ParkingLot parkingLot = vehicleProcessor.getParkingLot();
            int totalParkingSpaces = parkingLot.getTotalSpots();
            int availableSpaces = parkingLot.getAvailableSpots();
            writer.println(totalParkingSpaces+","+availableSpaces);
        }
        else if(parts.length==3){//this is the password authentication
            String account = parts[1].split(",")[0];
            String password = parts[2];
            if(account.equals("1")&&password.equals("1")){
                writer.println("passed");
            }else{
                writer.println("No passed");
            }
        }
        else if (parts[0].equals("shut down the parking system.")) {
            stop();
        }else {
            writer.println("Invalid request");
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
