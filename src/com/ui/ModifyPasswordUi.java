package com.ui;

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
import com.biz.UserBiz;

public class ModifyPasswordUi extends JDialog {
	JFrame fatherJframe;
	User user = new User();
	User loginUser=null;
	boolean flag1 = false;
	boolean flag2=	false;
	public ModifyPasswordUi() {
		super();
	}

	public ModifyPasswordUi(JDialog fatherJframe,User loginUser) {
		super(fatherJframe, true);
		this.loginUser=loginUser;
		setDefault();
		CreateJFrame("修改密码");
	}
	private void setDefault() {
		user.setUser_id(loginUser.getUser_id());
		user.setUser_name(loginUser.getUser_name());
		user.setUser_password(loginUser.getUser_password());
		user.setUser_age(loginUser.getUser_age());
		user.setUser_sex(loginUser.getUser_sex());
		user.setUser_phone(loginUser.getUser_phone());
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
		jPs.add(Ctrols.getLabel(""));
		JButton jb = Ctrols.getButton("确认");
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if(flag1&&flag2) {
					user.setUser_password(UserBiz.modifyPassword(user));
					JOptionPane.showMessageDialog(null, "修改成功");
					ModifyPasswordUi.this.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "密码信息有误，请检查","修改失败", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		JButton je=Ctrols.getButton("取消");
		je.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				ModifyPasswordUi.this.dispose();
			}
		});
		jpL1.add(jb);
		jpL1.add(je);
		
		return jPs;
	}

	private JPanel setCenter() {
		JPanel jpF = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		JPanel jpc1 = new JPanel(new GridLayout(2, 1, 50, 50));
		JPanel jpc2 = new JPanel(new GridLayout(2, 2, 0, 50));


		jpc1.add(Ctrols.getLabel("新的密码:", new Font("微软雅黑", Font.PLAIN, 18)));
		jpc1.add(Ctrols.getLabel("确认密码:", new Font("微软雅黑", Font.PLAIN, 18)));


		JLabel jmsg1 = Ctrols.getLabel("   ");
		JLabel jmsg2 = Ctrols.getLabel("   ");
		JPasswordField jtpa = Ctrols.getJPasswordField();
		jtpa.setColumns(12);
		jtpa.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				JTextField j = (JTextField) e.getSource();
				if(j.getText().equals("")) {
					flag1=false;
					jmsg1.setText("  请输入密码");
					jmsg1.setForeground(Color.RED);
				}else {
					user.setUser_password(j.getText());
					jmsg1.setText("  √");
					jmsg1.setForeground(Color.GREEN);
					flag1=true;
				}
			}
		});
		jpc2.add(jtpa);
		jpc2.add(jmsg1);
		JPasswordField jtpaCheck = Ctrols.getJPasswordField();
		jtpaCheck.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				JPasswordField jtp=(JPasswordField)e.getSource();
				String password=new String(jtp.getPassword());
				if(password.equals("")) {
					flag2=false;
					jmsg2.setText("  ");
				}else if(!password.equals(user.getUser_password())) {
					flag2=false;
					jmsg2.setText("  两次输入不一致");
					jmsg2.setForeground(Color.RED);
				}else {
					flag2=true;
					jmsg2.setText("  √");
					jmsg2.setForeground(Color.GREEN);
				}
			}
		});
		jpc2.add(jtpaCheck);
		jpc2.add(jmsg2);

		
		
		jpF.add(jpc1);
		jpF.add(jpc2);
		return jpF;
	}

	private JPanel setNorth() {
		JPanel jPanelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		jPanelNorth.add(Ctrols.getTitleLabel("修改密码"));
		return jPanelNorth;
	}

	public void initJFram() {
		setSize(600, 400);
		setLocationRelativeTo(null);

//        setBounds(200, 200, 500, 400);
		setVisible(true);
		setResizable(false); // 设置窗体大小不可变

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	public User getUser() {
		return user;
	}

}