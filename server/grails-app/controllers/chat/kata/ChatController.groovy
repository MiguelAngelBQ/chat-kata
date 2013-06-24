package chat.kata

import org.springframework.validation.Errors;

//import chat.kata.command.SendChatMessageCommand;

class ChatController {
	ChatService chatService
	
	def list(Integer seq) {

		
		if(!hasErrors()){
			final Collection<ChatMessage> msgs = [];
			final int salida = chatService.collectChatMessages(msgs, seq);
			render(contentType: "text/json") {
				messages=[]
				for(i in 0..msgs.size()-1){
					messages.add(nick:msgs[i].nick,message:msgs[i].message);
				}
				last_seq = salida
			}
		}else{		
			render(status: 400, contentType: "text/json") {error = "Invalid seq parameter"}
		}
	}

	def send(){
		
		if(!request.JSON){
			render(status: 400, contentType: "text/json") {error = "Invalid body"}
		}else{
			ChatMessage msgs = new ChatMessage(request.JSON)
			msgs.validate()
			if(msgs.validate()){
				chatService.putChatMessage(msgs)					
				render(status: 201)
			}else{
				render(status: 400, contentType: "text/json") {
					if(msgs.errors.hasFieldErrors("nick"))
						error = "Missing nick parameter"
					else if(msgs.errors.hasFieldErrors("message"))
						error = "Missing message parameter"
				}
			}		
		}	
	}
}
