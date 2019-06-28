package com.winterchen.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "altersql")
public class Altersql {
    @Id
    private Integer id;

    @Column(name = "table_name")
    private String tableName;

    private Integer corpid;

    private String columns;

    private String sqls;

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


    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getSqls() {
        return sqls;
    }

    public void setSqls(String sqls) {
        this.sqls = sqls;
    }
}