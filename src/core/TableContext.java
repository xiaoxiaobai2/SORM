package core;

import Utils.JavaFileUtils;
import Utils.StringUtils;
import bean.ColumnInfo;
import bean.TableInfo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 存储数据库的所有表信息,并根据表结构生成对应的类结构
 */
public class TableContext {
    /**
     * 表名为key,value为表的信息  ，存储数据库中的每一张表信息
     */
    public static Map<String, TableInfo> tables=new HashMap<>();
    /**
     * 将po 的Class对象和表信息关联起来，便于重用
     */
    public static Map<Class, TableInfo> poClassTableInfo=new HashMap<>();
    private TableContext(){}
    static {
        try {
            Connection connection=DBManger.getConn();
            DatabaseMetaData dbmd=connection.getMetaData();
            ResultSet tableSet=dbmd.getTables(null,"%","%",new String[]{"TABLE"});
            while (tableSet.next()){
                String tableName=(String)tableSet.getObject("TABLE_NAME");
                TableInfo tableInfo=new TableInfo(tableName,new HashMap<>(),new ArrayList<>());
                tables.put(tableName,tableInfo);
                //查找表的字段名
                ResultSet columnSet=dbmd.getColumns(null,"%",tableName,"%");
                while (columnSet.next()){
                    String columnName=columnSet.getString("COLUMN_NAME");
                    ColumnInfo columnInfo=new ColumnInfo(columnName,columnSet.getString("TYPE_NAME"),0);
                    tableInfo.getColumnInfos().put(columnName,columnInfo);
                }
                //查找表的主键
                ResultSet keySet = dbmd.getPrimaryKeys(null,"%",tableName);
                while (keySet.next()){
                    ColumnInfo columnInfo=tableInfo.getColumnInfos().get(keySet.getString("COLUMN_NAME"));
                    columnInfo.setKeyType(1);
                    tableInfo.getOnlyKey().add(columnInfo);
                }
                if (tableInfo.getOnlyKey().size()>0){
                    //取唯一主键，方便实用
                    tableInfo.setOnlyPriKey(tableInfo.getOnlyKey().get(0));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //将所有的表都存储好之后，生成相应的类信息
        updateJavaPOFile();
    }

    /**
     * 根据表结构，更新对应的类,  并把类和表信息对应存储起来
     */
    public static void updateJavaPOFile(){
        for (TableInfo tableInfo:TableContext.tables.values()) {
            JavaFileUtils.createJavaPOFile(tableInfo,new MySqlTypeCovertor());
            try {
                Class clazz=Class.forName(DBManger.getConf().getPoPackages()+
                        "."+StringUtils.firstToUpperCase(tableInfo.getTableName()));
                System.out.println("加载的类名:"+clazz.getName());
                poClassTableInfo.put(clazz,tableInfo);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        TableContext tableContext=new TableContext();
    }
}
