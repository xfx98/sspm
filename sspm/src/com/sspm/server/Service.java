package com.sspm.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import com.sspm.data.DataBase;
import com.sspm.obj.Goods;

public class Service implements Runnable {
	private ServerSocket serverSocket = null;
	private Socket soc;
	private Service service;
	private static DataBase db = DataBase.getDatabase();

	public static DataBase getDb() {
		return db;
	}

	public void open() { // ����������
		service = new Service();
		Thread thread = new Thread(service);
		thread.start();
	}

	public void stop() { // �رշ�����

		try {
			synchronized (ClientMannager.sockets) { // �رո�������
				for (ClientLink socket : ClientMannager.sockets) {
					socket.getIos().close();
					socket.getOos().close();
				}
				ClientMannager.sockets.removeAllElements();
			}

			JOptionPane.showMessageDialog(null, "�������ѹر�");
			this.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void send(Object object) {
		ClientMannager.sendAll(object);
	}

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(8434);
			JOptionPane.showMessageDialog(null, "�������ѿ���");
			while (true) { // ѭ�ؽ��տͻ��˵�����
				soc = serverSocket.accept();
				ClientLink cl = new ClientLink(soc);
				ClientMannager.sockets.add(cl);
				new Thread(cl).start();
				System.out.println("�ͻ�������");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("���󣡶˿ڱ�ռ�ã�");
		}
	}
}

class PaiMai extends Thread {
	private long paiMaiTime;

	public PaiMai(int paiMaiTime) {
		this.setPaiMaiTime(paiMaiTime);
	}

	@Override
	public void run() {
		try {
			Service.send("���������Ὣ��"+ paiMaiTime +"���ʼ");
			PaiMai.sleep(paiMaiTime * 1000);
			ResultSet res = Service.getDb().getStatement()
					.executeQuery("select gno,name,des,img,begprice,minAddPrice from Goods");
			while (res.next()) {
				Service.send(new Goods(res.getInt(1), res.getString(2), res.getString(3), res.getString(4),
						res.getInt(5), res.getInt(6)));
				try {
					Service.send("����30s��ʱ��鿴��Ʒ�ͳ��ۣ�");
					int price = Service.getDb().getPrice(res.getInt(1));
					PaiMai.sleep(30000);
					if (Service.getDb().getPrice(res.getInt(1)) <= res.getInt(5)) {
						Service.send("��Ʒ����!");
					} else {
						while (Service.getDb().getPrice(res.getInt(1)) > price) {
							Service.send("��ǰ��߼�Ϊ" + Service.getDb().getUser(res.getInt(1)) + "����"
									+ Service.getDb().getPrice(res.getInt(1)) + "Ԫ");
							price = Service.getDb().getPrice(res.getInt(1));
							PaiMai.sleep(10000);
						}
						Service.send("��Ʒ�����ɹ������Ϊ��" + Service.getDb().getUser(res.getInt(1)) + ",�۸�Ϊ��"
								+ Service.getDb().getPrice(res.getInt(1)) + "Ԫ");
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Service.send("���������ᵽ�˽���!");
			System.out.println("���������ᵽ�˽���!");
		} catch (Exception e) {
		}
	}

	public void setPaiMaiTime(long paiMaiTime) {
		this.paiMaiTime = paiMaiTime;
	}
}
