package server;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

public class RequestProcessor extends Thread {

    private Socket socket;
    private Mapper mapper;

    public RequestProcessor(Socket socket, Mapper mapper) {
        this.socket = socket;
        this.mapper = mapper;
    }

    @Override
    public void run() {
        try{
            InputStream inputStream = socket.getInputStream();

            // 封装Request对象和Response对象
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());

            Mapper.Host host = mapper.getMappedHost().get(request.getHost());
            String[] paths = request.getUrl().split("/");
            String contextPath = paths[1];
            String uri = request.getUrl().replace("/"+contextPath, "");
            Mapper.Context context = host.getMappedContext().get(contextPath);

            Map<String, HttpServlet> servletMap =  context.getServletMap();
            HttpServlet servlet = servletMap.get(uri);
            // 静态资源处理
            if(servlet == null) {
                response.outputHtml(host.getAppBase()+request.getUrl());
            }else{
                // 动态资源servlet请求
                HttpServlet httpServlet = servlet;
                httpServlet.service(request,response);
            }

            socket.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
