package com.winterchen.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "data")
public class Data {
    @Id
    private Integer id;

    @Column(name = "table_name")
    private String tableName;

    private Integer corpid;

    private String isuse;

    private String columns;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return table_name
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName
     */
    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    /**
     * @return corpid
     */
    public Integer getCorpid() {
        return corpid;
    }

    /**
     * @param corpid
     */
    public void setCorpid(Integer corpid) {
        this.corpid = corpid;
    }

    /**
     * @return isuse
     */
    public String getIsuse() {
        return isuse;
    }

    /**
     * @param isuse
     */
    public void setIsuse(String isuse) {
        this.isuse = isuse == null ? null : isuse.trim();
    }

    /**
     * @return columns
     */
    public String getColumns() {
        return columns;
    }

    /**
     * @param columns
     */
    public void setColumns(String columns) {
        this.columns = columns == null ? null : columns.trim();
    }
}