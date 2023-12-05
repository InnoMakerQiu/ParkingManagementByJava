package ClientSide;

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
import javax.swing.JOptionPane;
import java.awt.*;
import javax.swing.*;






/**
 * ButtonListenerOfLogin class represents the action listener for the login button.
 * It handles the login process and initiates the main menu window upon successful login.
 */
class ButtonListenerOfLogin implements ActionListener {
	final JFrame jf;
	final JTextField account;
	final JTextField password;

	/**
	 * Constructs a ButtonListenerOfLogin object.
	 *
	 * @param jf       The JFrame associated with the login button.
	 * @param account  The JTextField for entering the account username.
	 * @param password The JTextField for entering the account password.
	 */
	public ButtonListenerOfLogin(JFrame jf, JTextField account, JTextField password) {
		this.jf = jf;
		this.account = account;
		this.password = password;
	}

	/**
	 * Handles the action event when the login button is clicked.
	 * It performs the authentication process and opens the main menu window upon successful login.
	 *
	 * @param e The ActionEvent associated with the button click.
	 */
	public void actionPerformed(ActionEvent e) {
		String username = this.account.getText();
		String password = this.password.getText();

		// Perform authentication (Replace with your authentication logic)
		if (passwordAuthentication(username, password)) {
			// Open the main menu window upon successful login
			new Welcome(jf);
		} else {
			// Display an error message for incorrect login information
			JOptionPane jop = new JOptionPane();
			JOptionPane.showMessageDialog(jop, "信息输入有误，请重新输入");
		}
	}

	/**
	 * Simulates password authentication by sending user credentials to the server.
	 * Replace this method with your actual authentication logic.
	 *
	 * @param username The entered username.
	 * @param password The entered password.
	 * @return True if authentication is successful, false otherwise.
	 */
	private boolean passwordAuthentication(String username, String password) {
		// Replace the following lines with your authentication logic
		// (e.g., contacting a server for authentication)
		Client.sendMessage("account:" + username + ", password:" + password);
		String isPassed = Client.receiveMessage();
		System.out.println(isPassed);
		return isPassed != null && isPassed.equals("passed");
	}
}


/**
 * the class named "Welcome" represents the main menu of the parking management system
 * displayed after a successful login.
 */
class Welcome {
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
		ButtonListenerOfQuerying b1 = new ButtonListenerOfQuerying(jf);
		j1.addActionListener(b1);

		ButtonListenerOfManagement b2 = new ButtonListenerOfManagement(jf);
		j2.addActionListener(b2);

		ButtonListenerOfGenerateBillReport b3 =
				new ButtonListenerOfGenerateBillReport(jf);
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


// 主界面按钮监听器，处理车辆信息查询按钮事件
class ButtonListenerOfQuerying implements ActionListener {
	final JFrame parentJFrame;

	/**
	 * 构造函数，接收主窗口对象
	 *
	 * @param jf 母窗口对象
	 */
	public ButtonListenerOfQuerying(JFrame jf) {
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

class ButtonListenerOfGenerateBillReport implements ActionListener{//生成账单报表
	final JFrame jf;
	public ButtonListenerOfGenerateBillReport(JFrame jframe) {
		jf=jframe;
	}
	public void actionPerformed(ActionEvent e) {
		jf.setVisible(false);

		JFrame jf2 = new JFrame();
		jf2.setTitle("停车管理系统财务报表");
		jf2.setIconImage(new ImageIcon("src/").getImage());
		jf2.setSize(400, 350);
		jf2.setLocationRelativeTo(null);
		jf2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf2.setLayout(null);

		JLabel j0 = new JLabel("请选择操作:", SwingConstants.CENTER);
		JButton j1 = new JButton("车辆查询");
		JButton j2 = new JButton("时间段查询");

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

		// Button listeners for different operations
		// Create anonymous classes and use lambda
		// expressions as much as possible
		j1.addActionListener(new ButtonListenerOfBillReportByVehicle(jf2));

		j2.addActionListener(new
				ButtonListenerOfQueryTotalRevenueByTime(jf2));
		jf2.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				jf.setVisible(true);
			}
		});
	}
}

class ButtonListenerOfShuttingDownTheSystem implements ActionListener{
	final JFrame parentJFrame;
	ButtonListenerOfShuttingDownTheSystem(JFrame jf){
		this.parentJFrame = jf;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DisplayInterface.shutDownTheParkingSystem();
		parentJFrame.dispose();
		Client.performExitOperations();
	}
}


//主菜单->在库车辆管理->查询在库车辆数量以及总停车位
class ButtonListenerOfQueryTheNumberOfVehicles implements ActionListener {
	final JFrame jf;

	public ButtonListenerOfQueryTheNumberOfVehicles(JFrame jframe) {
		jf = jframe;
	}

	public void actionPerformed(ActionEvent e) {
		DisplayInterface.queryParkingLot();
	}
}

//主菜单->入库车辆查询->显示车牌号的入库信息
class ButtonListenerOfQueryingByLicensePlate implements ActionListener{

