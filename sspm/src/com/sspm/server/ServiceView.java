package com.sspm.server;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sspm.views.AddGoodsView;
import com.sspm.views.GoodsMessage;
import com.sspm.views.LoginUserView;
import com.sspm.views.UsersMessage;

public class ServiceView extends JFrame {
	private static final long serialVersionUID = 1L;
	Service ser;
	private JButton open, stop, login, deleteGoods, goodsMessage, usersMessage, deleteUsers, setTime, addGoods;
	JPanel b1, b2, b3, b4, b5, b6, b7, b8, b9;
	JTextField c1, c2;

	public ServiceView() {
		super("实时拍卖系统服务端");
		ser = new Service();
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout(10, 30));

		open = new JButton("打开服务器");
		stop = new JButton("关闭服务器");
		b1 = new JPanel();
		b1.setLayout(new FlowLayout());
		b1.add(open);
		b1.add(stop);
		con.add(b1, BorderLayout.SOUTH);

		b2 = new JPanel();
		b2.setLayout(new GridLayout(3, 3, 40, 30));
		con.add(b2, BorderLayout.CENTER);

		login = new JButton("注册账号");
		login.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new LoginUserView();

			}

		});
		usersMessage = new JButton("查看账号");
		usersMessage.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new UsersMessage(Service.getDb().showUsersAll());

			}

		});

		deleteUsers = new JButton("删除账号");
		deleteUsers.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String sss = JOptionPane.showInputDialog("请输入要删除用户的账号：");
				int useIdd = Integer.parseInt(sss);
				Service.getDb().deleteUser(useIdd);

			}

		});

		b2.add(login);
		b2.add(usersMessage);
		b2.add(deleteUsers);

		addGoods = new JButton("添加物品");
		addGoods.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AddGoodsView();
			}

		});
		goodsMessage = new JButton("查看物品");
		goodsMessage.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new GoodsMessage(Service.getDb().showGoodsAll());
			}

		});
		deleteGoods = new JButton("删除物品");
		deleteGoods.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String sss = JOptionPane.showInputDialog("请输入要删除物品的编号：");
				int goodsId = Integer.parseInt(sss);
				Service.getDb().deleteGoods(goodsId);

			}

		});

		b2.add(addGoods);
		b2.add(goodsMessage);
		b2.add(deleteGoods);

		c1 = new JTextField(5);
		setTime = new JButton("设置拍卖时间");
		c2 = new JTextField(10);
		b2.add(c1);
		b2.add(setTime);
		b2.add(c2);
		setActionListener();

		this.setLocation(700, 260);
		this.setVisible(true);
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void setActionListener() {
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ser.open();
			}
		});

		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ser.stop();
				} catch (Exception e2) {
				}
			}
		});

		try {
			c2.setText("IP地址：" + InetAddress.getLocalHost());
			c2.setEnabled(false);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		setTime.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PaiMai(Integer.parseInt(c1.getText())).start();
			}
		});
	}

	public static void main(String[] args) {
		new ServiceView();
	}
}
