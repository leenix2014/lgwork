package com.lagou.filter.util;

import javax.servlet.http.HttpServletRequest;

public class WebRequestHolder {
    private static ThreadLocal<HttpServletRequest> THREAD_LOCALS = new ThreadLocal<>();

    public static void saveRequest(HttpServletRequest request){
        THREAD_LOCALS.set(request);
    }

    public static HttpServletRequest getRequest(){
        return THREAD_LOCALS.get();
    }

    public static void clear(){
        THREAD_LOCALS.remove();
    }
}
