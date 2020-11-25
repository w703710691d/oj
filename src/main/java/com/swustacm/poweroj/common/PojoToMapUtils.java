package com.swustacm.poweroj.common;


import com.swustacm.poweroj.common.util.JavaClassUtils;
import org.apache.poi.ss.usermodel.Cell;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * 将对象转为Map 用于表格导入
 *
 * @author xingzi
 * @date 2019 07 05  11:15
 */
public class PojoToMapUtils {

    public static HashMap<String, Object> pojotoMap(Object object) {
        HashMap<String, Object> map = new HashMap<>(16);
        if (object == null) {
            object = new Object();
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

    public static HashMap<String, Object> pojotoMapNoCCToDown(Object object) {
        HashMap<String, Object> map = new HashMap<>(16);
        if (object == null) {
            object = new Object();
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
    public static Object getValue(Field field, Object object) {
        try {
            String firstLetter = field.getName().substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + field.getName().substring(1);

            if (haveGetter(field, object)) {
                Method method = object.getClass().getMethod(getter);
                Object o = method.invoke(object);
                return method.invoke(object);
            } else {
                throw new NoSuchMethodException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setValue(Field field, Object object, Cell cell) {
        try {
            String firstLetter = field.getName().substring(0, 1).toUpperCase();
            String setter = "set" + firstLetter + field.getName().substring(1);
            if (haveSetter(field, object)) {
                Method method = object.getClass().getMethod(setter, field.getType());
                if (!JavaClassUtils.isExcelNull(cell.getStringCellValue())) {
                    if ("int".equals(field.getType().getSimpleName()) ||
                            "Integer".equals(field.getType().getSimpleName())) {
                        method.invoke(object, Integer.valueOf(cell.getStringCellValue()));
                    }
                    //取到值为true 未取到为false
                    if ("boolean".equals(field.getType().getSimpleName().toLowerCase())) {
                        method.invoke(object, true);
                    }
                    if ("float".equals(field.getType().getSimpleName().toLowerCase()) ||
                            "double".equals(field.getType().getSimpleName().toLowerCase())) {
                        method.invoke(object, cell.getNumericCellValue());
                    }
                    if ("String".equals(field.getType().getSimpleName())) {
                        method.invoke(object, cell.getStringCellValue());
                    }
                    if ("LocalDateTime".equals(field.getType().getSimpleName()) ||
                            "Date".equals(field.getType().getSimpleName())) {
                        method.invoke(object, cell.getDateCellValue());
                    }
                }
            } else {
                throw new NoSuchMethodException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 驼峰转想下划线
     *
     * @param name
     * @return
     */
    private static String cCtoDownLine(String name) {

        name = name.replaceAll("([A-Z])", "_$0");
        return name.toLowerCase();
    }

    /**
     * 判断是否有getter方法
     *
     * @param field
     * @param object
     * @return
     */
    private static boolean haveGetter(Field field, Object object) {
        String firstLetter = field.getName().substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + field.getName().substring(1);

        Method[] methods = object.getClass().getMethods();
        return Arrays.stream(methods).anyMatch(
                method -> getter.equals(method.getName()));
    }

    private static boolean haveSetter(Field field, Object object) {
        String firstLetter = field.getName().substring(0, 1).toUpperCase();
        String getter = "set" + firstLetter + field.getName().substring(1);
        Method[] methods = object.getClass().getMethods();
        return Arrays.stream(methods).anyMatch(
                method -> getter.equals(method.getName()));
    }

    /**
     * deepcopy
     *
     * @param obj
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T obj) {
        T cloneObj = null;
        try {
            //写入字节流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream obs = new ObjectOutputStream(out);
            obs.writeObject(obj);
            obs.close();
            //分配内存，写入原始对象，生成新对象
            ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());
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
     * 反转map
     *
     * @param map map
     * @return mapReverse
     */
    public static Map<String, Object> mapReverse(Map<String, Object> map) {
        Map<String, Object> map1 = new HashMap<>(map.size());
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            map1.put((String) entry.getValue(), entry.getKey());
        }
        return map1;
    }

    public static void main(String[] args) {
        boolean a = false;

    }
}
