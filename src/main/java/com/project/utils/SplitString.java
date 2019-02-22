package com.project.utils;

import java.util.*;

/**
 * Created by 陈梦圆 on 2017/10/26 0026.
 * 对字符串进行分词
 * 这里是对查询出的食物进行分词
 */
public class SplitString {
    public static List<String> getWord(String str){
        if(str == null){
            return null;
        }
        //分词
        String[] strArray = str.split("、");
        List<String> list = stringToList(strArray);
        //去重，使用set集合
        Set<String> set = new HashSet<>(list);
        String[] result = new String[set.size()];
        Iterator<String> iterator = set.iterator();   //迭代器
        for(int i=0;iterator.hasNext();i++){
            result[i] = (String) iterator.next();
        }
        // Arrays.sort(result);
        return stringToList(result);   //将list添加在目标list的后面
    }

    private static List<String> stringToList(String[] strings){
        List<String> stringList = new ArrayList<>();
        for(int i=0;i<strings.length;i++){
            stringList.add(strings[i]);
        }
        return stringList;
    }
}
