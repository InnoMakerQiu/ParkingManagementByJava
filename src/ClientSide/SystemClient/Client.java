package ClientSide.SystemClient;

import java.awt.Color;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Client {

	static PrintWriter writer; // 用于向服务器发送消息的输出流
	static BufferedReader reader; // 用于从服务器接收消息的输入流
	static JFrame frame; // 客户端界面的主窗口
	static Socket socket; // 与服务器建立的套接字连接

	public static void main(String[] args) {
		try{
			socket = new Socket("localhost", 9010); // 这里的 "localhost" 和 8080 需要根据实际情况修改
			writer = new PrintWriter(socket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		login();
	}
	static void login() {
		JLabel jlbTop, jlbReg, jlbPsw;
		JPanel jp;
		JTextField jtf;
		JPasswordField jpf;
		JCheckBox box1, box2;
		JButton jbLogin;

		frame = new JFrame();
		jlbTop = new JLabel(new ImageIcon("images/parking1.png"));
		jlbTop.setBounds(0, 0, 480, 160);

		jp = new JPanel();
		jp.setLayout(null);

		jtf = new JTextField("");
		jtf.setBounds(115, 10, 180, 30);

		jpf = new JPasswordField(15);
		jpf.setBounds(115, 35, 180, 30);

		box1 = new JCheckBox("记住密码");
		box1.setBounds(110, 75, 90, 15);
		box2 = new JCheckBox("自动登录");
		box2.setBounds(210, 75, 90, 15);

		jlbReg = new JLabel("注册账号");
		jlbReg.setBounds(300, 15, 60, 15);
		jlbReg.setForeground(Color.blue);

		jlbPsw = new JLabel("找回密码");
		jlbPsw.setBounds(300, 50, 60, 15);
		jlbPsw.setForeground(Color.blue);

		jbLogin = new JButton("登录");
		jbLogin.setBounds(110, 100, 180, 32);

		jp.add(jtf);
		jp.add(jpf);
		jp.add(box1);
		jp.add(box2);
		jp.add(jlbReg);
		jp.add(jlbPsw);
		jp.add(jbLogin);

		frame.add(jlbTop, "North");
		frame.add(jp, "Center");

		frame.setTitle("停车管理系统");
		frame.setIconImage(new ImageIcon("src/").getImage());
		frame.setSize(400, 350);

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				// 在窗口关闭时执行其他函数的操作
				performExitOperations();
			}
		});

		ButtonListenerOfLogin bl = new ButtonListenerOfLogin(frame, jtf, jpf);
		jbLogin.addActionListener(bl);
	}

	public static void performExitOperations(){
		writer.close();
		try{
			reader.close();
			socket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		frame.dispose();
		System.exit(0);
	}

	public static void sendMessage(String message){
		writer.println(message);
	}

	public static String receiveMessage() {
		try {
			return reader.readLine();
		} catch (Exception e) {
			return null;
		}
	}
}

