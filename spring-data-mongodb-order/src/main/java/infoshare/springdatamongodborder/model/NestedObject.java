package infoshare.springdatamongodborder.model;

public class NestedObject {
    private String systemName;
    private String checkName;
    private String checkVersion;
    private String checkCategory;

    public NestedObject(String systemName, String checkName, String checkVersion, String checkCategory) {
        this.systemName = systemName;
        this.checkName = checkName;
        this.checkVersion = checkVersion;
        this.checkCategory = checkCategory;
    }
}
