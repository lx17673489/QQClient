package com.lx.qqclient.view;/*
 *@title
 *@description
 *@author 梁湘
 *@version 1.0
 *@create 2023/7/20 9:53
 */

import com.lx.qqclient.common.User;
import com.lx.qqclient.utils.Utility;

public class QQView {
    private boolean loop = true;
    public static void main(String[] args) {
        new QQView().mainMenu();
    }

    public void mainMenu(){
        while(loop){
            showMenu(1,"0");
            String key = Utility.readString(1);
            switch (key){
                case "1":
                    String userId = Utility.readString(50);
                    String pwd = Utility.readString(50);
                    //验证是否登录成功
                    while(loop){
                        showMenu(2,userId);
                        key = Utility.readString(1);
                        switch (key){
                            case "1:":
                                System.out.println("显示在线用户列表");
                                break;
                            case "2":
                                System.out.println("群发消息");
                                break;
                            case "3":
                                System.out.println("私聊消息");
                                break;
                            case "4":
                                System.out.println("发送文件");
                                break;
                            case "9":
                                loop = false;
                                System.out.println("退出系统");
                                break;
                            default:
                                break;
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
                break;
            case 2:
                System.out.println("==================网络通讯系统二级菜单,userId=(" +  userId + ")===============");
                System.out.println("\t\t 1.显示在线用户列表");
                System.out.println("\t\t 2.群发消息");
                System.out.println("\t\t 3.私聊消息");
                System.out.println("\t\t 4.发送文件");
                System.out.println("\t\t 9.退出系统");
            default:
                break;
        }
    }

}
