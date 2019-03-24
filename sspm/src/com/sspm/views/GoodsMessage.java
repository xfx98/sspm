package com.sspm.views;

import java.awt.TextArea;

import javax.swing.JFrame;

import com.sspm.data.DataBase;

public class GoodsMessage extends JFrame{
	private static final long serialVersionUID = 1L;

	public GoodsMessage(String usersMessage) {
		super("物品信息");
		this.setTitle("物品信息");
		TextArea ta = new TextArea(usersMessage,15,60,TextArea.SCROLLBARS_HORIZONTAL_ONLY);
		ta.setEditable(false);
		this.getContentPane().add(ta);
		this.setLocation(800, 300);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new GoodsMessage(DataBase.getDatabase().showGoodsAll());
	}
}
