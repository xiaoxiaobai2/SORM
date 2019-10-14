package core;

/**
 * 负责类型转换
 */
public interface TypeConvertor {
    /**
     * 数据库数据类型转为java数据类型
     * @param columnType 列 的类型
     * @return java数据类型
     */
    String dbType2javaType(String columnType);

    /**
     * java数据类型转为数据库数据类型
     * @param javaType java数据类型
     * @return 数据库数据类型
     */
    String javaType2dbType(String javaType);
}
