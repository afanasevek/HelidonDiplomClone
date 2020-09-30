package ru.afanasev.helidon.object;

import io.helidon.dbclient.DbColumn;
import io.helidon.dbclient.DbRow;

public class Post {
		
	private int id;
	private String Text;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return Text;
	}
	public void setText(String text) {
		Text = text;
	}
	
	

	
}
