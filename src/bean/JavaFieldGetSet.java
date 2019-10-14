package bean;

public class JavaFieldGetSet {
    private String privateTypeName;
    private String createSet;
    private String createGet;

    public JavaFieldGetSet() {
    }

    public JavaFieldGetSet(String privateTypeName, String createSet, String createGet) {
        this.privateTypeName = privateTypeName;
        this.createSet = createSet;
        this.createGet = createGet;
    }

    @Override
    public String toString() {
        System.out.println(privateTypeName);
        System.out.println(createSet);
        System.out.println(createGet);
        return super.toString();
    }

    public String getPrivateTypeName() {
        return privateTypeName;
    }

    public void setPrivateTypeName(String privateTypeName) {
        this.privateTypeName = privateTypeName;
    }

    public String getCreateSet() {
        return createSet;
    }

    public void setCreateSet(String createSet) {
        this.createSet = createSet;
    }

    public String getCreateGet() {
        return createGet;
    }

    public void setCreateGet(String createGet) {
        this.createGet = createGet;
    }
}
