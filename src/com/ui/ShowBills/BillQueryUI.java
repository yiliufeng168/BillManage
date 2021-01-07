package com.ui.ShowBills;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.beans.Bill;
import com.beans.Action;
import com.beans.User;

import com.service.ActionService;
import com.service.BillService;
import com.utils.DateConverUtils;

import com.utils.MyWindowListener;
import com.utils.UiUtils;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

/**
 * 账单查询页面
 */

public class BillQueryUI extends JDialog{
	//当前登陆用户
	private User loginUser=null;

	//动作列表
	private List<Action> actionList =null;		//存储当前收支类型下的事件
	//账单列表
	private List<Bill> billList=null;

	//动作列表数据模型
	private DefaultComboBoxModel<String> actionDefaultComboBoxModel=new DefaultComboBoxModel<String>();
	
	//账单类型，选择下拉框
	JComboBox<String> jBillTypeBox=new JComboBox<String>(new String[]{"全部","支出","收入"});
	
	//类型列表选择下拉框（包含全部）
	JComboBox<String> jActionListBox=new JComboBox<String>();
	
	//起始日期设置栏
	UtilDateModel modelStart = new UtilDateModel();
	JDatePanelImpl dPImplStart=new JDatePanelImpl(modelStart);
	JDatePickerImpl jDateStart=new JDatePickerImpl(dPImplStart);
	
	//截至日期设置栏
	UtilDateModel modelEnd = new UtilDateModel();
	JDatePanelImpl dPImplEnd=new JDatePanelImpl(modelEnd);
	JDatePickerImpl jDateEnd=new JDatePickerImpl(dPImplEnd);
	
	//返回主菜单按钮
	JButton jExit= UiUtils.getButton("返回主菜单");
	//查询按钮
	JButton jQuery= UiUtils.getButton("查询");
	//删除按钮
	JButton jDel= UiUtils.getButton("删除");
	//编辑
	JButton jEdit= UiUtils.getButton("编辑");
			
	//最小金额
	JTextField jMinMoney= UiUtils.getJTextField();
	//最大金额
	JTextField jMaxMoney= UiUtils.getJTextField();
	
	//表格
	JTable jtable=null;
	Vector<Vector<String>> tableValue=null;
	Vector<String> columnNameV=null;
	DefaultTableModel dtm=new DefaultTableModel();
	
	
	JLabel jGet= UiUtils.getLabel("收入:", UiUtils.f24);
	JLabel jPay= UiUtils.getLabel("支出:", UiUtils.f24);
	JLabel jAll= UiUtils.getLabel("共计:", UiUtils.f24);


	public BillQueryUI(JFrame jffather,User loginUser) {
		super(jffather,true);
		this.loginUser=loginUser;
		this.actionList = ActionService.getAllActions(loginUser);
		addWindowListener(new MyWindowListener(jffather));
		initJFram("账单");
	}


