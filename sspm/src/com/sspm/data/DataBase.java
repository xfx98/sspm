package com.sspm.data;

import java.sql.Statement;

import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sspm.obj.Goods;
import com.sspm.obj.Users;

public class DataBase {
	private Connection connection;
	private Statement statement;
	private static final DataBase database = new DataBase("auctionsystem", "sa", "980928");//������Ҫ���޸ĸ��û���������
	
	public static void main(String[] args) {
		database.receverData();
	}
	
	public static DataBase getDatabase() {
		return database;
	}

	private DataBase(String databaseName, String admin, String password) {// ����һ�����ӵ����ݿ�
		this.link(databaseName, admin, password);
	}

	public void link(String databaseName, String admin, String password) {// �������ݿ�
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=" + databaseName,
					admin, password);
			statement = connection.createStatement();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addUsers(Users user) {// ����û�
		try {
			PreparedStatement pre = connection
					.prepareStatement("insert into users(uno,name,code,tell) values(?,?,?,?)");
			pre.setInt(1, user.getId());
			pre.setString(2, user.getName());
			pre.setString(3, user.getPassword());
			pre.setString(4, user.getTell());
			try {
				pre.executeUpdate();
				JOptionPane.showMessageDialog(null, "�û�ע��ɹ���");
			} catch (SQLServerException e) {
				JOptionPane.showMessageDialog(null, "���û��Ѵ��ڻ���Ϊ����ȫ�����ݣ�");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addGoods(Goods goods) {// �����Ʒ
		try {
			PreparedStatement pre = connection.prepareStatement(
					"insert into Goods(gno,name,des,img,begprice,minAddPrice,endprice) values(?,?,?,?,?,?,?)");
			pre.setInt(1, goods.getId());
			pre.setString(2, goods.getName());
			pre.setString(3, goods.getDes());
			pre.setString(4, goods.getImg());
			pre.setInt(5, goods.getBeginPrice());
			pre.setInt(6, goods.getMinAddPrice());
			pre.setInt(7, goods.getBeginPrice());
			try {
				pre.executeUpdate();
				JOptionPane.showMessageDialog(null, "�����Ʒ�ɹ���");
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "����Ʒ�Ѵ��ڻ��������������,����Ʒ���������10����");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void receverData() {
		try {
			statement.executeUpdate("update Goods set endprice = begprice \r\n" + 
					"\r\n" + "update Goods set uno = null");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updatePrice(int goodsId, int usersId, int price) {// ���¼۸�
		try {
			PreparedStatement pre = connection.prepareStatement("update Goods set endprice = ?,uno = ? where gno = ?");
			pre.setInt(1, price);
			pre.setInt(2, usersId);
			pre.setInt(3, goodsId);
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String showGoodsAll() {// �鿴������Ʒ
		String out = "��Ʒ��� \t��Ʒ����       \t���ļ�\t��С�Ӽ�\t�ɽ���\t������\r\n\r\n";

		try {
			ResultSet res = statement.executeQuery("select gno,name,begprice,minAddPrice,endprice,uno " + "from Goods");
			while (res.next()) {
				out += res.getString(1) + "\t" + res.getString(2) + "\t" + res.getString(3) + "\t" + res.getString(4)
						+ "\t" + res.getString(5) + "\t" + res.getString(6) + "\r\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return out;
	}

	public Statement getStatement() {
		return statement;
	}

	public String showUsersAll() {// �鿴�����û�
		String out = "�û����\t�û���\t�û��ֻ���\r\n";

		try {
			ResultSet res = statement.executeQuery("select uno,name,tell " + "from users");
			while (res.next()) {
				out += res.getString(1) + "\t" + res.getString(2) + "\t" + res.getString(3) + "\r\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return out;
	}

	public int getPrice(int goodsId) {// ��ȡ��ǰ�۸�
		try {
			PreparedStatement pre = connection.prepareStatement("select endprice from Goods where gno = ?");
			pre.setInt(1, goodsId);
			ResultSet rs = pre.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getAddPrice(int goodsId) {// ��С�Ӽ�
		try {
			PreparedStatement pre = connection.prepareStatement("select minAddPrice from Goods where gno = ?");
			pre.setInt(1, goodsId);
			ResultSet rs = pre.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getUser(int goodsId) {// ��ȡ��ǰ���������
		try {
			PreparedStatement pre = connection.prepareStatement("select uno from Goods where gno = ?");
			pre.setInt(1, goodsId);
			ResultSet rs = pre.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void deleteGoods(int goodsId) {// ɾ����Ʒ
		try {
			PreparedStatement pre = connection.prepareStatement("delete from Goods where gno = ?");
			pre.setInt(1, goodsId);
			int n = pre.executeUpdate();
			if (n != 0) {
				JOptionPane.showMessageDialog(null, "ɾ���ɹ�!");
			} else {
				JOptionPane.showMessageDialog(null, "�����Ŵ���!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteUser(int userId) {// ɾ���˺�
		try {
			PreparedStatement pre = connection.prepareStatement("delete from users where uno = ?");
			pre.setInt(1, userId);
			int n = pre.executeUpdate();
			if (n != 0) {
				JOptionPane.showMessageDialog(null, "ɾ���ɹ�!");
			} else {
				JOptionPane.showMessageDialog(null, "�����Ŵ���!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int login(int userId, String password) {
		try {
			PreparedStatement pre = connection.prepareStatement("select uno from users where uno = ? and code = ?");
			pre.setInt(1, userId);
			pre.setString(2, password);
			ResultSet rs= pre.executeQuery();
			return rs.next()?1:0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
