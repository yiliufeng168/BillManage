package com.ui.AddBills;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.*;

import java.util.ArrayList;
import java.util.List;


import javax.swing.DefaultComboBoxModel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.beans.Bill;
import com.beans.Action;
import com.beans.User;

import com.service.ActionService;
import com.service.BillService;
import com.utils.MyWindowListener;
import com.utils.UiUtils;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

/**
 * 添加收支账单
 */

public class WriteBillUi extends JFrame  {
	//保存用户信息
	private User loginUser=null;

	//保存当前编写的账单类型
	private int actionType=0;

	//保存当前动作列表
	private List<Action> actions=new ArrayList<Action>();

	//账单类型文字描述
	private String[] actionTypeStrList= {"支出","收入"};

	//动作列表数据模型
	private DefaultComboBoxModel<String> actionDefaultComboBoxModel=new DefaultComboBoxModel<String>();


	//父窗体
	private JFrame menuJFrame;
	/**
	 * 下方为控件
	 */
	//账单类型下拉列表框
	private JComboBox<String> actionTypeListBox=new JComboBox<String>(actionTypeStrList);
	
	//动作列表下拉列表框
	private JComboBox<String> actionListBox=new JComboBox<String>();

	//金额输入框
	private JTextField jtMoney = UiUtils.getJTextField();

	//日期工具模型
	private UtilDateModel dateModelmodel = new UtilDateModel();
	//日期面板
	private JDatePanelImpl datePanelImpl=new JDatePanelImpl(dateModelmodel);
	//日期选取器
	private JDatePickerImpl jd=new JDatePickerImpl(datePanelImpl);
	//备注输入框
	private JTextField jTMark= UiUtils.getJTextField();
	
	
	public WriteBillUi() {
		// TODO 自动生成的构造函数存根
	}
	public WriteBillUi(JFrame jffather,User loginUser) {
		super();

		this.loginUser=loginUser;
		this.menuJFrame=jffather;
		initJFram("记账");
		
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
		setSize(600, 700);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false); // 设置窗体大小不可变
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		addWindowListener(new MyWindowListener(menuJFrame));
	}
	
	//窗体上方布局
	private JPanel setNorth() {
		JPanel jPanelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		//标题
		jPanelNorth.add(UiUtils.getTitleLabel("新建账单"));
		return jPanelNorth;
	}
	//窗体中间布局
	private JPanel setCenter() {
		
		JPanel jpF = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
		//面板左侧设置
		JPanel jpc1 = new JPanel(new GridLayout(6, 1, 50, 60));
		//面板右侧设置
		JPanel jpc2 = new JPanel(new GridLayout(6, 1, 50, 50));
		jpc1.add(UiUtils.getLabel("记录类型:", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT));
		JLabel actionTypeLabel= UiUtils.getLabel("支出类型:", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT);
		jpc1.add(actionTypeLabel);
		jpc1.add(UiUtils.getLabel("金额(￥)：", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT));
		jpc1.add(UiUtils.getLabel("日期：", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT));
		jpc1.add(UiUtils.getLabel("备注：", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT));
		
		actionTypeListBox.setSelectedIndex(0);
		actionTypeListBox.setFont(UiUtils.f16);

		//更换动作类型后，动作列表发生改变
		actionTypeListBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO 自动生成的方法存根
				if(e.getStateChange()==ItemEvent.SELECTED) {
					actionType=actionTypeListBox.getSelectedIndex();
					String t=(actionType==0)?"支出":"收入";
					actionTypeLabel.setText(t+"类型");

					updateActionListBox();
//					actionListBox.setModel(tdcm);
					System.out.println("WriteBillUI Line153:"+"更换动作类型触发");
				}
			}
		});
		jpc2.add(actionTypeListBox);
		actionTypeListBox.setSize(100, 20);
		

		//动作列表
		JPanel jpevent=new JPanel(new GridLayout(1,2, 20, 0));


		updateActionListBox();
		actionListBox.setFont(UiUtils.f16);
		actionListBox.setModel(actionDefaultComboBoxModel);


		
		JButton actionSetButton= UiUtils.getButton("管理");
		actionSetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				ActionSetUi actionSetUi =new ActionSetUi(WriteBillUi.this, loginUser, actionType);
				updateActionListBox();
				System.out.println("WriteBillUI Line189:"+"设置动作类型触发");
			}
		});

		jpevent.add(actionListBox);
		jpevent.add(actionSetButton);

		jpc2.add(jpevent);
		jpc2.add(jtMoney);
		

		jpc2.add(jd);
	
		jd.setFont(UiUtils.f24);
		jpc2.add(jTMark);
		
		
		jpF.add(jpc1);
		jpF.add(jpc2);
		
		return jpF;
	}
	//窗体下方布局
	private JPanel setSouth() {
		JPanel jPs = new JPanel(new GridLayout(2,1,0,10));
		JPanel jpL1=new JPanel(new FlowLayout(FlowLayout.CENTER,30,10));
		jPs.add(jpL1);
		jPs.add(UiUtils.getLabel(""));
		JButton jb = UiUtils.getButton("保存");
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				saveBill();
				WriteBillUi.this.dispose();
			}
		});
		JButton jbnext = UiUtils.getButton("保存并编辑下一个");
		jbnext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				saveBill();
				jtMoney.setText("");
				dateModelmodel.setValue(null);
				jTMark.setText("");
			}
		});
		JButton je= UiUtils.getButton("返回");
		je.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				
				WriteBillUi.this.dispose();
			}
		});
		jpL1.add(jb);
		jpL1.add(jbnext);
		jpL1.add(je);
		return jPs;
	}
	
	private void saveBill() {
		float momey=new Float(jtMoney.getText());
		if (momey<0||dateModelmodel.getValue()==null) {
			JOptionPane.showMessageDialog(null, "金额或日期错误","保存失败", JOptionPane.ERROR_MESSAGE);
			return;
		}
		//保存账单信息
		Bill bill=new Bill();
		bill.setDate(dateModelmodel.getValue());
		bill.setAction_id(actions.get(actionListBox.getSelectedIndex()).getId());
		bill.setUser_id(loginUser.getUser_id());
		bill.setMoney(momey);
		bill.setNote(jTMark.getText());
		BillService.addBill(bill);
	}
	private void updateActionListBox(){
		actions=ActionService.getAllActions(loginUser,actionType);
		actionDefaultComboBoxModel.removeAllElements();
		for(Action li:actions) {
			actionDefaultComboBoxModel.addElement(li.getName());
		}
	}
}
