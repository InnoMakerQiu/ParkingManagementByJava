package ClientSide.SystemClient;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//主菜单->入库车辆查询->显示车牌号的入库信息
class ButtonListenerOfQueryingByLicensePlate implements ActionListener {

    final JFrame jf;
    final JTextField jtf;

    public ButtonListenerOfQueryingByLicensePlate(
            JFrame jframe, JTextField jtf) {
        jf = jframe;
        this.jtf = jtf;
    }

    public void actionPerformed(ActionEvent e) {
        DisplayInterface.queryVehicleInParkingLot(jtf.getText());
    }
}


class ButtonListenerOfQueryingInParkingLot implements ActionListener {
    final JFrame parentJFrame;

    /**
     * 构造函数，接收主窗口对象
     *
     * @param jf 母窗口对象
     */
    public ButtonListenerOfQueryingInParkingLot(JFrame jf) {
        this.parentJFrame = jf;
    }

    /**
     * 处理按钮点击事件
     *
     * @param e ActionEvent对象
     */
    public void actionPerformed(ActionEvent e) {
        parentJFrame.setVisible(false);
        JFrame jf1 = new JFrame();

        jf1.setTitle("停车管理系统");
        jf1.setIconImage(new ImageIcon("src/").getImage());
        jf1.setSize(400, 350);
        jf1.setLayout(null);
        jf1.setResizable(false);
        jf1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf1.setVisible(true);
        jf1.setLocationRelativeTo(null);

        JLabel jl = new JLabel("请输入车牌号：", SwingConstants.CENTER);
        jl.setBounds(110, 10, 180, 30);
        jl.setFont(new Font("宋体", Font.BOLD, 10));
        jf1.add(jl);

        JTextField jtf;
        jtf = new JTextField("");
        jtf.setBounds(110, 100, 180, 30);
        jf1.add(jtf);

        JButton enterKey = new JButton("确定");
        enterKey.setBounds(100, 200, 60, 40);
        jf1.add(enterKey);
        ButtonListenerOfQueryingByLicensePlate b10 =
                new ButtonListenerOfQueryingByLicensePlate(jf1, jtf);
        enterKey.addActionListener(b10);
        enterKey.setFont(new Font("宋体", Font.BOLD, 10));

        jf1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                parentJFrame.setVisible(true);
            }
        });
    }
}
