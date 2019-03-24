package com.sspm.obj;

import java.io.Serializable;

public class Goods implements Serializable{
	@Override
	public String toString() {
		return "Goods [id=" + id + ", name=" + name + ", des=" + des + ", img=" + img + ", beginPrice=" + beginPrice
				+ ", minAddPrice=" + minAddPrice + "]";
	}

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String des;
	private String img;
	private int beginPrice;
	private int minAddPrice;

	public Goods(int id, String name, String des, String img, int beginPrice, int minAddPrice) {
		this.setId(id);
		this.setName(name);
		this.setDes(des);
		this.setImg(img);
		this.setBeginPrice(beginPrice);
		this.setMinAddPrice(minAddPrice);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id > 0) {
			this.id = id;
		} else {
			this.id = 1;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;

	}

	public int getBeginPrice() {
		return beginPrice;
	}

	public void setBeginPrice(int beginPrice) {
		if (beginPrice > 0) {
			this.beginPrice = beginPrice;
		} else {
			this.beginPrice = 0;
		}
	}

	public int getMinAddPrice() {
		return minAddPrice;
	}

	public void setMinAddPrice(int minAddPrice) {
		if (minAddPrice > 0) {
			this.minAddPrice = minAddPrice;
		} else {
			this.minAddPrice = minAddPrice;
		}
	}

}
