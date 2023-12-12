package ClientSide.SystemClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * the class named "Welcome" represents the main menu of the parking management system
 * displayed after a successful login.
 */
public class Welcome {
    final JFrame parentJFrame;

    /**
     * Displays the main menu window with various operation options.
     */
    public Welcome(JFrame parentJFrame) {
        this.parentJFrame = parentJFrame;
        JFrame jf = new JFrame();
        parentJFrame.setVisible(false);
        jf.setTitle("停车管理系统");
        jf.setIconImage(new ImageIcon("src/").getImage());
        jf.setSize(400, 350);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setLayout(null);
        jf.setVisible(true);

        JLabel j0 = new JLabel("请选择操作:", SwingConstants.CENTER);
        JButton j1 = new JButton("车辆信息查询");
        JButton j2 = new JButton("在库车辆管理");
        JButton j3 = new JButton("生成账单报表");
        JButton j4 = new JButton("关闭停车系统");

        j0.setFont(new Font("宋体", Font.BOLD, 10));
        j1.setFont(new Font("宋体", Font.BOLD, 10));
        j2.setFont(new Font("宋体", Font.BOLD, 10));
        j3.setFont(new Font("宋体", Font.BOLD, 10));
        j4.setFont(new Font("宋体", Font.BOLD, 10));

        j0.setBounds(110, 10, 180, 30);
        j1.setBounds(110, 40, 180, 30);
        j2.setBounds(110, 70, 180, 30);
        j3.setBounds(110, 100, 180, 30);
        j4.setBounds(110, 130, 180, 30);

        jf.add(j0);
        jf.add(j1);
        jf.add(j2);
        jf.add(j3);
        jf.add(j4);


        // Button listeners for different operations
        ButtonListenerOfQueryingInParkingLot b1 = new ButtonListenerOfQueryingInParkingLot(jf);
        j1.addActionListener(b1);

        ButtonListenerOfManagement b2 = new ButtonListenerOfManagement(jf);
        j2.addActionListener(b2);

        ButtonListenerOfBillReport b3 =
                new ButtonListenerOfBillReport(jf);
        j3.addActionListener(b3);

        ButtonListenerOfShuttingDownTheSystem b4 =
                new ButtonListenerOfShuttingDownTheSystem(jf);
        j4.addActionListener(b4);

        jf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                parentJFrame.setVisible(true);
            }
        });
    }
}
