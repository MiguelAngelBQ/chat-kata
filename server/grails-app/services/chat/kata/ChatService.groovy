package chat.kata


class ChatService {
	
	
	private Collection<ChatMessage> messages=new ArrayList<ChatMessage>();
	/**
	 * Collects chat messages in the provided collection
	 * 
	 * @param if specified messages are collected from the provided sequence (exclusive)
	 * @param messages the collection where to add collected messages
	 * 
	 * @return the sequence of the last message collected.
	 */
	Integer collectChatMessages(Collection<ChatMessage> collector, Integer fromSeq = null){
		int currentmessage= fromSeq != null ? fromSeq+1 : 0;
		
		while(currentmessage < messages.size()){
			collector.add(messages[currentmessage]);
			currentmessage++;
		}
		currentmessage--;
		return currentmessage;
	}
	
	/**
	 * Puts a new message at the bottom of the chat
	 * 
	 * @param message the message to add to the chat
	 */
	void putChatMessage(ChatMessage message){
		messages.add(message);
	}
	
	
}
