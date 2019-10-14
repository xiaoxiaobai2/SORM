package bean;

import java.util.List;
import java.util.Map;

/**
 * 封装一张表信息
 * @author 张浩
 */
public class TableInfo {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 包含的字段(字段名，字段信息)
     */
    private Map<String,ColumnInfo> columnInfos;
    /**
     * 唯一主键
     */
    private ColumnInfo onlyPriKey;
    /**
     * 联合主键
     */
    private List<ColumnInfo> onlyKey;

    public TableInfo() {
    }

    public TableInfo(String tableName, Map<String,ColumnInfo> columnInfos, List<ColumnInfo> onlyKey) {
        this.tableName = tableName;
        this.columnInfos = columnInfos;
        this.onlyKey = onlyKey;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String,ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public void setColumnInfos(Map<String,ColumnInfo> columnInfos) {
        this.columnInfos = columnInfos;
    }

    public ColumnInfo getOnlyPriKey() {
        return onlyPriKey;
    }

    public void setOnlyPriKey(ColumnInfo onlyPriKey) {
        this.onlyPriKey = onlyPriKey;
    }

    public List<ColumnInfo> getOnlyKey() {
        return onlyKey;
    }

    public void setOnlyKey(List<ColumnInfo> onlyKey) {
        this.onlyKey = onlyKey;
    }
}
