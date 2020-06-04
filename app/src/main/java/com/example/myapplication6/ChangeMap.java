package com.example.myapplication6;

import java.util.Map;
import java.util.TreeMap;

public class ChangeMap {
    static public <T1, T2, T3>  Map<T2, Map<T1,T3>> change(Map<T1,Map<T2,T3>> map) {
        Map<T2, Map<T1,T3>> tmp1 = new TreeMap<>();
        for (T1 kate : map.keySet()) {
            for (T2 no : map.get(kate).keySet()) {
                Map<T1,T3> tmp2 = new TreeMap<>();
                if (tmp1.containsKey(no)) {
                    tmp1.get(no).put(kate,map.get(kate).get(no));
                }else {
                    tmp2.put(kate, map.get(kate).get(no));
                    tmp1.put(no, tmp2);
                }
            }
        }
        return tmp1;
    }
}