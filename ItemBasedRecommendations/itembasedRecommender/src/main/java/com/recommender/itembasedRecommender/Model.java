package com.recommender.itembasedRecommender;

public class Model {

	private int userId;
	private int itemid;
	private String preferences;

	
	
	public Model(int userId, int itemid, String preferences) {
		super();
		this.userId = userId;
		this.itemid = itemid;
		this.preferences = preferences;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getItemid() {
		return itemid;
	}

	public void setItemid(int itemid) {
		this.itemid = itemid;
	}

	public String getPreferences() {
		return preferences;
	}

	public void setPreferences(String preferences) {
		this.preferences = preferences;
	}

}