	//初始化窗体设置
	public void initJFram(String title) {
		setTitle(title);
		setLayout(new BorderLayout()); // 边界布局
		Container container = getContentPane();
//		setResizable(false);
		container.add(BorderLayout.NORTH, setNorth());// 设置页面北部
		container.add(BorderLayout.CENTER, setCenter());// 设置页面中部
		container.add(BorderLayout.SOUTH, setSouth()); // 设置页面南部
		container.setBackground(Color.white);
		setSize(1600, 800);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false); // 设置窗体大小不可变
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	//设置上方的布局
	private JPanel setNorth() {
		JPanel jpN=new JPanel(new FlowLayout(FlowLayout.CENTER,0,20));//第一层
		JPanel jpni=new JPanel(new GridLayout(2,1,40,10));//第二层
		jpN.add(jpni);
		jpni.add(UiUtils.getTitleLabel(loginUser.getUser_name()+"的账单"));
		JPanel jpna=new JPanel(new GridLayout(2,8,20,10));//第三层
		
		jpni.add(jpna);
		jpna.add(UiUtils.getLabel("账单类型:"));
		jpna.add(jBillTypeBox);
		
		jpna.add(UiUtils.getLabel("起始日期:"));
		jpna.add(jDateStart);
		jpna.add(UiUtils.getLabel("最小金额:"));
		jpna.add(jMinMoney);
		jpna.add(jQuery);
		
		jpna.add(UiUtils.getLabel("收支类型:"));
		jpna.add(jActionListBox);
		jpna.add(UiUtils.getLabel("截至日期:"));
		jpna.add(jDateEnd);
		jpna.add(UiUtils.getLabel("最大金额:"));
		jpna.add(jMaxMoney);
		jpna.add(jExit);

		//设置当前收支类型下的事件
		jActionListBox.setModel(actionDefaultComboBoxModel);
		actionList = ActionService.getAllActions(loginUser);
		System.out.println("action:"+actionList);
		updateActionsListBox();
		jActionListBox.setFont(UiUtils.f16);
		jBillTypeBox.setFont(UiUtils.f16);
	
		//账单类型下拉框监听事件
		jBillTypeBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO 自动生成的方法存根
				if(e.getStateChange()==ItemEvent.SELECTED) {

					if(jBillTypeBox.getSelectedIndex()==0) {
						//选择全部
						actionList = ActionService.getAllActions(loginUser);
						updateActionsListBox();
					}else {
						//选择收入或者支出
						actionList = ActionService.getAllActions(loginUser,jBillTypeBox.getSelectedIndex()-1);
						updateActionsListBox();
					}
				}
			}
		});
		
		//查询按钮监听事件
		jQuery.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				Float minMoney=null;
				Float maxMoney=null;
				
				//验证最小金额
				if(!jMinMoney.getText().equals("")) {
					try {
						minMoney=new Float(jMinMoney.getText());
						if (minMoney<0) {
							throw new Exception();
						}
					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "最小金额设置错误","查询失败", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				//验证最大金额
				if(!jMaxMoney.getText().equals("")) {
					try {

						maxMoney=new Float(jMaxMoney.getText());
						if (maxMoney<0) {
							throw new Exception();
						}
					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "最大金额设置错误","查询失败", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				//获取动作编号
				Integer action_id=null;
				if(jActionListBox.getSelectedIndex()!=0) {
					int chooseEvent=jActionListBox.getSelectedIndex()-1;
					action_id= actionList.get(chooseEvent).getId();
				}
				//获取账单类型
				Integer bill_type=null;
				if(jBillTypeBox.getSelectedIndex()!=0) {
					bill_type=jBillTypeBox.getSelectedIndex()-1;
				}
				billList= BillService.getAllBilList(loginUser,bill_type ,action_id, modelStart.getValue(), modelEnd.getValue(), minMoney, maxMoney);
				System.out.println("点击查询按钮后bills");
				System.out.println(billList);
				dtm.setDataVector(getTableValue(),columnNameV);
			}
		});
		//返回主菜单监听事件
		jExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				BillQueryUI.this.dispose();
			}
		});
		return jpN;
	}
	
	//设置中间布局
	private JPanel setCenter() {
		JPanel jC=new JPanel(new BorderLayout());//第一层
		columnNameV=new Vector<String>();
		columnNameV.add("账单编号");
		columnNameV.add("时间");

		columnNameV.add("类型");
		columnNameV.add("事件");
		columnNameV.add("备注");
		columnNameV.add("金额(￥)");
		billList=BillService.getAllBilList(loginUser,null, null, null, null, null, null);
		Vector<Vector<String>> tableValue=getTableValue();

		dtm.setDataVector(tableValue,columnNameV);
		jtable=new MyJtable();
		jtable.setModel(dtm);
		
		jtable.setFont(UiUtils.f24);
		jtable.setRowHeight(36);
		jtable.setSelectionMode(0);//只允许选择一个
		JScrollPane js=new JScrollPane(jtable);
		js.setSize(1200, 400);
		
		JPanel jright=new JPanel(new FlowLayout(FlowLayout.CENTER,15,60));
		JPanel jright_in=new JPanel(new GridLayout(2,1,10,30));
		jright.add(jright_in);
		jright_in.add(jEdit);

		//编辑按钮监听事件
		jEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if(jtable.getSelectedRow()==-1) {
					JOptionPane.showMessageDialog(null, "请选择要编辑的一行数据","编辑失败", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int select_bill_id=jtable.getSelectedRow();
				BillEditUi billEditUi=new BillEditUi(BillQueryUI.this,loginUser,billList.get(select_bill_id));
				billList.set(select_bill_id,billEditUi.getBill());
				dtm.setDataVector(getTableValue(),columnNameV);
			}
		});
		jright_in.add(jDel);
		//删除按钮监听事件
		jDel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if(jtable.getSelectedRow()==-1) {
					JOptionPane.showMessageDialog(null, "请选择要删除的一行数据","删除失败", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int bill_id=Integer.parseInt(tableValue.elementAt(jtable.getSelectedRow()).elementAt(0));
				int i=JOptionPane.showConfirmDialog(null, "确认删除？","删除",JOptionPane.YES_NO_OPTION);
				System.out.println(i);
				if(i==0) {
					dtm.removeRow(jtable.getSelectedRow());

					for(Bill b:billList) {
						if(b.getId()==bill_id) {
							BillService.delBill(b);
							billList.remove(b);
							break;
						}
					}
					getTableValue();
				}
			}
		});
		jC.add(BorderLayout.CENTER,js);
		jC.add(BorderLayout.WEST, UiUtils.getLabel("                 "));
		jC.add(BorderLayout.EAST,jright );
		return jC;
	}
	
	//设置下方布局
	private JPanel setSouth() {
		JPanel jps=new JPanel(new FlowLayout(FlowLayout.CENTER,20,30));
		jps.add(jGet);
		jps.add(jPay);
		jps.add(jAll);
		return jps;
	}
	private void updateActionsListBox(){
		actionDefaultComboBoxModel.removeAllElements();
		actionDefaultComboBoxModel.addElement("--全部--");

		for(Action li: actionList) {
			actionDefaultComboBoxModel.addElement(li.getName());
		}
	}
	private Vector<Vector<String>> getTableValue(){
		Vector<Vector<String>> tv=new Vector<Vector<String>>();
		float get=0f;
		float pay=0f;
		float all=0f;
		for (Bill bill:billList ) {
            String action_name=null;

            String action_type_name=null;
            Vector<String> rowV=new Vector<String>();
            //遍历动作列表，找到账单类型，和动作名
			for(int i=0;i<actionList.size();i++){
				if(actionList.get(i).getId()==bill.getAction_id()){
					action_name=actionList.get(i).getName();
					int action_type=actionList.get(i).getType();
					action_type_name = ((action_type==0)?"支出":"收入");

					//对支出和收入进行累加
					if(action_type==0){
						pay+=bill.getMoney();
					}else if (action_type==1){
						get+=bill.getMoney();
					}else {
						System.out.println("账单累加异常");
					}
					break;
				}
			}

            all=get-pay;
            rowV.add(bill.getId().toString());
            rowV.add(DateConverUtils.convertDateToString(bill.getDate()));
            rowV.add(action_type_name);
			rowV.add(action_name);
            rowV.add(bill.getNote());
			rowV.add(bill.getMoney().toString());
            tv.add(rowV);
            
		}
		jGet.setText("  收入:  "+get+"元");
        jPay.setText("  支出:  "+pay+"元");
        jAll.setText("  共计:  "+all+"元");
		return tv;
	}
	class MyJtable extends JTable{
		@Override
		public boolean isCellEditable(int row, int column) {
			// TODO 自动生成的方法存根
			return false;
		}
		public MyJtable() {
			// TODO 自动生成的构造函数存根
		}
		public MyJtable(Vector<Vector<String>> tableValue, Vector<String> columnNameV) {
			// TODO 自动生成的构造函数存根
			super(tableValue, columnNameV);
		}
		
	}
}
