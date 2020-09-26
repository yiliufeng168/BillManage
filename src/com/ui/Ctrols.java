package com.ui;



import javax.swing.*;
import java.awt.*;

public class Ctrols {
    public static Font f32=new Font("微软雅黑",Font.BOLD,32);
    public static Font f24=new Font("微软雅黑",Font.PLAIN,24);
    public static Font f16=new Font("微软雅黑",Font.BOLD,16);
    
    public static JLabel getTitleLabel(String title){
        JLabel jl=new JLabel(title);
        jl.setHorizontalAlignment(SwingConstants.CENTER);
        jl.setFont(f32);
        return jl;
    }

    public static JLabel getLabel(String name){
        JLabel jl=new JLabel(name);
        jl.setHorizontalAlignment(SwingConstants.CENTER);
        jl.setFont(f16);
        return jl;
    }
    public static JLabel getLabel(String name,Font font){
        JLabel jl=new JLabel(name);
        jl.setHorizontalAlignment(SwingConstants.CENTER);
        jl.setFont(font);
        return jl;
    }
    public static JLabel getLabel(String name,Font font,int alignment){
        JLabel jl=new JLabel(name);
        jl.setHorizontalAlignment(alignment);;
        jl.setFont(font);
        return jl;
    }
    public static JButton getButton(String title){
        JButton jButton=new JButton(title);
        jButton.setBorderPainted(false);        //设置无边框
        jButton.setBackground(new Color(26,182,255));   //设置背景色
        jButton.setForeground(Color.white);     //设置前景色
        jButton.setFocusPainted(false);         //取消焦点标记
        jButton.setFont(f16);
        return jButton;
    }

    public static JTextField getJTextField(){
        JTextField jTextFiel=new JTextField();
        jTextFiel.setFont(f16);
        return jTextFiel;
    }
    public static JTextField getJTextField(String text){
        JTextField jTextFiel=new JTextField(text);
        jTextFiel.setFont(f16);
        return jTextFiel;
    }

    public static JPasswordField getJPasswordField(){
        JPasswordField jPasswordField=new JPasswordField();
        jPasswordField.setFont(f16);
        return jPasswordField;
    }
    public static JPasswordField getJPasswordField(String password){
        JPasswordField jPasswordField=new JPasswordField(password);
        jPasswordField.setFont(f16);
        return jPasswordField;
    }
    public static JTextArea getJTextArea() {
    	JTextArea jTextArea=new JTextArea();
    	jTextArea.setFont(f16);
    	return jTextArea;
    }
    public static JTextArea getJTextArea(int rows,int columns ) {
    	JTextArea jTextArea=new JTextArea(rows,columns);
    	jTextArea.setFont(f16);
    	return jTextArea;
    }
}
