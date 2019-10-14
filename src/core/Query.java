package core;

import java.util.List;

/**
 * 查询接口
 * @author 张浩
 * @date 2019.10.12
 */
public interface Query {
    /**
     * 将一个对象存储到数据库中
     * @param object 要存储的对象
     */
    void insert(Object object);

    /**
     * 删除clazz表示类对应的表中记录（按主键ID删除）
     * @param clazz
     * @param id
     */
    void delete(Class clazz,Object id);

    /**
     * 按对象删除
     * @param object 要删除的对象
     */
    void delete(Object object);

    /**
     * 更新数据信息
     * @param object 要更新的对象
     * @param fields 要更新的属性
     * @return  影响的条数
     */
    int update(Object object,String[] fields);

    /**
     * 查询多行记录
     * @param sql 查询语句
     * @param object 要查询的对象
     * @param params 按要求查找
     * @return  返回查找的结果，封装到List里
     */
    List queryRows(String sql,Object object,String[] params);

    /**
     * 查询单行记录
     * @param sql 查询语句
     * @param object
     * @param params 要查询的参数
     * @return 换回查询结果过封装到Object中
     */
    Object[] queryUniqueRow(String sql,Object object,String[] params);

    /**
     * 查询一个结果
     * @param sql 查询语句
     * @param object
     * @param params 要查询的参数
     * @return 换回查询的结果对象
     */
    Object queryValue(String sql,Object object,String[] params);

    /**
     * 查询一个数字
     * @param sql 查询语句
     * @param object
     * @param params 要查询的参数
     * @return 换回查询的数字
     */
    Number queryNumber(String sql,Object object,String[] params);

    /**
     * 执行sql语句
     * @param sql
     * @param params
     * @return
     */
    int excuteSql(String sql,Object[] params);
}
