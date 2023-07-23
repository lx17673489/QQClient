package com.lx.qqclient.service;/*
 *@title
 *@description
 *@author 梁湘
 *@version 1.0
 *@create 2023/7/20 15:55
 */

import com.lx.qqclient.utils.Utility;
import com.lx.qqcommon.Message;
import com.lx.qqcommon.MessageType;
import com.lx.qqcommon.User;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MatchGenerator;

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
        message.setSender(user.getUserId());
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

    public void exit(){
        //给服务端发送一个退出消息
        Message message = new Message();
        message.setMestype(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(user.getUserId());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    ManageClientConnectServerThread.get(user.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
            //对应的socket关闭
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsgToOne(){
        //获取私聊用户id，发送给客户端
        System.out.println("请输入私聊用户id：");
        String getterId = Utility.readString(50);
        Message message = new Message();
        message.setSender(user.getUserId());
        message.setGetter(getterId);
        System.out.println("输入发送的消息：");
        message.setContent(Utility.readString(100));
        message.setMestype(MessageType.MESSAGE_COMM_MES);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    ManageClientConnectServerThread.get(user.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToAll(){
        Message message = new Message();
        message.setSender(user.getUserId());
        System.out.println("输入要群发的消息：");
        message.setContent(Utility.readString(100));
        message.setMestype(MessageType.MESSAGE_TO_ALL_MES);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    ManageClientConnectServerThread.get(user.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
