package core;

public class MySqlTypeCovertor implements TypeConvertor {

    @Override
    public String dbType2javaType(String columnType) {
        String s=null;
        if ("varchar".equalsIgnoreCase(columnType)||"char".equalsIgnoreCase(columnType))
            s="String";
        if ("int".equalsIgnoreCase(columnType)||
                "mediumint".equalsIgnoreCase(columnType)||
                "tinyint".equalsIgnoreCase(columnType)||
                "integer".equalsIgnoreCase(columnType)||
                "smallint".equalsIgnoreCase(columnType)) {
            s="Integer";
        }
        if ("bigint".equalsIgnoreCase(columnType)) {
            s="Long";
        }
        if ("float".equalsIgnoreCase(columnType)||"double".equalsIgnoreCase(columnType)||"decimal".equalsIgnoreCase(columnType)) {
            s="Double";
        }
        if ("clob".equalsIgnoreCase(columnType)) {
            s="java.sql.Clob";
        }
        if ("blob".equalsIgnoreCase(columnType)) {
            s="java.sql.Blob";
        }
        if ("enum".equalsIgnoreCase(columnType)||"set".equalsIgnoreCase(columnType)) {
            s="Enum";
        }
        return s;
    }

    @Override
    public String javaType2dbType(String javaType) {
        return null;
    }
}
