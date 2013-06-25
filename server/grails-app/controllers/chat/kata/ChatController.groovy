package chat.kata

import org.springframework.validation.Errors;

//import chat.kata.command.SendChatMessageCommand;

class ChatController {
	ChatService chatService

	def list(Integer seq) {
		if(hasErrors()){
			render(status: 400, contentType: "text/json") { error = "Invalid seq parameter" }
			return
		}
		final List chatMessages = []
		final int nextMessage = chatService.collectChatMessages(chatMessages, seq)
		render(contentType: "text/json") {
			messages = []
			for(currentMessage in chatMessages){
				messages.add([nick:currentMessage.nick, message:currentMessage.message])
			}
			last_seq = nextMessage
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
