package com.ui.ShowBills;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

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
import com.ui.AddBills.ActionSetUi;
import com.utils.UiUtils;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

/**
 * 修改账单页面
 */

public class BillEditUi extends JDialog {
	//登陆用户信息
	private User loginUser=null;
	//要修改的账单
	private Bill bill=null;

	//动作列表
	private List<Action> actionList=null;

	//动作类型
	private int actionType=0;

	//动作列表数据模型
	private DefaultComboBoxModel<String> actionDefaultComboBoxModel=new DefaultComboBoxModel<String>();

	//选择收入或支出下拉列表框
	private JComboBox<String> actionTypeListBox=new JComboBox<String>(new String[]{"支出","收入"});

	//选择事件下拉列表框
	private JComboBox<String> actionListBox=new JComboBox<String>();

	//金额输入框
	private JTextField jtMoney = UiUtils.getJTextField();

	//日期数据模型
	private UtilDateModel dateModel = new UtilDateModel();
	//日期面板
	private JDatePanelImpl datePanelImpl=new JDatePanelImpl(dateModel);
	//日期选择器
	private JDatePickerImpl jd=new JDatePickerImpl(datePanelImpl);

	//备注输入框
	private JTextField jTMark= UiUtils.getJTextField();

	public BillEditUi() {
		// TODO 自动生成的构造函数存根
	}
	public BillEditUi(JDialog jffather,User loginUser,Bill bill) {
		super(jffather,true	);
		this.loginUser=loginUser;
		this.bill=bill;



		initJFram("记账");
		setDefault();
		//设置默认动作类型,将触发类型更换事件
		List<Action> actionAll=ActionService.getAllActions(loginUser);
		for(Action ac:actionAll){
			if(ac.getId()==bill.getAction_id()){
				actionTypeListBox.setSelectedIndex(ac.getType());
				break;
			}
		}
		//设置默认动作，
		for(int i=0;i<actionList.size();i++){
			if(actionList.get(i).getId()==bill.getAction_id()){
				actionListBox.setSelectedIndex(i);
				break;
			}
		}




		System.out.println("node 0");
		setVisible(true);



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
		setResizable(false); // 设置窗体大小不可变
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	//加载时设置默认值
	private void setDefault() {
		//设置动作列表的默认选择值

		jtMoney.setText(bill.getMoney().toString());
		dateModel.setValue(bill.getDate());
		jTMark.setText(bill.getNote());
		System.out.println(bill);
	}
	/**
	 * 设置标题
	 * @return
	 */
	private JPanel setNorth() {
		JPanel jPanelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		jPanelNorth.add(UiUtils.getTitleLabel("修改账单"));
		return jPanelNorth;
	}

	/**
	 * 设置中间布局
	 * @return
	 */
	private JPanel setCenter() {


		//大面板
		JPanel jpF = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
		//面板左侧
		JPanel jpc1 = new JPanel(new GridLayout(6, 1, 50, 60));

		//面板右侧
		JPanel jpc2 = new JPanel(new GridLayout(6, 1, 50, 50));

		//第二行收支类型动态标签
		JLabel jEventTypeLabel;
		{
			jpc1.add(UiUtils.getLabel("记录类型:", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT));
			jEventTypeLabel= UiUtils.getLabel("支出类型:", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT);
			jpc1.add(jEventTypeLabel);
			jpc1.add(UiUtils.getLabel("金额(￥)：", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT));
			jpc1.add(UiUtils.getLabel("日期：", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT));
			jpc1.add(UiUtils.getLabel("备注：", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT));
		}


		//右侧
		jpc2.add(actionTypeListBox);
		actionTypeListBox.setSelectedIndex(0);
		actionTypeListBox.setFont(UiUtils.f16);
		actionTypeListBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO 自动生成的方法存根
				if(e.getStateChange()==ItemEvent.SELECTED) {
					actionType=actionTypeListBox.getSelectedIndex();
					String t=(actionType==0)?"支出":"收入";
					jEventTypeLabel.setText(t+"类型");
					actionList= ActionService.getAllActions(loginUser,actionType);
					updateActionListBox();
				}
			}
		});
		actionTypeListBox.setSize(100, 20);


//动作列表设置
		actionList= ActionService.getAllActions(loginUser,actionType);
		actionListBox.setFont(UiUtils.f16);
		actionListBox.setModel(actionDefaultComboBoxModel);
		updateActionListBox();

		//动作列表：管理面板
		JPanel jpevent=new JPanel(new GridLayout(1,2, 20, 0));
		jpc2.add(jpevent);

		JButton jeset= UiUtils.getButton("管理");
		jeset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				ActionSetUi actionSetUi =new ActionSetUi(BillEditUi.this, loginUser, actionType);
				actionList= ActionService.getAllActions(loginUser,actionType);
				updateActionListBox();
			}
		});
		jpevent.add(actionListBox);
		jpevent.add(jeset);
		jpc2.add(jtMoney);
		
		jd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				
			}
		});
		
		jpc2.add(jd);
		jd.setFont(UiUtils.f16);
		
		jpc2.add(jTMark);
		
		
		jpF.add(jpc1);
		jpF.add(jpc2);

		
		return jpF;
	}

	/**
	 * 设置底部布局
	 * @return
	 */
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
				try {
					float momey=new Float(jtMoney.getText());
					if (momey<0||dateModel.getValue()==null) {
						throw new Exception();
					}
					bill.setAction_id(actionList.get(actionListBox.getSelectedIndex()).getId());
					bill.setDate(dateModel.getValue());
					bill.setMoney(momey);
					bill.setNote(jTMark.getText());
					BillService.editBill(bill);
					BillEditUi.this.dispose();
				}catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "金额或日期错误","保存失败", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton je= UiUtils.getButton("返回");
		je.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				BillEditUi.this.dispose();
			}
		});
		jpL1.add(jb);
		jpL1.add(je);
		System.out.println("面板下方初始化完成");

		return jPs;
	}
	public Bill getBill() {
		return bill;
	}

	private void updateActionListBox(){
		actionDefaultComboBoxModel.removeAllElements();
		for (Action ac:actionList){
			actionDefaultComboBoxModel.addElement(ac.getName());
		}
	}
}
