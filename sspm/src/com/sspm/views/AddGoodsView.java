package com.sspm.views;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.sspm.obj.Goods;
import com.sspm.server.Service;

public class AddGoodsView extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField jt1, jt2, jt3, jt4, jt5, jt6;
	private JLabel jl1, jl2, jl3, jl4, jl5, jl6;
	private JButton submit, cancel;

	public AddGoodsView() {
		super("��Ϣ��д");
		Container con = this.getContentPane();
		con.setLayout(new GridLayout(7, 2, 10, 20));
		jl6 = new JLabel("��ƷID:", JLabel.RIGHT);
		jt6 = new JTextField(15);
		jl1 = new JLabel("��Ʒ����:", JLabel.RIGHT);
		jt1 = new JTextField(15);
		jl2 = new JLabel("��Ʒ����:", JLabel.RIGHT);
		jt2 = new JTextField(15);
		jl3 = new JLabel("��ƷͼƬ:", JLabel.RIGHT);
		jt3 = new JTextField(15);
		jl4 = new JLabel("��Ʒ��ʼ��:", JLabel.RIGHT);
		jt4 = new JTextField(15);
		jl5 = new JLabel("��С�Ӽ�:", JLabel.RIGHT);
		jt5 = new JTextField(15);
		submit = new JButton("ȷ��");
		cancel = new JButton("ȡ��");
		con.add(jl6);
		con.add(jt6);
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
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					if (jt1.getText().equals("") || jt4.getText().equals("") || jt2.getText().equals("")
							|| jt3.getText().equals("") || jt6.getText().equals("") || jt5.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "������ȫ������");
					} else {
						Service.getDb()
								.addGoods(new Goods(Integer.parseInt(jt6.getText()), jt1.getText(), jt2.getText(),
										jt3.getText(), Integer.parseInt(jt4.getText()),
										Integer.parseInt(jt5.getText())));
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "�������ݹ��������");
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
