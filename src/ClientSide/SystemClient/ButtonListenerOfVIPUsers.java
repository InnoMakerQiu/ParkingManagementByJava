package ClientSide.SystemClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// VIP用户查询 主菜单->VIP用户查询和充值->VIP用户查询
class ButtonListenerOfVIPUsersForUsersView implements ActionListener {
    final JFrame jf;

    ButtonListenerOfVIPUsersForUsersView(JFrame jFrame) {
        this.jf = jFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 调用DisplayInterface类的queryVIPUsersForForUsersView方法
        DisplayInterface.queryVIPUsersForForUsersView(jf);
    }
}

// VIP用户余额添加 主菜单->VIP用户查询和充值->VIP余额添加
class ButtonListenerOfVIPUsersForBalanceAddition implements ActionListener {
    final JFrame jf;

    ButtonListenerOfVIPUsersForBalanceAddition(JFrame jFrame) {
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
        jf1.setFocusable(true);

        // 添加标签和文本框到新窗口
        JLabel jl = new JLabel("VIP余额添加:", SwingConstants.CENTER);
        jl.setBounds(110, 10, 180, 30);
        jl.setFont(new Font("宋体", Font.BOLD, 10));

        JTextField jtf = new JTextField("请输入车牌号:");
        jtf.setForeground(Color.gray);
        jtf.setBounds(70, 100, 180, 30);
        JTextField jtf2 = new JTextField("请输入充值金额:");
        jtf2.setForeground(Color.gray);
        jtf2.setBounds(70, 150, 180, 30);

        // 添加文本框的焦点监听器，实现在文本框获得或失去焦点时的显示效果
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

        jtf2.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (jtf2.getText().equals("请输入充值金额:")) {
                    jtf2.setText("");
                    jtf2.setForeground(Color.black);
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtf2.getText().isEmpty()) {
                    jtf2.setForeground(Color.gray);
                    jtf2.setText("请输入充值金额:");
                }
            }
        });

        // 添加按钮到新窗口，并设置按钮的监听器
        JButton jb = new JButton("确定");
        jb.setBounds(100, 200, 180, 30);
        jb.addActionListener(e1 ->
                DisplayInterface.queryVIPUsersForBalanceAddition(jtf.getText(), jtf2.getText()));

        jf1.add(jb);
        jf1.add(jtf2);
        jf1.add(jtf);
        jf1.add(jl);

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

// VIP用户按钮监听器
public class ButtonListenerOfVIPUsers implements ActionListener {
    final JFrame jf;

    public ButtonListenerOfVIPUsers(JFrame jframe) {
        jf = jframe;
    }

    public void actionPerformed(ActionEvent e) {
        // 将原窗口设为不可见
        jf.setVisible(false);

        // 创建新的JFrame窗口
        JFrame jf2 = new JFrame();
        jf2.setTitle("VIP用户查询和充值");
        jf2.setIconImage(new ImageIcon("src/").getImage());
        jf2.setSize(400, 350);
        jf2.setLocationRelativeTo(null);
        jf2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf2.setLayout(null);

        // 添加标签和两个按钮到新窗口
        JLabel j0 = new JLabel("请选择操作:", SwingConstants.CENTER);
        JButton j1 = new JButton("VIP用户查询");
        JButton j2 = new JButton("VIP用户余额充值");

        j0.setFont(new Font("宋体", Font.BOLD, 10));
        j1.setFont(new Font("宋体", Font.BOLD, 10));
        j2.setFont(new Font("宋体", Font.BOLD, 10));

        j0.setBounds(110, 10, 180, 30);
        j1.setBounds(110, 40, 180, 30);
        j2.setBounds(110, 70, 180, 30);

        jf2.add(j0);
        jf2.add(j1);
        jf2.add(j2);
        jf2.setVisible(true);

        // 添加两个按钮的监听器
        j1.addActionListener(new ButtonListenerOfVIPUsersForUsersView(jf2));
        j2.addActionListener(new ButtonListenerOfVIPUsersForBalanceAddition(jf2));

        // 添加窗口关闭监听器，实现在窗口关闭时将原窗口设为可见
        jf2.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                jf.setVisible(true);
            }
        });
    }
}
