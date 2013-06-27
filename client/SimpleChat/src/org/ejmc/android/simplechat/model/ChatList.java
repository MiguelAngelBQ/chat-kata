package org.ejmc.android.simplechat.model;

import java.util.ArrayList;


/**
 * List off chat messages..
 * 
 * @author startic
 *
 */
public class ChatList {
	ArrayList<Message> msgs;
	int last_seq = 0;
	
	public ChatList(){
		msgs = new ArrayList<Message>();
	}
	
	public void setMessage(String nick, String message){
		msgs.add(new Message(nick,message));
	}
	
	public ArrayList<String> getMessages(){
		ArrayList<String> mensajes= new ArrayList<String>();
		for(int i=0; i<msgs.size();i++){
			mensajes.add(msgs.get(i).nick+": "+msgs.get(i).message);
		}
		return mensajes;
	}
	
	public ArrayList<Message> getMessages2(){
		return msgs;
	}
	
	public void setMessages(ChatList cl){
		msgs.addAll(cl.msgs);
	}
	
	public void setSeq(int s){
		last_seq=s;
	}
	
	public int getSeq(){
		return last_seq;
	}
}
