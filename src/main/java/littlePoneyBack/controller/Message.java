package littlePoneyBack.controller;

public class Message {
	private String message;
	private boolean erreur;
	
	public Message() {
		super();
	}
	public Message(String message, boolean erreur) {
		super();
		this.message = message;
		this.erreur = erreur;
	}
	@Override
	public String toString() {
		return "Message [message=" + message + ", erreur=" + erreur + "]";
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isErreur() {
		return erreur;
	}
	public void setErreur(boolean erreur) {
		this.erreur = erreur;
	}
}
