package bean;

/**
 * 列信息
 */
public class ColumnInfo {
    /**
     * 字段名
     */
    private String name;
    /**
     * 字段类型
     */
    private String dataType;
    /**
     * 是否为主键   普通键--0  主键--1   外键--2
     */
    private int keyType;

    public ColumnInfo() {
    }

    public ColumnInfo(String name, String dataType, int keyType) {
        this.name = name;
        this.dataType = dataType;
        this.keyType = keyType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getKeyType() {
        return keyType;
    }

    public void setKeyType(int keyType) {
        this.keyType = keyType;
    }
}
