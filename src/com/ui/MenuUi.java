package com.ui;

import javax.swing.*;

import com.beans.User;
import com.ui.AddBills.WriteBillUi;
import com.ui.PersonalInfo.PersonalUi;

import com.ui.ShowBills.BillQueryUI;
import com.utils.UiUtils;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 主菜单
 */
public class MenuUi extends JFrame {
	User loginUser = null;

	public MenuUi() {
		// TODO 自动生成的构造函数存根
		super();
	}

	public MenuUi(String title, User loginUser) throws HeadlessException {
		super(title);
		this.loginUser = loginUser;
		CreateJFrame("主菜单");
	}

	public void CreateJFrame(String title) {
		setTitle(title);
		setLayout(new BorderLayout()); // 边界布局
		Container container = getContentPane();
		container.add(BorderLayout.NORTH, setNorth());// 设置页面北部
		container.add(BorderLayout.CENTER, setCenter());// 设置页面中部
		container.setBackground(Color.white);
		initJFram();
	}

	public JPanel setNorth() {
		JPanel jPanelNorth=new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));
        jPanelNorth.add(UiUtils.getTitleLabel("主菜单"));
        return jPanelNorth;
	}

	public JPanel setCenter() {
		JPanel jP=new JPanel(new FlowLayout(FlowLayout.CENTER,0,50));
		JPanel jGrid=new JPanel(new GridLayout(5,1,0,60));
		jP.add(jGrid);
		JButton jbjz= UiUtils.getButton("记账");
		jbjz.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				setVisible(false);
				WriteBillUi writeBillUi=new WriteBillUi(MenuUi.this,loginUser);

			}
		});
		
		
		
		JButton jbzd= UiUtils.getButton("账单");
		jbzd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				setVisible(false);
				BillQueryUI billQueryUI=new BillQueryUI(MenuUi.this, loginUser);
				
			}
		});
		JButton jbgr= UiUtils.getButton("个人中心");
		jbgr.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				PersonalUi personalUi=new PersonalUi(MenuUi.this,loginUser);
				loginUser=personalUi.getUser();
			}
		});
		JButton jbtc= UiUtils.getButton("退出");
		jbtc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				MenuUi.this.dispose();
			}
		});
		JLabel jl= UiUtils.getLabel("                           ");//占位，控制宽度
		jGrid.add(jbjz);
		jGrid.add(jbzd);
		jGrid.add(jbgr);
		jGrid.add(jbtc);
		jGrid.add(jl);
		return jP;
	}
	

	public void initJFram() {

		setSize(500, 600);
		setVisible(true);
		setResizable(false); // 设置窗体大小不可变
		setLocationRelativeTo(null); // 设置窗体居中
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
