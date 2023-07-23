package com.lx.qqclient.service;/*
 *@title
 *@description
 *@author 梁湘
 *@version 1.0
 *@create 2023/7/20 16:12
 */

import com.lx.qqcommon.Message;
import com.lx.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnetServerThread extends Thread{
    private Socket socket;
    private boolean isExist = true;
    public ClientConnetServerThread(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while(isExist){
            //循环等待服务器发来的消息
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                //得到message之后。。。。
                switch (message.getMestype()){
                    case (MessageType.MESSAGE_RET_ONLINE_FRIEND):
                        System.out.println("在线用户：");
                        System.out.println(message.getContent());
                        break;
                    case (MessageType.MESSAGE_COMM_MES):
                        System.out.println(message.getSender() +" 对 "+message.getGetter()+" 说："+message.getContent());
                    case (MessageType.MESSAGE_TO_ALL_MES):
                        System.out.println(message.getSender() +" 的群发消息： " +message.getContent());
                        break;
                    default:
                        System.out.println("还未处理！");
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
