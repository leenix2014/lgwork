package com.lagou.filter;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Statistics implements Runnable{

    private List<Info> history = new CopyOnWriteArrayList<>();

    Statistics(){
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this, 1, 1, TimeUnit.MINUTES);
    }


    @Override
    public void run() {
        long minuteAgo = System.currentTimeMillis() - 60 * 1000;
        for (Info info : history){
            if (info.endTime < minuteAgo){
                history.remove(info);
            }
        }
    }

    class Info implements Comparable<Info> {
        String methodName;
        long duration;
        long endTime;
        Info(String methodName, long duration, long endTime) {
            this.methodName = methodName;
            this.duration = duration;
            this.endTime = endTime;
        }

        @Override
        public int compareTo(Info o) {
            return Long.compare(duration, o.duration);
        }
    }

    void record(String methodName, long duration, long endTime){
        history.add(new Info(methodName, duration, endTime));
    }

    void printMonitorInfo(){
        long end = System.currentTimeMillis();
        long start = end - 60 * 1000;
        Map<String, List<Info>> methodLists = new HashMap<>();
        for (Info info : history){
            String methodName = info.methodName;
            if (info.endTime < start || info.endTime > end){
                continue;
            }
            List<Info> list = methodLists.computeIfAbsent(methodName, k -> new ArrayList<>());
            list.add(info);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(new Date());
        for(Map.Entry<String, List<Info>> entry : methodLists.entrySet()) {
            String methodName = entry.getKey();
            List<Info> times = entry.getValue();
            Collections.sort(times);

            System.out.println(String.format("%s %s一分钟内总请求数为:%s TP90为:%s, TP99为:%s", now, methodName, times.size(), getTP90(times), getTP99(times)));
        }
        System.out.println("------------------------------------");
    }

    private long getTP90(List<Info> sortedList){
        Info item = getTP(sortedList, 0.90);
        if (item == null){
            return 0;
        }
        return item.duration;
    }

    private long getTP99(List<Info> sortedList){
        Info item = getTP(sortedList, 0.99);
        if (item == null){
            return 0;
        }
        return item.duration;
    }

    private <T> T getTP(List<T> sortedList, double f){
        int size = sortedList.size();
        int index = (int) Math.floor(size*f);
        if (index<0 || index > sortedList.size()){
            return null;
        }
        return sortedList.get(index);
    }
}
