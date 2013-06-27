package org.ejmc.android.simplechat.model;


/**
 * Simple message.
 * 
 * @author startic
 * 
 */
public class Message {
	String nick;
	String message;
	
	public Message(String nick, String message){
		this.nick=nick;
		this.message=message;
	}
	public String getNick(){
		return nick;
	}
	
	public String getMessage(){
		return message;
	}
}
