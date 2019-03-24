package com.sspm.views;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.sspm.obj.Users;
import com.sspm.server.Service;

public class LoginUserView extends JFrame {
	private static final long serialVersionUID = 1L;
	private String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
	private JTextField jt1, jt4, jt5;
	private JLabel jl1, jl2, jl3, jl4, jl5;
	private JButton submit, cancel;
	private JPasswordField jt2, jt3;

	public LoginUserView() {

		super("用户注册");
		Container con = this.getContentPane();
		con.setLayout(new GridLayout(6, 2, 10, 20));
		jl1 = new JLabel("账号ID:", JLabel.RIGHT);
		jt1 = new JTextField(15);
		jl2 = new JLabel("密码:", JLabel.RIGHT);
		jt2 = new JPasswordField(15);
		jl3 = new JLabel("确认密码:", JLabel.RIGHT);
		jt3 = new JPasswordField(15);
		jl4 = new JLabel("姓名:", JLabel.RIGHT);
		jt4 = new JTextField(15);
		jl5 = new JLabel("联系电话:", JLabel.RIGHT);
		jt5 = new JTextField(15);
		submit = new JButton("注册账号");
		cancel = new JButton("取消");
 
		con.add(jl1);
		con.add(jt1);
		con.add(jl2);
		con.add(jt2);
		con.add(jl3);
		con.add(jt3);
		con.add(jl4);
		con.add(jt4);
		con.add(jl5);
		con.add(jt5);
		con.add(submit);
		con.add(cancel);
		submit.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jt1.getText().equals("") || jt4.getText().equals("") || jt2.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "请输入全部数据");
				} else {
					if (jt5.getText().matches(PHONE_NUMBER_REG)) {
						if (jt2.getText().equals(jt3.getText())) {
							Service.getDb().addUsers(new Users(Integer.parseInt(jt1.getText()), jt4.getText(), jt2.getText(),
									jt5.getText()));
						} else {
							JOptionPane.showMessageDialog(null, "两次密码输入不同,请重新输入！");
						}
					} else {
						JOptionPane.showMessageDialog(null, "请输入正确手机号！");
					}
				}
			}
		});
		JFrame jf = this;
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jf.dispose();
			}
		});
		this.pack();
		this.setLocation(700, 300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}