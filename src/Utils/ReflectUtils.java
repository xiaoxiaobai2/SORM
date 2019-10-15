package Utils;


import bean.ColumnInfo;
import bean.TableInfo;
import core.MySqlTypeCovertor;
import core.TableContext;

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

    public static void invokeSet(Object obj, String fieldName, Object fieldValue){
        try {
            Class c=obj.getClass();
//            TableInfo tableInfo=TableContext.poClassTableInfo.get(c);
//            ColumnInfo columnInfo=tableInfo.getColumnInfos().get(fieldName);
//            String o=new MySqlTypeCovertor().dbType2javaType(columnInfo.getDataType());
//            o+".class";
//            System.out.println("dalsj alskjd lakj:::::::::::::::::"+o);
//            System.out.println("-----------"+o.getClass());
////            MySqlTypeCovertor mySqlTypeCovertor=new MySqlTypeCovertor();
////            mySqlTypeCovertor.dbType2javaType()
//            System.out.println(fieldValue.getClass());


//            System.out.println("通过get找打对应的类型"+invokeGet(obj,fieldName));
//            System.out.println(fieldValue.getClass());
            Method m=c.getMethod("set"+StringUtils.firstToUpperCase(fieldName),fieldValue.getClass());
            m.invoke(obj,fieldValue);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
