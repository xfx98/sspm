package com.sspm.client;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.*;
import com.sspm.obj.Bid;
import com.sspm.obj.Goods;
import com.sspm.obj.Login;

//���������û���
public class Client extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;

	private int userID;
	private int goodsID;
	private String code;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private JTextArea goodsMessage;
	private JPanel shang, xia, zuo, you, ss, xx, yy;
	private JButton link, jblend, jbjingpai;
	private JLabel jlname, jlbeginPrice, jlminAddPrice,  l;
	private JTextField inputIp, jtok;
	private Container con;
	private Icon icon;

	public Client() {
		super("ʵ  ʱ  ��  ��");
		con = this.getContentPane();
		inputIp = new JTextField(10);
		link = new JButton("���ӷ�����");
		shang = new JPanel();
		jblend = new JButton("��¼");
		shang.setLayout(new BorderLayout());
		ss = new JPanel();
		ss.add(inputIp);
		ss.add(link);
		shang.add(ss);
		shang.add(jblend, BorderLayout.EAST);
		con.setLayout(new BorderLayout());
		con.add(shang, BorderLayout.NORTH);
		link.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				link(inputIp.getText());
			}
		});
		goodsMessage = new JTextArea(10, 25);
		goodsMessage.setText("��Ʒ����:\n");
		goodsMessage.setEditable(false);
		goodsMessage.setLineWrap(true);
		con.add(goodsMessage);
		jblend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			if(jblend.getText()=="��¼") {
				userID = Integer.parseInt(JOptionPane.showInputDialog("����������û��˺ţ�"));
				code = JOptionPane.showInputDialog("����������û����룺");
				if (userID != 0)
					send(new Login(userID, code));
				else {
					JOptionPane.showMessageDialog(null, "��¼ʧ��!");

				}
			}
			}
		});

		zuo = new JPanel();
		l = new JLabel();
		icon = new ImageIcon("");
		l.setIcon(icon);
		l.setBounds(10, 10, icon.getIconWidth(), icon.getIconHeight());
		zuo.add(l, new Integer(Integer.MIN_VALUE));
		con.add(zuo, BorderLayout.WEST);

		jlname = new JLabel("��Ʒ����:");
		jlbeginPrice = new JLabel("�׼�:");
		jlminAddPrice = new JLabel("ÿ�����ټӼ�:");
		you = new JPanel();
		yy = new JPanel();
		you.setLayout(new BorderLayout());
		yy.setLayout(new GridLayout(3, 1, 0, 1));
		yy.add(jlname);
		yy.add(jlbeginPrice);
		yy.add(jlminAddPrice);
		you.add(goodsMessage, BorderLayout.SOUTH);
		you.add(yy);
		con.add(you, BorderLayout.EAST);

		jtok = new JTextField("");
		jbjingpai = new JButton("����");
		xia = new JPanel();
		xx = new JPanel();
		xx.setLayout(new GridLayout(2, 2, 50, 5));
		xx.add(jtok);
		xx.add(jbjingpai);
		xia.add(xx);
		con.add(xia, BorderLayout.SOUTH);
		jbjingpai.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!jblend.getText().equals("��¼")) {
					send(new Bid(goodsID, userID, Integer.parseInt(jtok.getText())));
				} else {
					JOptionPane.showMessageDialog(null, "���۴������ȵ�¼�ٽ��о���!");
				}
			}
		});

		this.setBounds(450, 150, 800, 600);
		this.setVisible(true);
	}

	public void link(String ip) { // ����
		Socket soc = null;
		try {
			soc = new Socket(ip, 8434);
			oos = new ObjectOutputStream(soc.getOutputStream());
			ois = new ObjectInputStream(soc.getInputStream());
			Thread client = new Thread(this);
			client.start();
			JOptionPane.showMessageDialog(null, "���ӳɹ�");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "����ʧ��");
		}
	}

	public void send(Object object) { // ������Ϣ
		try {
			oos.writeObject(object);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "�������Ѿ��˿ڣ�������������!");
			System.exit(1);
		}
	}

	@Override
	public void run() { // ���Ͻ��ն���
		Object obj = null;
		while (true) {
			try {
				obj = ois.readObject();
				if (obj instanceof Goods) {
					Goods bid = (Goods) obj;
					goodsMessage.setText("��Ʒ����:\n" + bid.getDes());
					jlname.setText("��Ʒ����:" + bid.getName());
					jlbeginPrice.setText("�׼�:" + bid.getBeginPrice());
					jlminAddPrice.setText("ÿ�����ټӼ�:" + bid.getMinAddPrice());
					icon = new ImageIcon(bid.getImg());
					l.setIcon(icon);
					goodsID = bid.getId();

				} else if (obj instanceof String) {
					JOptionPane.showMessageDialog(null, obj);
				} else if (obj instanceof Integer) {
					Integer in = (Integer) obj;
					if (in.equals(0)) {
						this.userID = 0;
						JOptionPane.showMessageDialog(null, "��¼ʧ��!");

					} else {

						jblend.setText("��ӭ:" + userID);
					}
				}
			} catch (ClassNotFoundException | IOException e) {

			}

		}
	}

	public static void main(String[] args) {
		new Client().setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}