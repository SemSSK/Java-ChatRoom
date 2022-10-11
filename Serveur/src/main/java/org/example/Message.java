package org.example;

import java.io.Serializable;

public class Message implements Serializable {

    private int id;
    private String pseudo;
    private Object content;

    public Message(int id,String pseudo, Object content){
        this.id = id;
        this.pseudo = pseudo;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Object getContent() {
        return content;
    }
    public String getContentAsText() {
        return (String)content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
