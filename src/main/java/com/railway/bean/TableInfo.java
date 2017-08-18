package com.railway.bean;

public class TableInfo {
    private Integer id;

    private String tableName;

    private Integer typeId;

    private String templatePath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public Integer getTableType() {
        return typeId;
    }

    public void setTableType(Integer tableType) {
        this.typeId = tableType;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath == null ? null : templatePath.trim();
    }
}