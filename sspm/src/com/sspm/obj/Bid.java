package com.sspm.obj;

import java.io.Serializable;

public class Bid implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int goodsId;
	private int userId;
	private int endPrice;
	
	public Bid() {
	}
	public Bid(int goodsId, int userId, int endPrice) {
		this.setUserId(userId);
		this.setEndPrice(endPrice);
		this.setGoodsId(goodsId);
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	
	public int getEndPrice() {
		return endPrice;
	}
	public void setEndPrice(int endPrice) {
		this.endPrice = endPrice;
	}
	
}
