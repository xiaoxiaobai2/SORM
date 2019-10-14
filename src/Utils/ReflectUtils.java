package Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils {
    public static Object invokeGet(Object obj,String fieldName){
        try {
            Class c=obj.getClass();
            Method m=c.getMethod("get"+StringUtils.firstToUpperCase(fieldName),null);
            return m.invoke(obj,null);//通过反射找到主键的值
        } catch (NoSuchMethodException e) {
            System.err.println("没找到方法！");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.err.println("访问不合法！");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
