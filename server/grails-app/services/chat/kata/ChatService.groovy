package chat.kata

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantReadWriteLock


class ChatService {


	private Collection<ChatMessage> messages=new ArrayList<ChatMessage>();
	private ReentrantReadWriteLock rwl= new ReentrantReadWriteLock();
	private Lock r = rwl.readLock();
	private Lock w = rwl.writeLock();
	/**
	 * Collects chat messages in the provided collection
	 * 
	 * @param if specified messages are collected from the provided sequence (exclusive)
	 * @param messages the collection where to add collected messages
	 * 
	 * @return the sequence of the last message collected.
	 */
	Integer collectChatMessages(Collection<ChatMessage> collector, Integer fromSeq = null){
		r.lock();
		try{
			int currentmessage= fromSeq != null ? fromSeq+1 : 0;
			while(currentmessage < messages.size()){
				collector.add(messages[currentmessage]);
				currentmessage++;
			}
			return messages.size()-1;
		}finally{
			r.unlock();
		}
	}

	/**
	 * Puts a new message at the bottom of the chat
	 * 
	 * @param message the message to add to the chat
	 */
	void putChatMessage(ChatMessage message){
		w.lock();
		try{
			messages.add(message);
		}
		finally{
			w.unlock();
		}
	}
}
