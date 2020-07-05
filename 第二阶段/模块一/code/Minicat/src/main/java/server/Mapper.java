package server;

import java.util.HashMap;
import java.util.Map;

public class Mapper {
    private Map<String, Host> mappedHost = new HashMap<>();

    public Map<String, Host> getMappedHost() {
        return mappedHost;
    }

    public void setMappedHost(Map<String, Host> mappedHost) {
        this.mappedHost = mappedHost;
    }

    protected static final class Host{
        public Host(String appBase){
            this.appBase = appBase;
        }
        private String appBase;
        private Map<String, Context> mappedContext = new HashMap<>();

        public String getAppBase() {
            return appBase;
        }

        public void setAppBase(String appBase) {
            this.appBase = appBase;
        }

        public Map<String, Context> getMappedContext() {
            return mappedContext;
        }

        public void setMappedContext(Map<String, Context> mappedContext) {
            this.mappedContext = mappedContext;
        }
    }

    protected static final class Context{
        private Map<String,HttpServlet> servletMap = new HashMap<>();

        public Map<String, HttpServlet> getServletMap() {
            return servletMap;
        }

        public void setServletMap(Map<String, HttpServlet> servletMap) {
            this.servletMap = servletMap;
        }
    }
}
