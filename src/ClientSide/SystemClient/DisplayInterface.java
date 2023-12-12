package ClientSide.SystemClient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.String;


/**
 * DisplayInterface class provides static methods for displaying query results in the parking management system.
 */
public class DisplayInterface {

    /**
     * Queries and displays the entry time information for a given license plate.
     *
     * @param licensePlate The license plate of the vehicle for which entry time is queried.
     */
    public static void queryVehicleInParkingLot(String licensePlate) {
        // Send query request to the server
        Client.sendMessage("QUERY_ENTRY_TIME:" + licensePlate);
        // Receive response from the server
        String enterDate = Client.receiveMessage();

        // Display the entry time information or an error message
        if (enterDate==null||enterDate.equals("Don't found the vehicle")) {
            JOptionPane.showMessageDialog(null, "未找到相关车辆信息", "停车管理系统",
                    JOptionPane.ERROR_MESSAGE);
        } else{
            JOptionPane.showMessageDialog(null, "车辆入库信息：" + "\n" + enterDate,
                    "停车管理系统", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Queries and displays information about the parking lot, including total and available parking spaces.
     */
    public static void queryParkingLot() {
        // Send query request to the server
        Client.sendMessage("QUERY_PARKING_LOT:null");
        // Receive response from the server
        String message = Client.receiveMessage();

        // Display the parking lot information or an error message
        if (message != null) {
            String[] parts = message.split(","); // Assuming data is separated by commas
            int totalParkingSpaces = Integer.parseInt(parts[0]);
            int availableSpaces = Integer.parseInt(parts[1]);

            JOptionPane.showMessageDialog(null, "总共停车位数量：" +
                            "\n" + totalParkingSpaces + "\n" + availableSpaces,
                    "停车管理系统", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "网络连接错误", "停车管理系统",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void queryTotalRevenue(String startTime,String endTime){
        Client.sendMessage("QUERY_TOTAL_REVENUE:"+startTime+","+endTime);
        String revenue = Client.receiveMessage();
        // Display the parking lot information or an error message
        if (revenue != null) {
            JOptionPane.showMessageDialog(null,"总收入为： "+revenue,
                    "停车管理系统", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "网络连接错误", "停车管理系统",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void queryVehicleRevenue(String license, String number, JFrame jf){
        jf.setVisible(false);
        Client.sendMessage("QUERY_VEHICLE_REVENUE:" + license + "," + number);
        String receivedData = Client.receiveMessage();
        System.out.println(receivedData);
        // Display the parking lot information or an error message
        if (receivedData != null) {
            // 解析数据
            String[] rows = receivedData.split("\\+");
            String[][] data = new String[rows.length][];
            for (int i = 0; i < rows.length; i++) {
                data[i] = rows[i].split(",");
            }

            JFrame frame = getFrame(data);

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    jf.setVisible(true);
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "网络连接错误", "停车管理系统",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void shutDownTheParkingSystem(){
        Client.sendMessage("shut down the parking system.");
    }

    private static JFrame getFrame(String[][] data) {
        JFrame frame = new JFrame("Bill Records");
        frame.setSize(900, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // 创建一个默认的表格模型
        DefaultTableModel tableModel = new DefaultTableModel(
                data, new String[]{"License", "Start", "End", "TotalRevenue"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 设置表格不可编辑
            }
        };

        // 创建一个 JTable，并使用默认的表格模型
        JTable table = new JTable(tableModel);

        // 创建一个滚动窗格，并将表格添加到滚动窗格中
        JScrollPane scrollPane = new JScrollPane(table);

        // 将滚动窗格添加到 JFrame 中
        frame.add(scrollPane, BorderLayout.CENTER);

        // 设置 JFrame 可见
        frame.setVisible(true);
        return frame;
    }
}

