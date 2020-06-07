package com.example.myapplication6;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

    public class ChangeMap4 {
        static public <T1,T2,T3,T4> Map<T3,Map<T2,Map<T1,T4>>> change(Map<T1, Map<T2, Map<T3,T4>>> map){
            Map map1 = change1(map);
            Map map2 = change2(map1);
            Map map3 = change1(map2);
            
            return map3;

        }
        static public <T1, T2, T3, T4> Map<T1, Map<T3, Map<T2, T4>>> change1(Map<T1, Map<T2, Map<T3, T4>>> map) {
            Map<T1, Map<T3, Map<T2, T4>>> tmp1 = new HashMap<>();
            for (T1 kate : map.keySet()) {
                Map<T3, Map<T2, T4>> tmp2 = new HashMap<>();
                for (T2 no : map.get(kate).keySet()) {
                    for (T3 day : map.get(kate).get(no).keySet()) {
                        Map<T2, T4> tmp3 = new HashMap<>();
                        if (tmp2.containsKey(day)) {
                            tmp2.get(day).put(no, map.get(kate).get(no).get(day));
                        } else {
                            tmp3.put(no, map.get(kate).get(no).get(day));
                            tmp2.put(day, tmp3);
                        }
                    }
                }
                tmp1.put(kate, tmp2);
            }
            return tmp1;
        }

        static public <T1, T2, T3, T4> Map<T2, Map<T1, Map<T3, T4>>> change2(Map<T1, Map<T2, Map<T3, T4>>> tmp) {
            Map<T2, Map<T1, Map<T3, T4>>> tmp1 = new HashMap<>();
            for (T1 kate : tmp.keySet()) {
                for (T2 day : tmp.get(kate).keySet()) {
                    Map<T1, Map<T3, T4>> tmp2 = new HashMap<>();
                    if (tmp1.containsKey(day)) {
                        tmp1.get(day).put(kate, tmp.get(kate).get(day));
                    } else {
                        tmp2.put(kate, tmp.get(kate).get(day));
                        tmp1.put(day, tmp2);
                    }
                }
            }
            return tmp1;
        }
    }

