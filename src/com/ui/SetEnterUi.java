package com.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.UUID;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.beans.Eventr;
import com.beans.User;
import com.biz.BillBiz;
import com.biz.EventBiz;

public class SetEnterUi extends JDialog {
	User loginUser=null;
	String eventType=null;
	int eventInt=0;
	public SetEnterUi() {
		// TODO 自动生成的构造函数存根
	}
	public SetEnterUi(JDialog fa,User loginUser,int eventType) {
		super(fa,true);
		this.loginUser=loginUser;
		this.eventType=(eventType==0)?"支出":"收入";
		this.eventInt=eventType;
		CreateJFrame(this.eventType+"类型管理");
		initJFram();
		
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
		setSize(600, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false); // 设置窗体大小不可变
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	private JPanel setNorth() {
		JPanel jPanelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
		jPanelNorth.add(Ctrols.getTitleLabel(eventType+"类型管理"));
		return jPanelNorth;
	}
	private JPanel setCenter() {
		JPanel jc=new JPanel(new FlowLayout(FlowLayout.CENTER,50,50));
		
		JPanel jc2=new JPanel(new GridLayout(3,1,0,30));
		
		
		List<Eventr> lis=EventBiz.getAllEvents(loginUser,eventInt);
		DefaultListModel<Eventr> dlm=new DefaultListModel<Eventr>();
		int i=0;
		for(Eventr li:lis) {
			dlm.add(i++,li);
		}
		JList<Eventr> jl=new JList<Eventr>();
		jl.setModel(dlm);
		
		jl.setFont(Ctrols.f16);
		
		JScrollPane jcs=new JScrollPane(jl);
		jcs.setPreferredSize(new Dimension(400,200));
		
		JButton jadd=Ctrols.getButton("添加");
		jadd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				String nEv=JOptionPane.showInputDialog(null,"请输入"+eventType+"类型","添加",JOptionPane.PLAIN_MESSAGE);
				if(nEv!=null) {
	
					Eventr nEventr=new Eventr(null,loginUser.getUser_id(),nEv,eventInt);
					if(!EventBiz.checkEventExist(nEventr)) {
						nEventr.setEvent_id(EventBiz.registEv(nEventr));
						dlm.add(dlm.getSize(), nEventr);
					}else {
						JOptionPane.showMessageDialog(null, "类型重复","失败", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		JButton jedit=Ctrols.getButton("编辑");
		jedit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				int selectIndex=jl.getSelectedIndex();
				if(selectIndex!=-1) {
					Eventr f=dlm.getElementAt(selectIndex);
					String nEv=(String) JOptionPane.showInputDialog(null,"请输入"+eventType+"类型","编辑",JOptionPane.PLAIN_MESSAGE,null,null,f.getEvent_name());
					if(nEv!=null) {
						f.setEvent_name(nEv);
						EventBiz.modifyEv(f);
						dlm.set(selectIndex, f);
					}
				}
				

			}
		});
		JButton jdel=Ctrols.getButton("删除");
		jdel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				int selectIndex=jl.getSelectedIndex();
				if(selectIndex!=-1) {
					Eventr f=dlm.get(selectIndex);
					if(BillBiz.checkEventIdExist(f.getEvent_id())) {
						JOptionPane.showMessageDialog(null, "账单中包含该记录，无法删除","失败", JOptionPane.ERROR_MESSAGE);
					}else {
						EventBiz.delEv(f);
						dlm.remove(selectIndex);
					}

				}
			}
		});
		jc.add(jcs);
		jc.add(jc2);
		jc2.add(jadd);
		jc2.add(jedit);
		jc2.add(jdel);
		
		return jc;
	}
	private JPanel setSouth() {
		JPanel jPs = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
		JPanel jpi=new JPanel(new GridLayout(2,1,100,10));
		jPs.add(jpi);
		JButton je=Ctrols.getButton("返回");
		je.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				SetEnterUi.this.dispose();
			}
		});
		
		jpi.add(je);
		jpi.add(Ctrols.getLabel("                               "));
		return jPs;
	}
}
