package core;

import Utils.JDBCUtils;
import Utils.ReflectUtils;
import bean.ColumnInfo;
import bean.TableInfo;
import com.zhanghao.po.Emp;

import java.io.BufferedWriter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlQuery implements Query {
    @Override
    public void insert(Object object) {
        Class clazz=object.getClass();
        TableInfo tableInfo=TableContext.poClassTableInfo.get(clazz);
        Field[] fields=clazz.getDeclaredFields();
        StringBuffer stringBuffer=new StringBuffer();
        List<Object> fieldValueList=new ArrayList<>();
        //insert into tableName (fieldName,fieldName) values(?,?)
        stringBuffer.append("insert into "+tableInfo.getTableName()+" (");
        int totalField=0;
        for (Field field:fields){
            String fieldName=field.getName();
            Object fieldValue=ReflectUtils.invokeGet(object,fieldName);
            if (fieldValue!=null){
                stringBuffer.append(fieldName+",");
                totalField++;
                fieldValueList.add(fieldValue);
            }
        }
        stringBuffer.setCharAt(stringBuffer.length()-1,')');
        stringBuffer.append(" values (");
        for (int i = 0; i < totalField; i++) {
            stringBuffer.append("?,");
        }
        stringBuffer.setCharAt(stringBuffer.length()-1,')');
        excuteSql(stringBuffer.toString(),fieldValueList.toArray());
    }

    @Override
    public void delete(Class clazz, Object id) {
        //Object id 为主键的值
        TableInfo tableInfo=TableContext.poClassTableInfo.get(clazz);
        ColumnInfo onlyPriKey=tableInfo.getOnlyPriKey();
        String sql="delete from "+tableInfo.getTableName()+" where "+onlyPriKey.getName()+"=?";
        excuteSql(sql,new Object[]{id});
    }

    @Override
    public void delete(Object object) {
        //没有传主键，需要通过反射找到主键的值
        Class clazz=object.getClass();
        TableInfo tableInfo=TableContext.poClassTableInfo.get(clazz);
        ColumnInfo columnInfo=tableInfo.getOnlyPriKey();
        Object priKeyValue= ReflectUtils.invokeGet(object,columnInfo.getName());
//        System.out.println((int)priKeyValue);
        delete(clazz,priKeyValue);
    }

    @Override
    public int update(Object object, String[] fields) {
        //update tableName set field=?,field=? where id=?
        Class clazz=object.getClass();
        List<Object> fieldValueList=new ArrayList<>();
        TableInfo tableInfo=TableContext.poClassTableInfo.get(clazz);
        ColumnInfo columnInfo=tableInfo.getOnlyPriKey();
        Object priKeyValue=ReflectUtils.invokeGet(object,columnInfo.getName());
        StringBuffer sb=new StringBuffer();
        sb.append("update "+tableInfo.getTableName()+" set ");
        for (int i = 0; i < fields.length; i++) {
            Object fieldValue=ReflectUtils.invokeGet(object,fields[i]);
            fieldValueList.add(fieldValue);
            sb.append(fields[i]+"=?,");
        }
        fieldValueList.add(priKeyValue);
        sb.setCharAt(sb.length()-1,' ');
        sb.append("where ");
        sb.append(columnInfo.getName()+"=?");

        return excuteSql(sb.toString(),fieldValueList.toArray());
    }

    @Override
    public List queryRows(String sql, Object object, String[] params) {
        return null;
    }

    @Override
    public Object[] queryUniqueRow(String sql, Object object, String[] params) {
        return new Object[0];
    }

    @Override
    public Object queryValue(String sql, Object object, String[] params) {
        return null;
    }

    @Override
    public Number queryNumber(String sql, Object object, String[] params) {
        return null;
    }

    @Override
    public int excuteSql(String sql, Object[] params) {
        Connection connection=DBManger.getConn();
        PreparedStatement ps=null;
        ResultSet set=null;
        int total=0;
        try {
            ps=connection.prepareStatement(sql);
            JDBCUtils.handleParams(ps,params);
            System.out.println(ps.toString());
            total=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBManger.close(connection,ps,set);
        }
        return total;
    }

    public static void main(String[] args) {
        Emp emp=new Emp();
        emp.setId(2);
        emp.setDepartment("测试");
        emp.setSalary(99999.0);
        new MySqlQuery().update(emp,new String[]{"salary"});
    }
}
