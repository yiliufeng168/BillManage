package com.ui;

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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.beans.Bill;
import com.beans.Eventr;
import com.beans.User;
import com.biz.BillBiz;
import com.biz.EventBiz;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;



public class WriteBillUi extends JDialog {
	User loginUser=null;
	Bill bill=new Bill();
	int eventType=0;
	String[] chooseList1= {"支出","收入"};
	JComboBox<String> jComboBox=new JComboBox<String>(chooseList1);
	JComboBox<Eventr> jeComboBox=new JComboBox<Eventr>();
	JTextField jtMoney = Ctrols.getJTextField();
	UtilDateModel model = new UtilDateModel();
	JDatePanelImpl datePanelImpl=new JDatePanelImpl(model);
	JDatePickerImpl jd=new JDatePickerImpl(datePanelImpl);
	JTextField jTMark=Ctrols.getJTextField();
	public WriteBillUi() {
		// TODO 自动生成的构造函数存根
	}
	public WriteBillUi(JFrame jffather,User loginUser) {
		super(jffather,true	);
		this.loginUser=loginUser;
		CreateJFrame("记账");
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
		setSize(600, 700);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false); // 设置窗体大小不可变
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	private JPanel setNorth() {
		JPanel jPanelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
		jPanelNorth.add(Ctrols.getTitleLabel("新建账单"));
		
		return jPanelNorth;
	}
	private JPanel setCenter() {
		
		JPanel jpF = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
		
		JPanel jpc1 = new JPanel(new GridLayout(6, 1, 50, 60));
		JPanel jpc2 = new JPanel(new GridLayout(6, 1, 50, 50));
		jpc1.add(Ctrols.getLabel("记录类型:", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT));
		JLabel jEventTypeLabel=Ctrols.getLabel("支出类型:", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT);
		jpc1.add(jEventTypeLabel);
		jpc1.add(Ctrols.getLabel("金额(￥)：", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT));
		jpc1.add(Ctrols.getLabel("日期：", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT));
		jpc1.add(Ctrols.getLabel("备注：", new Font("微软雅黑", Font.PLAIN, 18),SwingConstants.LEFT));
		
		jComboBox.setSelectedIndex(0);
		jComboBox.setFont(Ctrols.f16);
		jComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO 自动生成的方法存根
				if(e.getStateChange()==ItemEvent.SELECTED) {
					eventType=jComboBox.getSelectedIndex();
					String t=(eventType==0)?"支出":"收入";
					jEventTypeLabel.setText(t+"类型");
					List<Eventr> list=EventBiz.getAllEvents(loginUser,eventType);
					DefaultComboBoxModel<Eventr> tdcm=new DefaultComboBoxModel<Eventr>();
					int i=0;
					for(Eventr li:list) {
						tdcm.addElement(li);
					}
					jeComboBox.setModel(tdcm);
					
				}
			}
		});
		jpc2.add(jComboBox);
		jComboBox.setSize(100, 20);
		
		
		//收入/支出类型：管理面板
		JPanel jpevent=new JPanel(new GridLayout(1,2, 20, 0));
		jpc2.add(jpevent);
		List<Eventr> l=EventBiz.getAllEvents(loginUser,eventType);
		DefaultComboBoxModel<Eventr> dcm=new DefaultComboBoxModel<Eventr>();
		int i=0;
		for(Eventr li:l) {
			dcm.addElement(li);
		}
		
		jeComboBox.setFont(Ctrols.f16);
		jeComboBox.setModel(dcm);
		jeComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO 自动生成的方法存根
				if(e.getStateChange()==ItemEvent.SELECTED) {
					Eventr tempe=(Eventr)jeComboBox.getSelectedItem();
					bill.setEvent_id(tempe.getEvent_id());
					
				}
			}
		});
		
		
		JButton jeset=Ctrols.getButton("管理");
		jeset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				SetEnterUi setEnterUi=new SetEnterUi(WriteBillUi.this, loginUser, eventType);
				List<Eventr> list=EventBiz.getAllEvents(loginUser,eventType);
				DefaultComboBoxModel<Eventr> tdcm=new DefaultComboBoxModel<Eventr>();
				int i=0;
				for(Eventr li:list) {
					tdcm.addElement(li);
				}
				jeComboBox.setModel(tdcm);
				
			}
		});
		jpevent.add(jeComboBox);
		jpevent.add(jeset);
		jpc2.add(jtMoney);
		
		jd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				
			}
		});
		
		jpc2.add(jd);
	
		jd.setFont(Ctrols.f24);
		jpc2.add(jTMark);
		
		
		jpF.add(jpc1);
		jpF.add(jpc2);
		
		return jpF;
	}
	private JPanel setSouth() {
		JPanel jPs = new JPanel(new GridLayout(2,1,0,10));
		JPanel jpL1=new JPanel(new FlowLayout(FlowLayout.CENTER,30,10));
		jPs.add(jpL1);
		jPs.add(Ctrols.getLabel(""));
		JButton jb = Ctrols.getButton("保存");
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				try {
					float momey=new Float(jtMoney.getText());
					if (momey<0||model.getValue()==null) {
						throw new Exception();
					}
					bill.setBill_date(model.getValue());
					bill.setEvent_id(((Eventr)jeComboBox.getSelectedItem()).getEvent_id());
					bill.setUser_id(loginUser.getUser_id());
					bill.setMoney(momey);
					bill.setNote(jTMark.getText());
					BillBiz.addBill(bill);
					WriteBillUi.this.dispose();
				}catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "金额或日期错误","保存失败", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		JButton jbnext =Ctrols.getButton("保存并编辑下一个");
		jbnext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				try {
					float momey=new Float(jtMoney.getText());
					if (momey<0||model.getValue()==null) {
						throw new Exception();
					}
					bill.setBill_date(model.getValue());
					bill.setEvent_id(((Eventr)jeComboBox.getSelectedItem()).getEvent_id());
					bill.setUser_id(loginUser.getUser_id());
					bill.setMoney(momey);
					bill.setNote(jTMark.getText());
					BillBiz.addBill(bill);
					
					jtMoney.setText("");
					model.setValue(null);
					jTMark.setText("");
					
				}catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "金额或日期错误","保存失败", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		JButton je=Ctrols.getButton("返回");
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
}
