package core;

import Utils.JDBCUtils;
import Utils.ReflectUtils;
import bean.ColumnInfo;
import bean.PoTable;
import bean.TableInfo;
import com.zhanghao.po.Emp;

import java.lang.reflect.Field;
import java.sql.*;
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
        executeSql(stringBuffer.toString(),fieldValueList.toArray());
    }

    @Override
    public void delete(Class clazz, Object id) {
        //Object id 为主键的值
        TableInfo tableInfo=TableContext.poClassTableInfo.get(clazz);
        ColumnInfo onlyPriKey=tableInfo.getOnlyPriKey();
        String sql="delete from "+tableInfo.getTableName()+" where "+onlyPriKey.getName()+"=?";
        executeSql(sql,new Object[]{id});
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

        return executeSql(sb.toString(),fieldValueList.toArray());
    }

    @Override
    public List queryRows(String sql, Class object, Object[] params) {
        List objectList=null;
        Connection connection=DBManger.getConn();
        PreparedStatement ps=null;
        ResultSet set=null;
        try {
            ps=connection.prepareStatement(sql);
            JDBCUtils.handleParams(ps,params);
            System.out.println("组合后的SQL语句："+ps.toString());
            set=ps.executeQuery();
            ResultSetMetaData rsmd=set.getMetaData();
            while (set.next()){
                if (objectList==null){
                    objectList=new ArrayList();
                }
                Object obj=object.newInstance();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    String columnName=rsmd.getColumnLabel(i+1);
                    Object columnValue=set.getObject(i+1);
                    if (columnValue!=null)
                        ReflectUtils.invokeSet(obj,columnName,columnValue);
                }
                objectList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }finally {
            DBManger.close(connection,ps,set);
        }
        return objectList;
    }

    @Override
    public Object queryUniqueRow(String sql, Class object, Object[] params) {
        List list=queryRows(sql,object,params);
        return (list==null)?null:list.get(0);
    }

    @Override
    public Object queryValue(String sql, Object[] params) {
        Object value=null;
        Connection connection=DBManger.getConn();
        PreparedStatement ps=null;
        ResultSet set=null;
        try {
            ps=connection.prepareStatement(sql);
            JDBCUtils.handleParams(ps,params);
            System.out.println("组合后的SQL语句："+ps.toString());
            set=ps.executeQuery();
            while (set.next()){
                value=set.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBManger.close(connection,ps,set);
        }
        return value;
    }

    @Override
    public Number queryNumber(String sql, Object[] params) {
        Object obj=queryValue(sql,params);
        if(obj instanceof Number){
            return (Number)obj;
        }
        return null;
    }

    @Override
    public int executeSql(String sql, Object[] params) {
        Connection connection=DBManger.getConn();
        PreparedStatement ps=null;
        ResultSet set=null;
        int total=0;
        try {
            ps=connection.prepareStatement(sql);
            JDBCUtils.handleParams(ps,params);
            total=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBManger.close(connection,ps,set);
        }
        return total;
    }

    public static void main(String[] args) {
//        Emp emp=new Emp();
//        emp.setId(2);
//        emp.setDepartment("测试");
//        emp.setSalary(99999.0);
//        new MySqlQuery().update(emp,new String[]{"salary"});

//        List<Emp> list=new MySqlQuery().queryRows("select name,salary from emp where salary>?",Emp.class,new Object[]{10000});
//        for (Emp e:list){
//            System.out.println(e.getName());
//            System.out.println(e.getSalary());
//        }

//        //复杂的查询语句，需要先封装一个javabean
//        String sql="SELECT emp.id empId,emp.name,emp.salary,emp.department,student.ID stuID FROM emp "+
//                "JOIN student on emp.name=student.Name";
//        List<PoTable> list=new MySqlQuery().queryRows(sql,PoTable.class,null);
//        for (PoTable e:list){
//            System.out.println(e.getName()+"--"+e.getSalary()+"--"+e.getEmpId()+"--"+e.getStuId());
//        }
        System.out.println(new MySqlQuery().queryValue("select count(*) from emp where salary>?",new Object[]{10000}));
    }
}
