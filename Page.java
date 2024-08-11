package Level4;

public class Page {
    private String name;
    private String path;
    private boolean ifMishna;
    public Amud[] amuds;
    private String[] amudsNames;

    public Amud[] getAmuds() {
        return amuds;
    }

    public String[] getAmudsNames() {
        return amudsNames;
    }

    public void setAmudsNames(String[] amudsNames) {
        this.amudsNames = amudsNames;
    }

    public void setAmuds(Amud[] amuds) {
        this.amuds = amuds;
    }

    public Page(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isIfMishna() {
        return ifMishna;
    }

    public void setIfMishna(boolean ifMishna) {
        this.ifMishna = ifMishna;
    }
}
