package com.winterchen.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "columns")
public class Columns {
    @Id
    private Integer id;

    @Column(name = "column_name")
    private String columnName;

    @Column(name = "table_name")
    private String tableName;

    private Integer size;

    private Integer corpid;

    @Column(name = "column_type")
    private String columnType;

    private String nullable;

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
     * @return column_name
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName == null ? null : columnName.trim();
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
     * @return size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size
     */
    public void setSize(Integer size) {
        this.size = size;
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
     * @return column_type
     */
    public String getColumnType() {
        return columnType;
    }

    /**
     * @param columnType
     */
    public void setColumnType(String columnType) {


        this.columnType = columnType == null ? null : columnType.trim();
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }
}