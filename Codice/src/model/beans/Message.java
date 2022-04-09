package model.beans;

public class Message {
	private String text;
	
	public Message(String msg) {
		this.text = msg;
	}

	public String getText() {
		return text;
	}

	public void setText(String message) {
		this.text = message;
	}
}
