package Level4;

public class Amud {
    private String name;
    private String path;
    private String chapter;
    private boolean ifMishnha;
    private String content;

    public Amud(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public boolean existMishnha() {
        return ifMishnha;
    }

    public void setIfMishnha(boolean ifMishnha) {
        this.ifMishnha = ifMishnha;
    }
}
