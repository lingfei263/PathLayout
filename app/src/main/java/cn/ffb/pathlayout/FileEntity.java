package cn.ffb.pathlayout;

/**
 * Created by lingfei on 2017/7/3.
 */

public class FileEntity {
    private String title;
    private boolean isFile;

    public FileEntity(String title, boolean isFile) {
        this.title = title;
        this.isFile = isFile;
    }

    public FileEntity(String title) {
        this.title = title;
        this.isFile = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }
}
