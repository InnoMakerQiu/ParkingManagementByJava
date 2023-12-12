package ClientSide.SystemClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class ButtonListenerOfQueryTheNumberOfVehicles implements ActionListener {
    final JFrame jf;

    public ButtonListenerOfQueryTheNumberOfVehicles(JFrame jframe) {
        jf = jframe;
    }

    public void actionPerformed(ActionEvent e) {
        DisplayInterface.queryParkingLot();
    }
}

// 按钮监听器，处理主菜单中的"在库车辆管理"按钮事件
class ButtonListenerOfManagement implements ActionListener {
    final JFrame jf;

    /**
     * 构造函数，接收主窗口对象
     *
     * @param jf 主窗口对象
     */
    ButtonListenerOfManagement(JFrame jf) {
        this.jf = jf;
    }

    /**
     * 处理按钮点击事件
     *
     * @param e ActionEvent对象
     */
    public void actionPerformed(ActionEvent e) {
        JFrame jf1 = new JFrame();
        // 创建查询在库车辆数量以及剩余停车位数按钮
        JButton q1 = new JButton("查询在库车辆数量以及剩余停车位数");
        q1.setFont(new Font("宋体", Font.BOLD, 10));
        q1.setBounds(110, 60, 180, 30);

        // 创建按钮监听器对象
        ButtonListenerOfQueryTheNumberOfVehicles b7 =
                new ButtonListenerOfQueryTheNumberOfVehicles(jf);
        // 为按钮添加监听器
        q1.addActionListener(b7);

        // 配置子窗口属性
        jf1.setLocationRelativeTo(null);
        jf1.setTitle("停车管理系统");
        jf1.setIconImage(new ImageIcon("src/").getImage());
        jf1.setSize(400, 350);
        jf1.setLayout(null);
        jf1.setResizable(false);
        jf1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf1.setVisible(true);
        jf1.setLocationRelativeTo(null);
        // 将按钮添加到子窗口
        jf1.add(q1);
    }
}
