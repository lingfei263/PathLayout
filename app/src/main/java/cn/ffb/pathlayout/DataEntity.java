package cn.ffb.pathlayout;

import java.util.List;

/**
 * Created by lingfei on 2017/7/4.
 */

public class DataEntity {
    private String key;
    private List<FileEntity> fileList;

    public DataEntity(String key, List<FileEntity> fileList) {
        this.key = key;
        this.fileList = fileList;

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<FileEntity> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileEntity> fileList) {
        this.fileList = fileList;
    }
}
