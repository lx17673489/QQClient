package com.lx.qqclient.utils;/*
 *@title
 *@description
 *@author 梁湘
 *@version 1.0
 *@create 2023/7/20 9:54
 */

import java.util.Scanner;

public class Utility {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println(readString(50));
    }

    public static String readString(int len){
        String str = scanner.next();
        return str.length() >= len ? str.substring(0,len) : str;
    }
}
