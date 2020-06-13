package com.lagou.edu.factory;

import com.lagou.edu.anno.Autowired;
import com.lagou.edu.anno.Component;
import com.lagou.edu.utils.ScanUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 应癫
 *
 * 工厂类，生产对象（使用反射技术）
 */
public class BeanFactory {

    /**
     * 任务一：读取解析xml，通过反射技术实例化对象并且存储待用（map集合）
     * 任务二：对外提供获取实例对象的接口（根据id获取）
     */

    private static Map<String,Object> map = new HashMap<>();  // 存储对象
    private static Map<String, Object> typeMap = new HashMap<>();// 方便实现Autowired注入的Map<Class.name, bean>


    static {
        // 任务一：读取解析xml，通过反射技术实例化对象并且存储待用（map集合）
        // 加载xml
//        InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
//        // 解析xml
//        SAXReader saxReader = new SAXReader();
//        try {
//            Document document = saxReader.read(resourceAsStream);
//            Element rootElement = document.getRootElement();
//            List<Element> beanList = rootElement.selectNodes("//bean");
//            for (int i = 0; i < beanList.size(); i++) {
//                Element element =  beanList.get(i);
//                // 处理每个bean元素，获取到该元素的id 和 class 属性
//                String id = element.attributeValue("id");        // accountDao
//                String clazz = element.attributeValue("class");  // com.lagou.edu.dao.impl.JdbcAccountDaoImpl
//                // 通过反射技术实例化对象
//                Class<?> aClass = Class.forName(clazz);
//                Object o = aClass.newInstance();  // 实例化之后的对象
//
//                // 存储到map中待用
//                map.put(id,o);
//                typeMap.put(aClass.getCanonicalName(), o);
//                for(Class interf : aClass.getInterfaces()) {
//                    typeMap.put(interf.getCanonicalName(), o);
//                }
//            }

        try {
            // Component处理
            Set<Class> classes = ScanUtils.getClasssFromPackage("com.lagou.edu");
            for(Class clazz : classes) {
                Component service = (Component)clazz.getAnnotation(Component.class);
                if (service == null) {
                    continue;
                }
                Object o = clazz.newInstance();
                String id = service.value();
                if("".equals(id)) {
                    id = firstLowerCase(clazz.getSimpleName());
                }
                map.put(id, o);
                typeMap.put(clazz.getCanonicalName(), o);
                for(Class interf : clazz.getInterfaces()) {
                    map.put(firstLowerCase(interf.getSimpleName()), o);
                    typeMap.put(interf.getCanonicalName(), o);
                }
            }

            // Autowired处理
            for (Object bean : map.values()) {
                Class beanClazz = bean.getClass();
                for (Field field : beanClazz.getDeclaredFields()) {
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    if (autowired == null) {
                        continue;
                    }
                    field.setAccessible(true);
                    String id = autowired.value();
                    if(!"".equals(id)){
                        // autowired by name
                        field.set(bean, map.get(id));
                    } else {
                        // autowired by type
                        Object diBean = typeMap.get(field.getType().getCanonicalName());
                        field.set(bean, diBean);
                    }
                }
            }

            // 实例化完成之后维护对象的依赖关系，检查哪些对象需要传值进入，根据它的配置，我们传入相应的值
            // 有property子元素的bean就有传值需求
//            List<Element> propertyList = rootElement.selectNodes("//property");
//            // 解析property，获取父元素
//            for (int i = 0; i < propertyList.size(); i++) {
//                Element element =  propertyList.get(i);   //<property name="AccountDao" ref="accountDao"></property>
//                String name = element.attributeValue("name");
//                String ref = element.attributeValue("ref");
//
//                // 找到当前需要被处理依赖关系的bean
//                Element parent = element.getParent();
//
//                // 调用父元素对象的反射功能
//                String parentId = parent.attributeValue("id");
//                Object parentObject = map.get(parentId);
//                // 遍历父对象中的所有方法，找到"set" + name
//                Method[] methods = parentObject.getClass().getMethods();
//                for (int j = 0; j < methods.length; j++) {
//                    Method method = methods[j];
//                    if(method.getName().equalsIgnoreCase("set" + name)) {  // 该方法就是 setAccountDao(AccountDao accountDao)
//                        method.invoke(parentObject,map.get(ref));
//                    }
//                }
//
//                // 把处理之后的parentObject重新放到map中
//                map.put(parentId,parentObject);
//            }



//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
        }

    }

    public static String firstLowerCase(String name){
        if (name == null || name.length() == 0){
            return "";
        }
        return name.substring(0,1).toLowerCase() + name.substring(1);
    }


    // 任务二：对外提供获取实例对象的接口（根据id获取）
    public static  Object getBean(String id) {
        Object obj = map.get(id);
        if (obj == null){
            System.out.println("Can't find object:"+id);
        }
        return obj;
    }

}
