package com.bjtu.redis;

import java.util.Scanner;

public class Test {
    public static void main(String[] args){
        CustomRedis redis= null;
        //创建redis对象
        try {
            redis = new CustomRedis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int select;
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("请选择要进行的操作:\n1-增加一个用户|2-减少一个用户|3-显示用户的顺序|4-List|5-Set|6-ZSet|0 退出");
            select = sc.nextInt();
            switch (select){
                case 0:
                    System.exit(0);
                case 1:
                    assert redis != null;
                    redis.rincr();
                    break;
                case 2:
                    assert redis != null;
                    redis.rdecr();
                    break;
                case 3:
                    assert redis != null;
                    redis.ruserFrence();
                    break;
                case 4:
                    assert redis != null;
                    redis.rlist();
                    break;
                case 5:
                    assert redis != null;
                    redis.rset();
                    break;
                case 6:
                    assert redis != null;
                    redis.rzset();

            }
        }
    }
}
