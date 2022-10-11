package org.example;

import java.util.List;

public class MessageHandler {
    public enum MessageType{
        ClientsList,
        TextMessage,
    }
    private Message message;
    private MessageType type;

    private void parseMessage(){
        if (message.getContent() instanceof String) {
            type = MessageType.TextMessage;
        }
        else{
            type = MessageType.ClientsList;
        }
    }

    public MessageHandler(Message message){
        this.message = message;
        parseMessage();
    }

    public MessageType getType(){
        return type;
    }

    public List<String> getClientList(){
        return (List<String>) this.message.getContent();
    }

    public Object getContent(){
        return this.message.getContent();
    }

    public Message getMessage(){
        return this.message;
    }
}
