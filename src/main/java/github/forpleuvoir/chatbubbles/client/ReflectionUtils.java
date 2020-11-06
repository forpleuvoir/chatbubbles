package github.forpleuvoir.chatbubbles.client;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author forpleuvoir
 * @belongsProject suikamod
 * @belongsPackage com.forpleuvoir.chatbubbles
 * @className ReflectionUtils
 * @createTime 2020/10/25 13:19
 */
public class ReflectionUtils {
    public ReflectionUtils() {
    }

    public static Object getPrivateFieldValueByType(Object o, Class<?> objectClasstype, Class<?> fieldClasstype) {
        return getPrivateFieldValueByType(o, objectClasstype, fieldClasstype, 0);
    }

    public static Object getPrivateFieldValueByType(Object o, Class<?> objectClasstype, Class<?> fieldClasstype, int index) {
        Class objectClass;
        if (o != null) {
            objectClass = o.getClass();
        } else {
            objectClass = objectClasstype;
        }

        while(!objectClass.equals(objectClasstype) && objectClass.getSuperclass() != null) {
            objectClass = objectClass.getSuperclass();
        }

        int counter = 0;
        Field[] fields = objectClass.getDeclaredFields();

        for(int i = 0; i < fields.length; ++i) {
            if (fieldClasstype.equals(fields[i].getType())) {
                if (counter == index) {
                    try {
                        fields[i].setAccessible(true);
                        return fields[i].get(o);
                    } catch (IllegalAccessException var9) {
                    }
                }

                ++counter;
            }
        }

        return null;
    }

    public static Object getFieldValueByName(Object o, String fieldName) {
        Field[] fields = o.getClass().getFields();

        for(int i = 0; i < fields.length; ++i) {
            if (fieldName.equals(fields[i].getName())) {
                try {
                    fields[i].setAccessible(true);
                    return fields[i].get(o);
                } catch (IllegalAccessException var5) {
                }
            }
        }

        return null;
    }

    public static ArrayList<Field> getFieldsByType(Object o, Class<?> objectClassBaseType, Class<?> fieldClasstype) {
        ArrayList<Field> matches = new ArrayList();

        for(Class objectClass = o.getClass(); !objectClass.equals(objectClassBaseType) && objectClass.getSuperclass() != null; objectClass = objectClass.getSuperclass()) {
            Field[] fields = objectClass.getDeclaredFields();

            for(int i = 0; i < fields.length; ++i) {
                if (fieldClasstype.isAssignableFrom(fields[i].getType())) {
                    fields[i].setAccessible(true);
                    matches.add(fields[i]);
                }
            }
        }

        return matches;
    }

    public static Field getFieldByType(Object o, Class<?> objectClasstype, Class<?> fieldClasstype) {
        return getFieldByType(o, objectClasstype, fieldClasstype, 0);
    }

    public static Field getFieldByType(Object o, Class<?> objectClasstype, Class<?> fieldClasstype, int index) {
        Class objectClass;
        for(objectClass = o.getClass(); !objectClass.equals(objectClasstype) && objectClass.getSuperclass() != null; objectClass = objectClass.getSuperclass()) {
        }

        int counter = 0;
        Field[] fields = objectClass.getDeclaredFields();

        for(int i = 0; i < fields.length; ++i) {
            if (fieldClasstype.equals(fields[i].getType())) {
                if (counter == index) {
                    fields[i].setAccessible(true);
                    return fields[i];
                }

                ++counter;
            }
        }

        return null;
    }

    public static Method getMethodByType(Class<?> objectType, Class<?> returnType, Class<?>... parameterTypes) {
        return getMethodByType(0, objectType, returnType, parameterTypes);
    }

    public static Method getMethodByType(int index, Class<?> objectType, Class<?> returnType, Class<?>... parameterTypes) {
        Method[] methods = objectType.getDeclaredMethods();
        int counter = 0;

        for(int i = 0; i < methods.length; ++i) {
            if (returnType.equals(methods[i].getReturnType())) {
                Class<?>[] methodParameterTypes = methods[i].getParameterTypes();
                if (parameterTypes.length == methodParameterTypes.length) {
                    boolean match = true;

                    for(int t = 0; t < parameterTypes.length; ++t) {
                        if (parameterTypes[t] != methodParameterTypes[t]) {
                            match = false;
                        }
                    }

                    if (counter == index && match) {
                        methods[i].setAccessible(true);
                        return methods[i];
                    }
                }

                ++counter;
            }
        }

        return null;
    }

    public static boolean classExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException var2) {
            return false;
        }
    }
}
