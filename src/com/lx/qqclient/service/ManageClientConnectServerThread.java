package com.lx.qqclient.service;/*
 *@title
 *@description 管理客户端连接到服务器的线程
 *@author 梁湘
 *@version 1.0
 *@create 2023/7/20 16:18
 */

import java.util.HashMap;
import java.util.HashSet;

public class ManageClientConnectServerThread {
    private static HashMap<String,ClientConnetServerThread> map = new HashMap<>(); //<key:userId>

    public static void put(String userId,ClientConnetServerThread clientConnetServerThread){
        map.put(userId,clientConnetServerThread);
    }
    public static ClientConnetServerThread get(String userId){
        return map.get(userId);
    }

}
