package Utils;

import bean.ColumnInfo;
import bean.Configuration;
import bean.JavaFieldGetSet;
import bean.TableInfo;
import core.DBManger;
import core.TypeConvertor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JavaFileUtils {
    /**
     * 将字段信息 生成为  javaBean
     * @param columnInfo 字段信息
     * @param typeConvertor 转换规则
     * @return 返回创建好的javabean源码
     */
    public static JavaFieldGetSet createJavaFieldGetSet(ColumnInfo columnInfo, TypeConvertor typeConvertor){
        JavaFieldGetSet javaFieldGetSet=new JavaFieldGetSet();
        //生成 private type typeName;
        javaFieldGetSet.setPrivateTypeName("\tprivate "+typeConvertor.dbType2javaType(columnInfo.getDataType())+" "+columnInfo.getName()+";\n");

        //生成get方法
        StringBuffer createGet=new StringBuffer();
        createGet.append("\tpublic "+typeConvertor.dbType2javaType(columnInfo.getDataType())+" get"+StringUtils.firstToUpperCase(columnInfo.getName())+"(){\n");
        createGet.append("\t\treturn "+columnInfo.getName()+";\n\t}\n");
        javaFieldGetSet.setCreateGet(createGet.toString());

        //生成set方法
        StringBuffer createSet=new StringBuffer();
        createSet.append("\tpublic void set"+StringUtils.firstToUpperCase(columnInfo.getName())+
                "("+typeConvertor.dbType2javaType(columnInfo.getDataType())+" "+columnInfo.getName()+"){\n");
        createSet.append("\t\tthis."+columnInfo.getName()+"="+columnInfo.getName()+";\n\t}\n");
        javaFieldGetSet.setCreateSet(createSet.toString());

        return javaFieldGetSet;
    }
    public static String javaFieldSrc(TableInfo tableInfo, TypeConvertor typeConvertor){
        Map<String,ColumnInfo> columns=tableInfo.getColumnInfos();
        List<JavaFieldGetSet> javaFieldGetSetList=new ArrayList<>();

        for (ColumnInfo columnInfo:columns.values()){
            javaFieldGetSetList.add(createJavaFieldGetSet(columnInfo,typeConvertor));
        }
        StringBuffer sb=new StringBuffer();
        //生成package
        sb.append("package "+ DBManger.getConf().getPoPackages()+";\n\n");

        //生成import
        sb.append("import java.sql.*;\n");
        sb.append("import java.lang.*;\n\n");

        //生成类的声明
        sb.append("public class "+StringUtils.firstToUpperCase(tableInfo.getTableName())+"{\n");

        //生成属性定义
        for (int i = 0; i < javaFieldGetSetList.size(); i++) {
            sb.append(javaFieldGetSetList.get(i).getPrivateTypeName());
        }

        //生成空构造器
        sb.append("\tpublic "+StringUtils.firstToUpperCase(tableInfo.getTableName())+"(){\n\t}\n");

        //生成set方法
        for (int i = 0; i < javaFieldGetSetList.size(); i++) {
            sb.append(javaFieldGetSetList.get(i).getCreateSet());
        }

        //生成get方法
        for (int i = 0; i < javaFieldGetSetList.size(); i++) {
            sb.append(javaFieldGetSetList.get(i).getCreateGet());
        }

        //生成结尾标识符
        sb.append("}");
        return sb.toString();
    }

    public static void createJavaPOFile(TableInfo tableInfo,TypeConvertor typeConvertor){
        Configuration configuration=DBManger.getConf();

        String srcPath=configuration.getSrcPath()+"\\";
        String poPath=configuration.getPoPackages().replaceAll("\\.","\\\\");
        System.out.println(srcPath+poPath);
        File file=new File(srcPath+poPath);
        if (!file.exists())
            file.mkdirs();
        BufferedWriter bw=null;
        try {
            bw=new BufferedWriter(new FileWriter(new File(file,StringUtils.firstToUpperCase(tableInfo.getTableName()+".java"))));
            bw.write(javaFieldSrc(tableInfo,typeConvertor));
            bw.flush();
            System.out.println("成功生成"+StringUtils.firstToUpperCase(tableInfo.getTableName())+".java 文件！");
        } catch (IOException e) {
            System.err.println("创建流失败！");
            e.printStackTrace();
        }finally {
            if (bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    System.err.println("关闭失败！");
                    e.printStackTrace();
                }
            }
        }
    }

//    public static void main(String[] args) {
//        ColumnInfo columnInfo=new ColumnInfo("name","varchar",0);
//        System.out.println(createJavaFieldGetSet(columnInfo,new MySqlTypeCovertor()).toString());
//    }
}
