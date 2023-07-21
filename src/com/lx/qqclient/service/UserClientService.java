package com.lx.qqclient.service;/*
 *@title
 *@description
 *@author 梁湘
 *@version 1.0
 *@create 2023/7/20 15:55
 */

import com.lx.qqcommon.Message;
import com.lx.qqcommon.MessageType;
import com.lx.qqcommon.User;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

//验证用户登录
public class UserClientService {
    User user;
    Socket socket;
    public boolean checkLogin(String userId,String pwd){
        boolean login = false;
        //构建用户
        user = new User(userId,pwd);
        //发送给客户端
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        Message msg = null;
        try {
            socket = new Socket(InetAddress.getLocalHost(),9999);
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);
            //接收返回的消息,为Message对象
            ois = new ObjectInputStream(socket.getInputStream());
            msg = (Message)ois.readObject();
            if(MessageType.MESSAGE_LOGIN_SUCCESS.equals(msg.getMestype())){
                login = true;
                //创建一个通信线程
                ClientConnetServerThread clientConnetServerThread = new ClientConnetServerThread(socket);
                clientConnetServerThread.start();
                //方便管理，放入到集合中
                ManageClientConnectServerThread.put(userId,clientConnetServerThread);
            }else{
                socket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //判断是否登录成功
        return login;
    }

    public void getOnlineUsers(){
        //新建一个message
        Message message = new Message();
        message.setMestype(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        System.out.println();
        try {
            //发送给服务器
            ObjectOutputStream oos = new ObjectOutputStream(
                    ManageClientConnectServerThread.get(user.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
