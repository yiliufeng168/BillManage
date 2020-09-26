package com.ui;

import javax.swing.*;

import com.beans.User;
import com.biz.UserBiz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Ui extends JFrame {
    private User user=new User();
 
    public void CreateJFrame(String title){
        setTitle(title);
        setLayout(new BorderLayout());      //边界布局
        Container container=getContentPane();

        container.add(BorderLayout.NORTH,setNorth());//设置页面北部
        container.add(BorderLayout.CENTER,setCenter());//设置页面中部
        container.add(BorderLayout.SOUTH,setSouth());   //设置页面南部

        container.setBackground(Color.white);
        initJFram();
    }

    public void initJFram(){


        setSize(500,400);
        setVisible(true);
        setResizable(false);    //设置窗体大小不可变
        setLocationRelativeTo(null);    //设置窗体居中
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public JPanel setNorth(){
        //流式布局
        JPanel jPanelNorth=new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));
        jPanelNorth.add(Ctrols.getTitleLabel("个人记账系统"));
        return jPanelNorth;
    }
    public JPanel setCenter(){
        //流式布局
        JPanel jPCenter=new JPanel(new FlowLayout(FlowLayout.CENTER,80,40));
        JPanel jcol=new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
        JPanel jc1=new JPanel(new GridLayout(2,1,0,30));
        JPanel jc2=new JPanel(new GridLayout(2,1,0,30));
        jcol.add(jc1);
        jcol.add(jc2);
        jc1.add(Ctrols.getLabel("用户名:"));
        jc1.add(Ctrols.getLabel("密  码:"));

        JTextField jt1=Ctrols.getJTextField();
        jt1.setColumns(14);
        jt1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                user.setUser_name(jt1.getText());
            }
        });
        jc2.add(jt1);
        JPasswordField jPasswordField=Ctrols.getJPasswordField();
        jPasswordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                user.setUser_password(new String(jPasswordField.getPassword()));
            }
        });
        jc2.add(jPasswordField);

        jPCenter.add(jcol);
        return jPCenter;
    }

    public JPanel setSouth(){
        //流式布局
        JPanel jP=new JPanel(new GridLayout(2,1,0,30));
        JPanel jpL1=new JPanel(new FlowLayout(FlowLayout.CENTER,30,10));
        jP.add(jpL1);
        jP.add(Ctrols.getLabel(""));
        JButton jb1=Ctrols.getButton("登录");
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(user.getUser_name()==null||user.getUser_name().equals("")) {
            		JOptionPane.showMessageDialog(null, "用户名不能为空","登录失败", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	if(user.getUser_password()==null||user.getUser_password().equals("")) {
            		JOptionPane.showMessageDialog(null, "密码不能为空","登录失败", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
                User loginUser=UserBiz.Login(user);
                if(loginUser!=null){
                    Menu menu=new Menu("菜单",loginUser);
                    JOptionPane.showMessageDialog(null, "欢迎进入");
                    
                    Ui.this.dispose();
                }else {
                	JOptionPane.showMessageDialog(null, "用户名或密码错误","登录失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton jb2=Ctrols.getButton("注册");
        jb2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("点击了注册");
				RegistUI registUI=new RegistUI(Ui.this);
			}
		});
        jpL1.add(jb1);
        jpL1.add(jb2);

        return jP;
    }


}
