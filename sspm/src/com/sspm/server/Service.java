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

	public void open() { // 开启服务器
		service = new Service();
		Thread thread = new Thread(service);
		thread.start();
	}

	public void stop() { // 关闭服务器

		try {
			synchronized (ClientMannager.sockets) { // 关闭各个连接
				for (ClientLink socket : ClientMannager.sockets) {
					socket.getIos().close();
					socket.getOos().close();
				}
				ClientMannager.sockets.removeAllElements();
			}

			JOptionPane.showMessageDialog(null, "服务器已关闭");
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
			JOptionPane.showMessageDialog(null, "服务器已开启");
			while (true) { // 循地接收客户端的连接
				soc = serverSocket.accept();
				ClientLink cl = new ClientLink(soc);
				ClientMannager.sockets.add(cl);
				new Thread(cl).start();
				System.out.println("客户端连接");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("错误！端口被占用！");
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
			Service.send("本次拍卖会将于"+ paiMaiTime +"秒后开始");
			PaiMai.sleep(paiMaiTime * 1000);
			ResultSet res = Service.getDb().getStatement()
					.executeQuery("select gno,name,des,img,begprice,minAddPrice from Goods");
			while (res.next()) {
				Service.send(new Goods(res.getInt(1), res.getString(2), res.getString(3), res.getString(4),
						res.getInt(5), res.getInt(6)));
				try {
					Service.send("您有30s的时间查看物品和出价！");
					int price = Service.getDb().getPrice(res.getInt(1));
					PaiMai.sleep(30000);
					if (Service.getDb().getPrice(res.getInt(1)) <= res.getInt(5)) {
						Service.send("物品流拍!");
					} else {
						while (Service.getDb().getPrice(res.getInt(1)) > price) {
							Service.send("当前最高价为" + Service.getDb().getUser(res.getInt(1)) + "出价"
									+ Service.getDb().getPrice(res.getInt(1)) + "元");
							price = Service.getDb().getPrice(res.getInt(1));
							PaiMai.sleep(10000);
						}
						Service.send("物品拍卖成功，买家为：" + Service.getDb().getUser(res.getInt(1)) + ",价格为："
								+ Service.getDb().getPrice(res.getInt(1)) + "元");
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Service.send("本次拍卖会到此结束!");
			System.out.println("本次拍卖会到此结束!");
		} catch (Exception e) {
		}
	}

	public void setPaiMaiTime(long paiMaiTime) {
		this.paiMaiTime = paiMaiTime;
	}
}
