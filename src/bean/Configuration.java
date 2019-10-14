package bean;

/**
 * 封装配置信息
 * @author 张浩
 */
public class Configuration {
    /**
     * 驱动
     */
    private String Driver;
    private String URL;
    private String User;
    private String Pwd;
    private String usingDB;
    /**
     * 项目的源码路径
     */
    private String srcPath;
    /**
     * 生成java类的包
     */
    private String poPackages;

    public Configuration() {
    }

    public Configuration(String driver, String URL, String user, String pwd, String usingDB, String srcPath, String poPackages) {
        Driver = driver;
        this.URL = URL;
        User = user;
        Pwd = pwd;
        this.usingDB = usingDB;
        this.srcPath = srcPath;
        this.poPackages = poPackages;
    }

    public String getDriver() {
        return Driver;
    }

    public void setDriver(String driver) {
        Driver = driver;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPwd() {
        return Pwd;
    }

    public void setPwd(String pwd) {
        Pwd = pwd;
    }

    public String getUsingDB() {
        return usingDB;
    }

    public void setUsingDB(String usingDB) {
        this.usingDB = usingDB;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getPoPackages() {
        return poPackages;
    }

    public void setPoPackages(String poPackages) {
        this.poPackages = poPackages;
    }
}
