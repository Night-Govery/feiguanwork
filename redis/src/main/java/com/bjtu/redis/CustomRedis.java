package com.bjtu.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomRedis {
    private Jedis jedis;

    private HashMap<String,HashMap<String,String>> actions;
    private HashMap<String,HashMap<String,String>> counters;

    SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式

    public CustomRedis() throws Exception {
        actions = new HashMap<>();
        counters = new HashMap<>();

        jedis = JedisInstance.getInstance().getResource();
        init();

        FileMonitor m = new FileMonitor(500);//初始化监听时间间隔
        m.monitor(CustomRedis.class.getClassLoader().getResource("./").getPath(), new FileListener(CustomRedis.this)); //设置监听文件夹
        m.onstart();//开启监听
    }

    /**
     * list显示
     */
    public void rlist(){
        List<String> list = jedis.lrange("UserList", 0, -1 );
        System.out.println(list);
    }

    /**
     * set显示
     */
    public void rset(){
        Set<String> set = jedis.smembers("UserSet");
        System.out.println(set);
    }

    /**
     * zset显示
     */
    public void rzset(){
        Set<String> zset = jedis.zrange("UserZSet", 0, -1);
        System.out.println(zset);
    }

    /**
     *增加一个用户
     */
    public void rincr(){

        String save = actions.get("INCR_USER").get("save");
        System.out.println("现在直播间中观众数："+jedis.get("UserNum"));

        int value = Integer.parseInt(counters.get(save).get("valueFields"));
        System.out.println("增加观众数："+value);

        //获取当前系统时间
        String current_time = date_format.format(new Date());
        System.out.println("当前系统时间："+current_time);
        //添加用户
        for(int i=0;i<value;i++){
            jedis.incr("UserNum");
            jedis.sadd("UserSet",current_time);
            jedis.lpush("UserList",current_time);
            jedis.zadd("UserZSet",Integer.parseInt(current_time.substring(4,10)),current_time);
        }
        System.out.println("现在直播间中观众数："+jedis.get("UserNum"));
    }

    /**
     *删除一个用户
     */
    public void rdecr(){
        String save = actions.get("DECR_USER").get("save");
        System.out.println("现在直播间中观众数："+jedis.get("UserNum"));

        int value = Integer.parseInt(counters.get(save).get("valueFields"));
        System.out.println("减少观众数："+value);

        for(int i=0;i<value;i++){
            jedis.decr("UserNum");
        }
        System.out.println("现在直播间中观众数："+jedis.get("UserNum"));
    }

    /**
     * 观众次序
     */
    public void ruserFrence(){
        String valueFields = counters.get(actions.get("SHOW_USER_FREQ").get("retrieve")).get("valueFields");
        System.out.println("当前时间范围："+valueFields);

        //计算数量
        String[] time_array = valueFields.split(" ");
        try {
            Date start_time=date_format.parse(time_array[0]);
            Date end_time=date_format.parse(time_array[1]);
            int num=0;
            List<String> list = jedis.lrange("UserList", 0, -1 );
            for(int i=0;i<list.size();i++){
                Date time=date_format.parse(list.get(i));
                if(time.compareTo(start_time)>=0 && time.compareTo(end_time)<=0){
                    num++;
                }
            }
            System.out.println("直播间进入人数："+num);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    public String readfile(String fileName) {
        String json_str = "";

        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer strbur = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                strbur.append((char) ch);
            }

            fileReader.close();
            reader.close();

            json_str = strbur.toString();
            return json_str;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void init(){
        //清空数据
        actions.clear();
        counters.clear();
        //读取路径
        String action_path= CustomRedis.class.getClassLoader().getResource("./").getPath()+"/Actions.json";
        String counter_path= CustomRedis.class.getClassLoader().getResource("./").getPath()+"/Counter.json";
        //构建数组
        JSONArray action_array = JSON.parseObject(readfile(action_path)).getJSONArray("Actions");
        JSONArray counter_array = JSON.parseObject(readfile(counter_path)).getJSONArray("counters");

        //填充actions数据
        for (int i = 0 ; i < action_array.size();i++){

            JSONObject key = (JSONObject)action_array.get(i);
            HashMap<String,String> action=new HashMap<>();
            String name = (String)key.get("name");
            action.put("name",name);

            JSONArray a = key.getJSONArray("retrieve");
            for(int j=0;j<a.size();j++){
                JSONObject key2 = (JSONObject)a.get(j);
                action.put("retrieve",(String)key2.get("counterName"));
            }

            JSONArray b = key.getJSONArray("save");
            for (int j = 0; j < b.size(); j++) {
                JSONObject key3 = (JSONObject) b.get(j);
                action.put("save", (String) key3.get("counterName"));
            }

            actions.put(name,action);
        }

        //填充counter数据
        for (int i = 0 ; i < counter_array.size();i++){

            JSONObject key = (JSONObject)counter_array.get(i);
            HashMap<String,String> counter=new HashMap<>();

            counter.put("counterName",(String)key.get("counterName"));
            counter.put("counterIndex",(String)key.get("counterIndex"));
            counter.put("type",(String)key.get("type"));
            counter.put("KeyFields",(String)key.get("KeyFields"));
            counter.put("valueFields",(String)key.get("valueFields"));
            counters.put((String)key.get("counterName"),counter);
        }

        List<String> list = jedis.lrange("UserList", 0, -1 );
        jedis.set("UserNum",String.valueOf(list.size()));
        System.out.println(actions);
        System.out.println(counters);
    }
}
