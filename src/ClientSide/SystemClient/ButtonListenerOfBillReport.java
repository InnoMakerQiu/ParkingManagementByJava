package ClientSide.SystemClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// 根据车辆生成账单报表按钮监听器
class ButtonListenerOfBillReportByVehicle implements ActionListener {
    final JFrame jf;

    ButtonListenerOfBillReportByVehicle(JFrame jFrame) {
        this.jf = jFrame;
    }

    public void actionPerformed(ActionEvent e) {
        // 创建新的JFrame窗口
        JFrame jf1 = new JFrame();
        jf.setVisible(false);

        // 设置新窗口的属性
        jf1.setTitle("停车管理系统");
        jf1.setIconImage(new ImageIcon("src/").getImage());
        jf1.setSize(400, 350);
        jf1.setLayout(null);
        jf1.setResizable(false);
        jf1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf1.setVisible(true);
        jf1.setLocationRelativeTo(null);

        // 添加标签到新窗口
        JLabel jl = new JLabel("账单报表：", SwingConstants.CENTER);
        jl.setBounds(110, 10, 180, 30);
        jl.setFont(new Font("宋体", Font.BOLD, 10));
        jf1.add(jl);

        // 添加文本框1到新窗口
        JTextField jtf = new JTextField("请输入车牌号:");
        jtf.setForeground(Color.gray);
        jtf.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (jtf.getText().equals("请输入车牌号:")) {
                    jtf.setText("");
                    jtf.setForeground(Color.black);
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtf.getText().isEmpty()) {
                    jtf.setForeground(Color.gray);
                    jtf.setText("请输入车牌号:");
                }
            }
        });
        jtf.setBounds(100, 100, 180, 30);
        jf1.add(jtf);

        // 添加文本框2到新窗口
        JTextField jtf2 = new JTextField("请输入查询年份/月份:");
        jtf2.setForeground(Color.gray);
        jf1.setFocusable(true);
        jtf2.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (jtf2.getText().equals("请输入查询年份/月份:")) {
                    jtf2.setText("");
                    jtf2.setForeground(Color.black);
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtf2.getText().isEmpty()) {
                    jtf2.setForeground(Color.gray);
                    jtf2.setText("请输入查询年份/月份:");
                }
            }
        });
        jtf2.setBounds(100, 150, 180, 30);
        jf1.add(jtf2);

        // 添加确定按钮到新窗口，并设置按钮的监听器
        JButton jb = new JButton("确定");
        jb.setBounds(100, 200, 180, 30);
        jb.addActionListener(e1 -> DisplayInterface.queryVehicleRevenue(jtf.getText(), jtf2.getText(), jf1));
        jf1.add(jb);

        // 添加窗口关闭监听器，实现在窗口关闭时将原窗口设为可见
        jf1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                jf.setVisible(true);
            }
        });
    }
}

// 根据时间范围生成账单报表按钮监听器
class ButtonListenerOfBillReportByTime implements ActionListener {
    final JFrame jFrame;

    ButtonListenerOfBillReportByTime(JFrame jFrame) {
        this.jFrame = jFrame;
    }

    public void actionPerformed(ActionEvent e) {
        // 创建新的JFrame窗口
        JFrame jf1 = new JFrame();
        jFrame.setVisible(false);

        // 设置新窗口的属性
        jf1.setTitle("停车管理系统");
        jf1.setIconImage(new ImageIcon("src/").getImage());
        jf1.setSize(400, 350);
        jf1.setLayout(null);
        jf1.setResizable(false);
        jf1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf1.setVisible(true);
        jf1.setLocationRelativeTo(null);
        jf1.setFocusable(true);

        // 添加标签到新窗口
        JLabel jl = new JLabel("账单报表：", SwingConstants.CENTER);
        jl.setBounds(110, 10, 180, 30);
        jl.setFont(new Font("宋体", Font.BOLD, 10));

        // 添加文本框1到新窗口
        JTextField jtf = new JTextField("请输入起始时间: 例2023/5/2");
        jtf.setForeground(Color.gray);
        jtf.setBounds(70, 100, 180, 30);

        // 添加文本框2到新窗口
        JTextField jtf2 = new JTextField("请输入终止时间: 例2023/5/10");
        jtf2.setForeground(Color.gray);
        jtf2.setBounds(70, 150, 180, 30);

        // 添加文本框1的焦点监听器，实现在获得或失去焦点时的显示效果
        jtf.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (jtf.getText().equals("请输入起始时间: 例2023/5/2")) {
                    jtf.setText("");
                    jtf.setForeground(Color.black);
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtf.getText().isEmpty()) {
                    jtf.setForeground(Color.gray);
                    jtf.setText("请输入起始时间: 例2023/5/2");
                }
            }
        });

        // 添加文本框2的焦点监听器，实现在获得或失去焦点时的显示效果
        jtf2.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (jtf2.getText().equals("请输入终止时间: 例2023/5/10")) {
                    jtf2.setText("");
                    jtf2.setForeground(Color.black);
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtf2.getText().isEmpty()) {
                    jtf2.setForeground(Color.gray);
                    jtf2.setText("请输入终止时间: 例2023/5/10");
                }
            }
        });

        // 添加确定按钮到新窗口，并设置按钮的监听器
        JButton jb = new JButton("确定");
        jb.setBounds(100, 200, 180, 30);
        jb.addActionListener(e1 -> DisplayInterface.queryTotalRevenue(jtf.getText(), jtf2.getText()));

        // 添加各组件到新窗口
        jf1.add(jb);
        jf1.add(jtf2);
        jf1.add(jtf);
        jf1.add(jl);

        // 添加窗口关闭监听器，实现在窗口关闭时将原窗口设为可见
        jf1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                jFrame.setVisible(true);
            }
        });
    }
}

// 生成账单报表按钮监听器
public class ButtonListenerOfBillReport implements ActionListener {
    final JFrame jf;

    public ButtonListenerOfBillReport(JFrame jframe) {
        jf = jframe;
    }

    public void actionPerformed(ActionEvent e) {
        // 隐藏主窗口
        jf.setVisible(false);

        // 创建新的JFrame窗口
        JFrame jf2 = new JFrame();
        jf2.setTitle("停车管理系统财务报表");
        jf2.setIconImage(new ImageIcon("src/").getImage());
        jf2.setSize(400, 350);
        jf2.setLocationRelativeTo(null);
        jf2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf2.setLayout(null);

        // 添加标签到新窗口
        JLabel j0 = new JLabel("请选择操作:", SwingConstants.CENTER);
        JButton j1 = new JButton("车辆查询");
        JButton j2 = new JButton("时间段查询");

        // 设置字体样式
        j0.setFont(new Font("宋体", Font.BOLD, 10));
        j1.setFont(new Font("宋体", Font.BOLD, 10));
        j2.setFont(new Font("宋体", Font.BOLD, 10));

        // 设置各组件的位置
        j0.setBounds(110, 10, 180, 30);
        j1.setBounds(110, 40, 180, 30);
        j2.setBounds(110, 70, 180, 30);

        // 向新窗口添加各组件
        jf2.add(j0);
        jf2.add(j1);
        jf2.add(j2);
        jf2.setVisible(true);

        // 添加按钮监听器，实现按钮点击时的不同操作
        j1.addActionListener(new ButtonListenerOfBillReportByVehicle(jf2));
        j2.addActionListener(new ButtonListenerOfBillReportByTime(jf2));

        // 添加窗口关闭监听器，实现在窗口关闭时将原窗口设为可见
        jf2.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                jf.setVisible(true);
            }
        });
    }
}

