package chat.kata

//import chat.kata.command.SendChatMessageCommand;

class ChatController {
	ChatService chatService
	
	def list(Integer seq) {

		final Collection<ChatMessage> msgs = []; 
		final int salida = chatService.collectChatMessages(msgs, seq);
		render(contentType: "text/json") {
			messages=[]
			for(i in 0..msgs.size()-1){
				messages.add(nick:msgs[i].nick,message:msgs[i].message);
			}
			last_seq = salida
		}
	}

	def send(){
		def msgs = new ChatMessage(request.JSON)
		chatService.putChatMessage(msgs)		
		render(status: 201)
	}
}
