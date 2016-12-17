package com.example.dysaniazzz.bean;

/**
 * Created by DysaniazzZ on 17/12/2016.
 */
public class MsgBean {

    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;

    private String content; //消息内容
    private int type;       //消息类型

    public MsgBean(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
