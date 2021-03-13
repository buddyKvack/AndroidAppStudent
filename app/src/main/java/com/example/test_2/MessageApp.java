package com.example.test_2;

public class MessageApp {
    public String user_text,message_text;
    public MessageApp(){}
    public MessageApp(String UserName, String MessageText){
        this.user_text=UserName;
        this.message_text=MessageText;
    }

    public String getUser_text() {
        return user_text;
    }

    private void setUser_text(String user_text) {
        this.user_text = user_text;
    }

    public String getMessage_text() {
        return message_text;
    }

    private void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

}
