package com.ui.PersonalInfo;

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

import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.beans.User;

import com.service.UserService;
import com.utils.UiUtils;

/**
 * 展示个人信息页面
 */
public class PersonalUi extends JDialog {
	User user=new User();
	User loginUser=null;
	boolean flag1 = true;
	boolean flag2=true;
	public PersonalUi() {
		super();
	}
	public PersonalUi(JFrame fatherJframe,User loginUser) {
		super(fatherJframe,true);
		this.loginUser=loginUser;
		
		
		setDefault();
		CreateJFrame("个人中心");
		initJFram();
		
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
		
	}
	public void initJFram() {
		setSize(600, 700);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false); // 设置窗体大小不可变
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	public JPanel setNorth() {
		JPanel jPanelNorth=new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));
        jPanelNorth.add(UiUtils.getTitleLabel("个人信息"));
        return jPanelNorth;
	}
	
	public JPanel setSouth() {
		JPanel jPs = new JPanel(new GridLayout(2,1,0,10));
		JPanel jpL1=new JPanel(new FlowLayout(FlowLayout.CENTER,30,10));
		jPs.add(jpL1);
		jPs.add(UiUtils.getLabel(""));
		JButton jb = UiUtils.getButton("保存");
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if(flag1&&flag2) {
					UserService.modifyInfo(user);
					loginUser=user;
					JOptionPane.showMessageDialog(null, "修改成功");
				}else {
					JOptionPane.showMessageDialog(null, "信息有误，请检查","保存失败", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		JButton je= UiUtils.getButton("返回");
		je.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				PersonalUi.this.dispose();
			}
		});
		jpL1.add(jb);
		jpL1.add(je);
		return jPs;
	}
	public JPanel setCenter() {
		JPanel jpF = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		JPanel jpc1 = new JPanel(new GridLayout(6, 1, 50, 60));
		JPanel jpc2 = new JPanel(new GridLayout(6, 2, 50, 50));
		jpc1.add(UiUtils.getLabel("用户名:", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT));
		jpc1.add(UiUtils.getLabel("密    码:", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT));
		jpc1.add(UiUtils.getLabel("性别:", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT));
		jpc1.add(UiUtils.getLabel("年龄:", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT));
		jpc1.add(UiUtils.getLabel("手机号:", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT));
		
		
		JLabel[] jmsg = new JLabel[5];
		for (int i = 0; i < 5; i++) {
			jmsg[i] = UiUtils.getLabel(" ");
			jmsg[i].setHorizontalAlignment(JLabel.LEFT);
		}
		JTextField jtUserName = UiUtils.getJTextField(user.getUser_name());
		jtUserName.setEnabled(false);
		jpc2.add(jtUserName);
		jpc2.add(jmsg[0]);
		JButton jbalterPassword= UiUtils.getButton("修改密码");
		jbalterPassword.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				ModifyPasswordUi modifyPasswordUi=new ModifyPasswordUi(PersonalUi.this, loginUser);
				loginUser=modifyPasswordUi.getUser();
				user.setUser_password(loginUser.getUser_password());
			}
		});
		jpc2.add(jbalterPassword);
		jpc2.add(jmsg[1]);
		
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
		if(user.getUser_sex().equals("男")) {
			jrb1.setSelected(true);
		}else {
			jrb2.setSelected(true);
		}
		jpc2.add(jrb1);
		jpc2.add(jrb2);
		
		JTextField jtAge = UiUtils.getJTextField(user.getUser_age().toString());
		jtAge.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				JTextField j = (JTextField) e.getSource();
				try {
					int age=new Integer(j.getText());
					if (age<0) {
						throw new Exception();
					}
					flag1=true;
					jmsg[3].setText("  √");
					jmsg[3].setForeground(Color.GREEN);
					user.setUser_age(age);
				} catch (Exception e2) {
					jmsg[3].setForeground(Color.RED);
					jmsg[3].setText("  年龄输入错误");
					flag1=false;
				}
			}
		});
		jpc2.add(jtAge);
		jpc2.add(jmsg[3]);
	
		JTextField jtPhone = UiUtils.getJTextField(user.getUser_phone());
		jtPhone.setColumns(10);
		jtPhone.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				JTextField j = (JTextField) e.getSource();
				if(j.getText().equals("")) {
					flag2=false;
					jmsg[4].setForeground(Color.RED);
					jmsg[4].setText("  请输入手机号");
				}else {
					user.setUser_phone(j.getText());
					jmsg[4].setText("  √");
					jmsg[4].setForeground(Color.GREEN);
					flag2=true;
				}
			}
		});
		jpc2.add(jtPhone);
		jpc2.add(jmsg[4]);

		jpF.add(jpc1);
		jpF.add(jpc2);
		
		return jpF;
	}
	public User getUser() {
		return user;
	}
}
