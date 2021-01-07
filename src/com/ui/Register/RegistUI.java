package com.ui.Register;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Container;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.beans.User;

import com.service.UserService;
import com.utils.UiUtils;

/**
 * 注册页面
 */

public class RegistUI extends JDialog {
	JFrame fatherJframe;
	User user = new User();
	boolean flag[] = new boolean[6];

	public RegistUI() {
		super();
	}

	public RegistUI(JFrame fatherJframe) {
		super(fatherJframe, true);
		for (boolean f : this.flag)
			f = false;
		this.flag[3] = true;

		this.user.setUser_sex("男");

		CreateJFrame("注册");
	}

	public void CreateJFrame(String title) {
		setTitle(title);
		setLayout(new BorderLayout()); // 边界布局
		Container container = getContentPane();
		setResizable(false);
		container.add(BorderLayout.NORTH, setNorth());// 设置页面北部
		container.add(BorderLayout.CENTER, setCenter());// 设置页面中部
		container.add(BorderLayout.SOUTH, setSouth()); // 设置页面南部
		container.setBackground(Color.white);
		initJFram();
	}

	private JPanel setSouth() {
		JPanel jPs = new JPanel(new GridLayout(2,1,0,10));
		JPanel jpL1=new JPanel(new FlowLayout(FlowLayout.CENTER,30,10));
		jPs.add(jpL1);
		jPs.add(UiUtils.getLabel(""));
		JButton jb = UiUtils.getButton("注册");
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int i=0;
				for(boolean f:flag) {
					if(f)i++;
				}
				if(i==6) {
					UserService.regist(user);
					JOptionPane.showMessageDialog(null, "注册成功");
					RegistUI.this.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "注册信息有误，请检查","注册失败", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		JButton je= UiUtils.getButton("登录");
		je.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				RegistUI.this.dispose();
			}
		});
		jpL1.add(jb);
		jpL1.add(je);
		
		return jPs;
	}

	private JPanel setCenter() {
		JPanel jpF = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		JPanel jpc1 = new JPanel(new GridLayout(6, 1, 50, 50));
		JPanel jpc2 = new JPanel(new GridLayout(6, 2, 0, 50));
//		JPanel jpc3=new JPanel(new GridLayout(6, 1, 20, 50));	//存放提示

		jpc1.add(UiUtils.getLabel("用户名:", new Font("微软雅黑", Font.PLAIN, 18)));
		jpc1.add(UiUtils.getLabel("密    码:", new Font("微软雅黑", Font.PLAIN, 18)));
		jpc1.add(UiUtils.getLabel("确认密码:", new Font("微软雅黑", Font.PLAIN, 18)));
		jpc1.add(UiUtils.getLabel("性别:", new Font("微软雅黑", Font.PLAIN, 18)));
		jpc1.add(UiUtils.getLabel("年龄:", new Font("微软雅黑", Font.PLAIN, 18)));
		jpc1.add(UiUtils.getLabel("手机号:", new Font("微软雅黑", Font.PLAIN, 18)));

		JLabel[] jmsg = new JLabel[6];
		for (int i = 0; i < 6; i++) {
			jmsg[i] = UiUtils.getLabel(" ");
			jmsg[i].setHorizontalAlignment(JLabel.LEFT);
		}

		JTextField jtUserName = UiUtils.getJTextField();
		jtUserName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {

				JTextField j = (JTextField) e.getSource();
				if (j.getText().equals(""))
					return;
				if (UserService.checkUserName(j.getText())) {
					user.setUser_name(j.getText());
					flag[0] = true;
					jmsg[0].setText("  √");
					jmsg[0].setForeground(Color.GREEN);
				} else {
					jmsg[0].setText("  用户名已存在");
					jmsg[0].setForeground(Color.RED);
					flag[0] = false;

				}

			}
		});

		jtUserName.setColumns(10);
		JRadioButton jrb1 = new JRadioButton("男");
		jrb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				JRadioButton jrb = (JRadioButton) e.getSource();
				if (jrb.isSelected()) {
					user.setUser_sex("男");
				}
			}
		});
		JRadioButton jrb2 = new JRadioButton("女");
		jrb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				JRadioButton jrb = (JRadioButton) e.getSource();
				if (jrb.isSelected()) {
					user.setUser_sex("女");
				}
			}
		});
		ButtonGroup bg = new ButtonGroup();
		
		bg.add(jrb1);
		bg.add(jrb2);
		jrb1.setSelected(true);
		JTextField jtAge = UiUtils.getJTextField();
		jtAge.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				JTextField j = (JTextField) e.getSource();
				try {
					int age=new Integer(j.getText());
					if (age<0) {
						throw new Exception();
					}
					flag[4]=true;
					jmsg[4].setText("  √");
					jmsg[4].setForeground(Color.GREEN);
					user.setUser_age(age);
				} catch (Exception e2) {
					jmsg[4].setForeground(Color.RED);
					jmsg[4].setText("  年龄输入错误");
					flag[4]=false;
				}
			}
		});
		JTextField jtPhone = UiUtils.getJTextField();
		jtPhone.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				JTextField j = (JTextField) e.getSource();
				if(j.getText().equals("")) {
					flag[5]=false;
					jmsg[5].setForeground(Color.RED);
					jmsg[5].setText("  请输入手机号");
				}else {
					user.setUser_phone(j.getText());
					jmsg[5].setText("  √");
					jmsg[5].setForeground(Color.GREEN);
					flag[5]=true;
				}
			}
			
		});
		JPasswordField jtpa = UiUtils.getJPasswordField();
		jtpa.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				JTextField j = (JTextField) e.getSource();
				if(j.getText().equals("")) {
					flag[1]=false;
					jmsg[1].setText("  请输入密码");
					jmsg[1].setForeground(Color.RED);
				}else {
					user.setUser_password(j.getText());
					jmsg[1].setText("  √");
					jmsg[1].setForeground(Color.GREEN);
					flag[1]=true;
				}
			}
		});
		JPasswordField jtpaCheck = UiUtils.getJPasswordField();
		jtpaCheck.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				JPasswordField jtp=(JPasswordField)e.getSource();
				String password=new String(jtp.getPassword());
				if(password.equals("")) {
					flag[2]=false;
					jmsg[2].setText("  ");
				}else if(!password.equals(user.getUser_password())) {
					flag[2]=false;
					jmsg[2].setText("  两次输入不一致");
					jmsg[2].setForeground(Color.RED);
				}else {
					flag[2]=true;
					jmsg[2].setText("  √");
					jmsg[2].setForeground(Color.GREEN);
				}
			}
		});
		jpc2.add(jtUserName);
		jpc2.add(jmsg[0]);
		jpc2.add(jtpa);
		jpc2.add(jmsg[1]);
		jpc2.add(jtpaCheck);
		jpc2.add(jmsg[2]);
		jpc2.add(jrb1);
		jpc2.add(jrb2);
		jpc2.add(jtAge);
		jpc2.add(jmsg[4]);
		jpc2.add(jtPhone);
		jpc2.add(jmsg[5]);

//		jpc3.add(jmsg);

		jpF.add(jpc1);
		jpF.add(jpc2);
//		jpF.add(jpc3);
		return jpF;
	}

	private JPanel setNorth() {
		JPanel jPanelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		jPanelNorth.add(UiUtils.getTitleLabel("用户注册"));
		return jPanelNorth;
	}

	public void initJFram() {
		setSize(600, 700);
		setLocationRelativeTo(null);

//        setBounds(200, 200, 500, 400);
		setVisible(true);
		setResizable(false); // 设置窗体大小不可变

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

}