	final JFrame jf;
	final JTextField jtf;
	public ButtonListenerOfQueryingByLicensePlate(JFrame jframe,JTextField jtf) {
		jf=jframe;
		this.jtf = jtf;
	}
	public void actionPerformed(ActionEvent e) {
		DisplayInterface.queryVehicleInParkingLot(jtf.getText());
	}
}

//根据时间范围生成账单报表，主菜单->账单查询->根据时间查询
class ButtonListenerOfQueryTotalRevenueByTime implements ActionListener{
	final JFrame jFrame;
	ButtonListenerOfQueryTotalRevenueByTime(JFrame jFrame){
		this.jFrame = jFrame;
	}
	public void actionPerformed(ActionEvent e) {
		JFrame jf1=new JFrame();
		jFrame.setVisible(false);
		jf1.setTitle("停车管理系统");
		jf1.setIconImage(new ImageIcon("src/").getImage());
		jf1.setSize(400,350);
		jf1.setLayout(null);
		jf1.setResizable(false);
		jf1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf1.setVisible(true);
		jf1.setLocationRelativeTo(null);
		jf1.setFocusable(true);

		JLabel jl=new JLabel("账单报表：",SwingConstants.CENTER);
		jl.setBounds(110,10,180,30);
		jl.setFont(new Font("宋体",Font.BOLD,10));


		JTextField jtf = new JTextField("请输入起始时间: 例2023/5/2");
		jtf.setForeground(Color.gray);
		jtf.setBounds(70,100,180,30);
		JTextField jtf2 = new JTextField("请输入终止时间: 例2023/5/10");
		jtf2.setForeground(Color.gray);
		jtf2.setBounds(70,150,180,30);

		jtf.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if(jtf.getText().equals("请输入起始时间: 例2023/5/2")) {
					jtf.setText("");
					jtf.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent e) {
				if(jtf.getText().isEmpty()) {
					jtf.setForeground(Color.gray);
					jtf.setText("请输入起始时间: 例2023/5/2");
				}
			}
		});
		jtf2.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if(jtf2.getText().equals("请输入终止时间: 例2023/5/10")) {
					jtf2.setText("");
					jtf2.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent e) {
				if(jtf2.getText().isEmpty()) {
					jtf2.setForeground(Color.gray);
					jtf2.setText("请输入终止时间: 例2023/5/10");
				}
			}
		});

		JButton jb = new JButton("确定");
		jb.setBounds(100,200,180,30);
		jb.addActionListener(e1 ->
				DisplayInterface.queryTotalRevenue(jtf.getText(),jtf2.getText()));


		jf1.add(jb);
		jf1.add(jtf2);
		jf1.add(jtf);
		jf1.add(jl);
		jf1.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				jFrame.setVisible(true);
			}
		});
	}
}

//车辆信息查询--车牌号查询
class ButtonListenerOfBillReportByVehicle implements ActionListener{
	final JFrame jf;
	ButtonListenerOfBillReportByVehicle(JFrame jFrame){
		this.jf = jFrame;
	}
	public void actionPerformed(ActionEvent e){
		JFrame jf1=new JFrame();
		jf.setVisible(false);

		jf1.setTitle("停车管理系统");
		jf1.setIconImage(new ImageIcon("src/").getImage());
		jf1.setSize(400,350);
		jf1.setLayout(null);
		jf1.setResizable(false);
		jf1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf1.setVisible(true);
		jf1.setLocationRelativeTo(null);

		JLabel jl=new JLabel("账单报表：",SwingConstants.CENTER);
		jl.setBounds(110,10,180,30);
		jl.setFont(new Font("宋体",Font.BOLD,10));
		jf1.add(jl);

		JTextField jtf = new JTextField("请输入车牌号:");
		jtf.setForeground(Color.gray);
		jtf.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if(jtf.getText().equals("请输入车牌号:")) {
					jtf.setText("");
					jtf.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent e) {
				if(jtf.getText().isEmpty()) {
					jtf.setForeground(Color.gray);
					jtf.setText("请输入车牌号:");
				}
			}
		});
		jtf.setBounds(100,100,180,30);
		jf1.add(jtf);

		JTextField jtf2 = new JTextField("请输入查询年份/月份:");
		jtf2.setForeground(Color.gray);
		jf1.setFocusable(true);
		jtf2.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if(jtf2.getText().equals("请输入查询年份/月份:")) {
					jtf2.setText("");
					jtf2.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent e) {
				if(jtf2.getText().isEmpty()) {
					jtf2.setForeground(Color.gray);
					jtf2.setText("请输入查询年份/月份:");
				}
			}
		});
		jtf2.setBounds(100,150,180,30);
		jf1.add(jtf2);

		JButton jb = new JButton("确定");
		jb.setBounds(100,200,180,30);
		jb.addActionListener(e1 -> DisplayInterface.queryVehicleRevenue(jtf.getText(),jtf2.getText(),jf1));
		jf1.add(jb);

		jf1.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				jf.setVisible(true);
			}
		});
	}
}



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
		jlbTop = new JLabel(new ImageIcon("src/1.png"));
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

	public static String receiveMessage(){
		try{
			return reader.readLine();
		}catch(Exception e){
			return null;
		}
	}
}

