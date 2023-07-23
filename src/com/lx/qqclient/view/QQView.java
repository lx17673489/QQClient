package com.lx.qqclient.view;/*
 *@title
 *@description
 *@author 梁湘
 *@version 1.0
 *@create 2023/7/20 9:53
 */

import com.lx.qqclient.service.UserClientService;
import com.lx.qqclient.utils.Utility;
import com.lx.qqcommon.Message;
import com.lx.qqcommon.MessageType;

import java.io.ObjectInput;

public class QQView {
    private boolean loop = true;
    private UserClientService userClientService = new UserClientService();
    public static void main(String[] args) {
        new QQView().mainMenu();
    }

    public void mainMenu(){
        showMenu(1,"0");
        while(loop){
            String key = Utility.readString(1);
            switch (key){
                case "1":
                    System.out.print("请输入用户名：");
                    String userId = Utility.readString(50);
                    System.out.print("请输入密码：");
                    String pwd = Utility.readString(50);
                    //验证是否登录成功
                    if(userClientService.checkLogin(userId,pwd)){
                        while(loop){
                            showMenu(2,userId);
                            key = Utility.readString(1);
                            System.out.println(key);
                            switch (key){
                                case "1":
                                    System.out.println("显示在线用户列表");
                                    userClientService.getOnlineUsers();
                                    break;
                                case "2":
                                    userClientService.sendToAll();
                                    System.out.println("群发消息");
                                    break;
                                case "3":
                                    userClientService.sendMsgToOne();
                                    System.out.println("私聊消息");
                                    break;
                                case "4":
                                    System.out.println("发送文件");
                                    break;
                                case "9":
                                    loop = false;
                                    userClientService.exit();
                                    break;
                                default:
                                    showMenu(1,"0");
                                    break;
                            }
                        }
                    }
                    break;
                case "9":
                    loop = false;
                    break;
            }
        }
    }

    public void showMenu(int level,String userId){
        switch (level){
            case 1://主菜单
                System.out.println("=========欢迎登录网络通讯系统userId=("+  userId + ")==============");
                System.out.println("\t\t 1.登录系统");
                System.out.println("\t\t 9.退出系统");
                System.out.println("请输入你的选择:");
                break;
            case 2:
                System.out.println("==================网络通讯系统二级菜单," +  userId + " 登录成功===============");
                System.out.println("\t\t 1.显示在线用户列表");
                System.out.println("\t\t 2.群发消息");
                System.out.println("\t\t 3.私聊消息");
                System.out.println("\t\t 4.发送文件");
                System.out.println("\t\t 9.退出系统");
                System.out.println("请输入你的选择:");
            default:
                break;
        }
    }

}
