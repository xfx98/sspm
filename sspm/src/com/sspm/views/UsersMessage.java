package com.sspm.views;

import java.awt.TextArea;

import javax.swing.JFrame;

public class UsersMessage extends JFrame{
	private static final long serialVersionUID = 1L;

	public UsersMessage(String usersMessage) {
		super("用户信息");
		this.setTitle("用户信息");
		TextArea ta = new TextArea(usersMessage,15,37,TextArea.SCROLLBARS_HORIZONTAL_ONLY);
		ta.setEditable(false);
		this.getContentPane().add(ta);
		this.setLocation(800, 300);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
}
