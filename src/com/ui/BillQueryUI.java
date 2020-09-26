package com.ui;

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
import com.beans.Eventr;
import com.beans.User;
import com.biz.BillBiz;
import com.biz.EventBiz;
import com.utils.DateConverUtils;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;


public class BillQueryUI extends JDialog{
	User loginUser=null;
	String[] chooseList1= {"全部","支出","收入"};
	List<Eventr> eventrs=null;
	List<Bill> bills=null;
	
	//账单类型，选择下拉框
	JComboBox<String> jEventTypeBox=new JComboBox<String>(chooseList1);
	
	//类型列表选择下拉框（包含全部）
	JComboBox<Eventr> jEventListBox=new JComboBox<Eventr>();
	
	//起始日期设置栏
	UtilDateModel modelStart = new UtilDateModel();
	JDatePanelImpl dPImplStart=new JDatePanelImpl(modelStart);
	JDatePickerImpl jDateStart=new JDatePickerImpl(dPImplStart);
	
	//截至日期设置栏
	UtilDateModel modelEnd = new UtilDateModel();
	JDatePanelImpl dPImplEnd=new JDatePanelImpl(modelEnd);
	JDatePickerImpl jDateEnd=new JDatePickerImpl(dPImplEnd);
	
	//返回主菜单按钮
	JButton jExit=Ctrols.getButton("返回主菜单");
	//查询按钮
	JButton jQuery=Ctrols.getButton("查询");
	//删除按钮
	JButton jDel=Ctrols.getButton("删除");
	//编辑
	JButton jEdit=Ctrols.getButton("编辑");
			
	//最小金额
	JTextField jMinMoney=Ctrols.getJTextField();
	//最大金额
	JTextField jMaxMoney=Ctrols.getJTextField();
	
	//表格
	JTable jtable=null;
	Vector<Vector<String>> tableValue=null;
	Vector<String> columnNameV=null;
	DefaultTableModel dtm=new DefaultTableModel();
	
	
	JLabel jGet=Ctrols.getLabel("收入:",Ctrols.f24);
	JLabel jPay=Ctrols.getLabel("支出:",Ctrols.f24);
	JLabel jAll=Ctrols.getLabel("共计:",Ctrols.f24);
	public BillQueryUI(JFrame jffather,User loginUser) {
		super(jffather,true);
		this.loginUser=loginUser;
		this.eventrs=EventBiz.getAllEvents(loginUser);
		CreateJFrame("账单");
		initJFram();
	}
	public void CreateJFrame(String title) {
		setTitle(title);
		setLayout(new BorderLayout()); // 边界布局
		Container container = getContentPane();
//		setResizable(false);
		container.add(BorderLayout.NORTH, setNorth());// 设置页面北部
		container.add(BorderLayout.CENTER, setCenter());// 设置页面中部
		container.add(BorderLayout.SOUTH, setSouth()); // 设置页面南部
		container.setBackground(Color.white);
	}
	
