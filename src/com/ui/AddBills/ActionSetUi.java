package com.ui.AddBills;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import com.beans.Action;
import com.beans.User;


import com.service.ActionService;
import com.service.BillService;
import com.utils.UiUtils;

/**
 * 管理收支类型页面
 */

public class ActionSetUi extends JDialog {
	//登陆用户
	private User loginUser=null;
	
	//动作类型
	private int actionType=0;

	//所有动作列表
	private List<Action> actionList;

	//动作数据模型
	private DefaultListModel<String>  actionDefaultListModel=new DefaultListModel<String>();

	public ActionSetUi() {
		// TODO 自动生成的构造函数存根
	}
	public ActionSetUi(JFrame fa, User loginUser, int actionType) {
		super(fa,true);
		this.loginUser=loginUser;
		this.actionType=actionType;
		initJFram(((actionType==0)?"支出":"收入")+"类型管理");
	}
	public ActionSetUi(JDialog fa, User loginUser, int actionType) {
		super(fa,true);
		this.loginUser=loginUser;
		this.actionType=actionType;
		initJFram(((actionType==0)?"支出":"收入")+"类型管理");
	}

	public void initJFram(String title) {
		setTitle(title);
		setLayout(new BorderLayout()); // 边界布局
		Container container = getContentPane();
		setResizable(false);
		container.add(BorderLayout.NORTH, setNorth());// 设置页面北部
		container.add(BorderLayout.CENTER, setCenter());// 设置页面中部
		container.add(BorderLayout.SOUTH, setSouth()); // 设置页面南部
		container.setBackground(Color.white);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false); // 设置窗体大小不可变
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	//窗体上方
	private JPanel setNorth() {
		JPanel jPanelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		jPanelNorth.add(UiUtils.getTitleLabel(((actionType==0)?"支出":"收入")+"类型管理"));
		return jPanelNorth;
	}
	//窗体中间
	private JPanel setCenter() {
		JPanel jc=new JPanel(new FlowLayout(FlowLayout.CENTER,50,50));
		
		JPanel jc2=new JPanel(new GridLayout(3,1,0,30));

		actionList= ActionService.getAllActions(loginUser,actionType);

		int i=0;
		for(Action li:actionList) {
			actionDefaultListModel.addElement(li.getName());
		}
		JList<String> jl=new JList<String>();
		jl.setModel(actionDefaultListModel);
		
		jl.setFont(UiUtils.f16);
		
		JScrollPane jcs=new JScrollPane(jl);
		jcs.setPreferredSize(new Dimension(400,200));
		
		JButton jadd= UiUtils.getButton("添加");

		jadd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				String nEv=JOptionPane.showInputDialog(null,"请输入"+((actionType==0)?"支出":"收入")+"类型","添加",JOptionPane.PLAIN_MESSAGE);
				if(nEv!=null) {
	
					Action nAction =new Action(null,loginUser.getUser_id(),nEv,actionType);
					if(!ActionService.checkActionExist(nAction)) {
						ActionService.addAction(nAction);
						updateActionBox();

					}else {
						JOptionPane.showMessageDialog(null, "类型重复","失败", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		JButton jedit= UiUtils.getButton("编辑");
		jedit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				int selectIndex=jl.getSelectedIndex();
				if(selectIndex!=-1) {
					Action f=actionList.get(selectIndex);
					String nEv=(String) JOptionPane.showInputDialog(null,"请输入"+((actionType==0)?"支出":"收入")+"类型","编辑",JOptionPane.PLAIN_MESSAGE,null,null,f.getName());
					if(nEv!=null) {
						f.setName(nEv);
						ActionService.editAction(f);
						actionDefaultListModel.set(selectIndex, f.getName());
					}
				}
				

			}
		});
		JButton jdel= UiUtils.getButton("删除");
		jdel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				int selectIndex=jl.getSelectedIndex();
				if(selectIndex!=-1) {
					Action f=actionList.get(selectIndex);
					if(BillService.checkActionIdExist(f.getId())) {
						JOptionPane.showMessageDialog(null, "账单中包含该记录，无法删除","失败", JOptionPane.ERROR_MESSAGE);
					}else {
						ActionService.delAction(f);
						updateActionBox();
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
		JButton je= UiUtils.getButton("返回");
		je.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				ActionSetUi.this.dispose();
			}
		});
		
		jpi.add(je);
		jpi.add(UiUtils.getLabel("                               "));
		return jPs;
	}

	private void updateActionBox(){
		actionList=ActionService.getAllActions(loginUser,actionType);
		actionDefaultListModel.removeAllElements();
		for (Action ac:actionList){
			actionDefaultListModel.addElement(ac.getName());
		}

	}
}
