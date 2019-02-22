package com.project.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dobility
 */
public class RandomUtils {

    // 获取指定范围的随机整数，包含上下界
    public static int getRandom(int min, int max) {
        int r = (int)(Math.random() * (max - min));
        return r + min;
    }

    // 获取指定范围内的无重复随机数组，包含上下界
    public static int[] getRandomArray(int min, int max) {
        List<Integer> list = new ArrayList<>();
        int[] randList = new int[max - min + 1];
        for (int i = min; i <= max; i++) {
            list.add(i);
        }
        max = max - min;
        int j = 0;
        while (!list.isEmpty()) {
            int i = getRandom(0, max);
            randList[j++] = list.get(i);
            list.remove(i);
            max--;
        }
        return randList;
    }

}