	public void initJFram() {
		setSize(1600, 800);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false); // 设置窗体大小不可变
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	private JPanel setNorth() {
		JPanel jpN=new JPanel(new FlowLayout(FlowLayout.CENTER,0,20));//第一层
		JPanel jpni=new JPanel(new GridLayout(2,1,40,10));//第二层
		jpN.add(jpni);
		jpni.add(Ctrols.getTitleLabel(loginUser.getUser_name()+"的账单"));
		JPanel jpna=new JPanel(new GridLayout(2,8,20,10));//第三层
		
		jpni.add(jpna);
		jpna.add(Ctrols.getLabel("账单类型:"));
		jpna.add(jEventTypeBox);
		jpna.add(Ctrols.getLabel("起始日期:"));
		jpna.add(jDateStart);
		jpna.add(Ctrols.getLabel("最小金额:"));
		jpna.add(jMinMoney);
		jpna.add(jQuery);
		
		jpna.add(Ctrols.getLabel("收支类型:"));
		jpna.add(jEventListBox);
		jpna.add(Ctrols.getLabel("截至日期:"));
		jpna.add(jDateEnd);
		jpna.add(Ctrols.getLabel("最大金额:"));
		jpna.add(jMaxMoney);
		jpna.add(jExit);
		List<Eventr> list=EventBiz.getAllEvents(loginUser);
		jEventListBox.setModel(getComBoxModel(list));
	
		jEventListBox.setFont(Ctrols.f16);
		jEventTypeBox.setFont(Ctrols.f16);
	
		jEventTypeBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO 自动生成的方法存根
				if(e.getStateChange()==ItemEvent.SELECTED) {
					if(jEventTypeBox.getSelectedIndex()==0) {
						List<Eventr> list=EventBiz.getAllEvents(loginUser);
						jEventListBox.setModel(getComBoxModel(list));
					}else {
						List<Eventr> list=EventBiz.getAllEvents(loginUser,jEventTypeBox.getSelectedIndex()-1);
						jEventListBox.setModel(getComBoxModel(list));
					}
				}
			}
		});
		jQuery.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				Float minMoney=null;
				Float maxMoney=null;
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
				Integer event_id=null;
				if(jEventListBox.getSelectedIndex()!=0) {
					Eventr temp=(Eventr)jEventListBox.getSelectedItem();
					event_id=temp.getEvent_id();
				}
				Integer event_type=null;
				if(jEventTypeBox.getSelectedIndex()!=0) {
					event_type=jEventTypeBox.getSelectedIndex()-1;
				}
				bills=BillBiz.getAllBilList(loginUser,event_type ,event_id, modelStart.getValue(), modelEnd.getValue(), minMoney, maxMoney);
				dtm.setDataVector(getTableValue(),columnNameV);
			}
		});
		jExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				BillQueryUI.this.dispose();
			}
		});
		
		return jpN;
	}
	private JPanel setCenter() {
		JPanel jC=new JPanel(new BorderLayout());//第一层
		columnNameV=new Vector<String>();
		columnNameV.add("账单编号");
		columnNameV.add("时间");
		columnNameV.add("事件");
		columnNameV.add("类型");
		columnNameV.add("金额(￥)");
		columnNameV.add("备注");
		bills=BillBiz.getAllBilList(loginUser,null, null, null, null, null, null);
		Vector<Vector<String>> tableValue=getTableValue();

		dtm.setDataVector(tableValue,columnNameV);
		jtable=new MyJtable();
		jtable.setModel(dtm);
		
		jtable.setFont(Ctrols.f24);
		jtable.setRowHeight(36);
		jtable.setSelectionMode(0);//只允许选择一个
		JScrollPane js=new JScrollPane(jtable);
		js.setSize(1200, 400);
		
		JPanel jright=new JPanel(new FlowLayout(FlowLayout.CENTER,15,60));
		JPanel jright_in=new JPanel(new GridLayout(2,1,10,30));
		jright.add(jright_in);
		jright_in.add(jEdit);
		jEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if(jtable.getSelectedRow()==-1) {
					JOptionPane.showMessageDialog(null, "请选择要编辑的一行数据","编辑失败", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int bill_id=Integer.parseInt(tableValue.elementAt(jtable.getSelectedRow()).elementAt(0));
				for(Bill b:bills) {
					if(b.getBill_id()==bill_id) {
						
						BillEditUi billEditUi=new BillEditUi(BillQueryUI.this,loginUser,b);
						b=billEditUi.getBill();
						break;
					}
				}
				dtm.setDataVector(getTableValue(),columnNameV);
				
			}
		});
		jright_in.add(jDel);
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
					for(Bill b:bills) {
						if(b.getBill_id()==bill_id) {
							BillBiz.delBill(b);
							bills.remove(b);
							break;
						}
					}
					getTableValue();
				}
			}
		});
		
		jC.add(BorderLayout.CENTER,js);
		jC.add(BorderLayout.WEST, Ctrols.getLabel("                 "));
		jC.add(BorderLayout.EAST,jright );
		return jC;
	}
	private JPanel setSouth() {
		JPanel jps=new JPanel(new FlowLayout(FlowLayout.CENTER,20,30));
		jps.add(jGet);
		jps.add(jPay);
		jps.add(jAll);
		
		
		return jps;
	}
	private DefaultComboBoxModel<Eventr> getComBoxModel(List<Eventr> list){
		DefaultComboBoxModel<Eventr> tdcm=new DefaultComboBoxModel<Eventr>();
		int i=0;
		Eventr temp=new Eventr();
		temp.setEvent_name("--全部--");
		tdcm.addElement(temp);
		for(Eventr li:list) {
			tdcm.addElement(li);
		}
		return tdcm;
		
	}
	private Vector<Vector<String>> getTableValue(){
		Vector<Vector<String>> tv=new Vector<Vector<String>>();
		float get=0f;
		float pay=0f;
		float all=0f;
		for (Bill bill:bills ) {
            String event_name=null;
            String event_type=null;
            Vector<String> rowV=new Vector<String>();
            for (Eventr eventr:eventrs) {
                if(eventr.getEvent_id()==bill.getEvent_id()){
                    event_name=eventr.getEvent_name();
                    if(eventr.getEvent_type().intValue()==0) {
                    	pay=pay+bill.getMoney();
                    	event_type="支出";
                    }else {
                    	get=get+bill.getMoney();
                    	event_type="收入";
                    }
                    break;
                }
            }
            all=get-pay;
            rowV.add(bill.getBill_id().toString());
            rowV.add(DateConverUtils.convertDateToString(bill.getBill_date()));
            rowV.add(event_name);
            rowV.add(event_type);
            rowV.add(bill.getMoney().toString());
            rowV.add(bill.getNote());

         
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
