package com.swustacm.poweroj.common;


import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * 将对象转为Map 用于表格导入
 * @author xingzi
 * @date 2019 07 05  11:15
 */
public class PojoToMapUtils {

    public static HashMap<String,Object> pojotoMap(Object object){
        HashMap<String,Object> map = new HashMap<>(16);
        if (object ==null){
            object =  new Object();
        }
        Field[] methods = object.getClass().getDeclaredFields();
        Object finalObject = object;
        Arrays.stream(methods)
                  .forEach(
                          field -> {
                              if (haveGetter(field, finalObject)) {
                                  map.put(cCtoDownLine(field.getName()),
                                          getValue(field, finalObject) == null ? "" : getValue(field, finalObject));
                              }
                          }
                  );

        return map;
    }
    public static HashMap<String,Object> pojotoMapNoCCToDown(Object object){
        HashMap<String,Object> map = new HashMap<>(16);
        if (object ==null){
            object =  new Object();
        }
        Field[] methods = object.getClass().getDeclaredFields();
        Object finalObject = object;
        Arrays.stream(methods)
                .forEach(
                        field -> {
                            if (haveGetter(field, finalObject)) {
                                map.put(field.getName(),
                                        getValue(field, finalObject) == null ? "" : getValue(field, finalObject));
                            }
                        }
                );

        return map;
    }

    /**
     * @param '得到类属性值'
     * @param field
     * @param object
     * @return
     */
    public static Object getValue(Field field,Object object){
        try {
            String firstLetter = field.getName().substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + field.getName().substring(1);

            if(haveGetter(field,object)){
                Method method = object.getClass().getMethod(getter);
                return method.invoke(object);
            }
            else {
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 驼峰转想下划线
     * @param name
     * @return
     */
    private static String cCtoDownLine(String name){

        name = name.replaceAll("([A-Z])","_$0");
        return name.toLowerCase();
    }

    /**
     * 判断是否有getter方法
     * @param field
     * @param object
     * @return
     */
    private static boolean haveGetter(Field field,Object object){
        String firstLetter = field.getName().substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + field.getName().substring(1);

        Method[] methods = object.getClass().getMethods();
        return Arrays.stream(methods).anyMatch(
                method -> getter.equals(method.getName()));
    }

    /**
     * deepcopy
     * @param obj
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T obj){
        T cloneObj = null;
        try {
            //写入字节流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream obs = new   ObjectOutputStream(out);
            obs.writeObject(obj);
            obs.close();

            //分配内存，写入原始对象，生成新对象
            ByteArrayInputStream ios = new  ByteArrayInputStream(out.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(ios);
            //返回生成的新对象
            cloneObj = (T) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloneObj;
    }

    /**
     *反转map
     * @param map map
     * @return mapReverse
     */
    public static Map<String,Object> mapReverse(Map<String,Object> map){
         Map<String,Object> map1 = new HashMap<>(map.size());
        for(Map.Entry<String,Object> entry : map.entrySet()){
            map1.put((String) entry.getValue(),entry.getKey());
        }
        return map1;
    }
}
