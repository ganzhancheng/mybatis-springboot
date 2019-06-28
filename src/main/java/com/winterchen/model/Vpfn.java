package com.winterchen.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "vpfn")
public class Vpfn {
    @Id
    private Integer id;

    private String name;

    private String type;

    private String source;

    @Column(name = "isUpdate")
    private String isupdate;

    private String usesource;

    public String getUsesource() {
        return usesource;
    }

    public void setUsesource(String usesource) {
        this.usesource = usesource;
    }

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source
     */
    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    /**
     * @return isUpdate
     */
    public String getIsupdate() {
        return isupdate;
    }

    /**
     * @param isupdate
     */
    public void setIsupdate(String isupdate) {
        this.isupdate = isupdate == null ? null : isupdate.trim();
    }
}