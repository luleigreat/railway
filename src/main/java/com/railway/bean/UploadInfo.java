package com.railway.bean;

public class UploadInfo extends UploadInfoKey {
    private Integer tableId;

    private Integer uploaded;

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer getUploaded() {
        return uploaded;
    }

    public void setUploaded(Integer uploaded) {
        this.uploaded = uploaded;
    }
}