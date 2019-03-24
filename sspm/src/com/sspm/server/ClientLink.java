package com.sspm.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.sspm.obj.Bid;
import com.sspm.obj.Login;

public class ClientLink implements Runnable {

	@SuppressWarnings("unused")
	private Socket socket = null;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;

	public ObjectInputStream getIos() {
		return ois;
	}

	public ObjectOutputStream getOos() {
		return oos;
	}

	public ClientLink(Socket socket) {
		this.socket = socket;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(Object sentObj) { // ��������
		try {
			oos.writeObject(sentObj);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {// ѭ����ȡ�ͻ��˷���������
		Bid bid;
		while (true) {
			try {

				Object obj = ois.readObject();
				if (obj instanceof Bid) {
					bid = (Bid) obj;
					if (bid.getEndPrice() >= Service.getDb().getPrice(bid.getGoodsId()) + Service.getDb().getAddPrice(bid.getGoodsId())) {
						Service.getDb().updatePrice(bid.getGoodsId(), bid.getUserId(), bid.getEndPrice());
						oos.writeObject("���۳ɹ���");
					} else {
						oos.writeObject("����ʧ��,��ǰ�۸�Ϊ" + Service.getDb().getPrice(bid.getGoodsId()));
					} 
				}else if(obj instanceof Login) {
					Login lo = (Login) obj;
					send(Service.getDb().login(lo.getUserID(), lo.getPassword()));
				}

			} catch (IOException | ClassNotFoundException e) {
				ClientMannager.sockets.remove(this);
			}
		}
	}

}
